(ns measure.db
  (:require  [measure.config :refer [config]]
             [clojure.java.jdbc :as jdbc])
  (:import com.mchange.v2.c3p0.ComboPooledDataSource))


(defn pool
  [spec]
  (let [cpds (doto (ComboPooledDataSource.)
               (.setJdbcUrl (:connection-uri spec))
               (.setUser (:user spec))
               (.setPassword (:password spec))
               ;; expire excess connections after 1 minute of inactivity:
               (.setMaxIdleTimeExcessConnections 60)
               ;; expire connections after 20 secs of inactivity:
               (.setMaxIdleTime 20))]
    {:datasource cpds}))

(def pooled-db (future (pool @config)))

(defn get-connection [] @pooled-db)

(defn generated-pk
  "Extract the generated primary key from
   an INSERT statement.
   PostgreSQL returns the whole row, while
   H2 returns a map with key :identity()"
  [row]
  (if-let [h2-identity (get row (keyword "identity()"))]
    (assoc row :id h2-identity)
    row))




(defn find-hero [id]
  (jdbc/query (get-connection)
              ["SELECT id, firstname, lastname, heroname FROM heroes WHERE id = ?" id]
              {:result-set-fn first}))


(defn find-heroes []
  (jdbc/query (get-connection)
              ["SELECT id, firstname, lastname, heroname FROM heroes ORDER BY id"]))


(defn insert-hero [hero]
  (->>  (jdbc/insert! (get-connection) :heroes hero)
        (map generated-pk)
        (first)))

