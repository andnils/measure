(ns measure.db
  (:require [clojure.java.jdbc :as jdbc]))

(defn- generated-key
  "Extract the generated primary key from
   an INSERT statement.
   PostgreSQL returns the whole row, while
   H2 returns a map with key :identity()"
  [row]
  (if-let [h2-id (get row (keyword "identity()"))]
    {:id h2-id}
    row))


(defn find-hero-by-id [db id]
  (jdbc/query db
    ["SELECT id, firstname, lastname, heroname
      FROM heroes WHERE id = ?" id]))

(defn find-all-heroes [db]
  (jdbc/query db
    ["SELECT id, firstname, lastname, heroname 
      FROM heroes ORDER BY id"]))

(defn insert-hero [db hero]
  (first
   (map generated-key
        (jdbc/insert! db :heroes hero))))



