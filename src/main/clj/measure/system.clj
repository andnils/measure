(ns measure.system
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [aero.core :as aero]
            [ragtime.jdbc]
            [ragtime.repl]
            [measure.component.hikaricp :refer [hikaricp]]
            [measure.component.jetty :refer [jetty-server]]))



(defn- make-ragtime-config
  [{{:keys [:jdbc-url :username :password]} :db-config}]
  {:datastore  (ragtime.jdbc/sql-database {:connection-uri jdbc-url
                                           :user username
                                           :password password})
   :migrations (ragtime.jdbc/load-resources "migrations")})


(defn db-migrate [config]
  (ragtime.repl/migrate (make-ragtime-config config)))


(defn db-rollback [config]
  (ragtime.repl/rollback (make-ragtime-config config)))



(defn make-config []
  (aero/read-config
    (clojure.java.io/resource "config.edn")
    {:resolver aero/resource-resolver}))


(defn make-system
  ([]
   (let [config (make-config)]
     (make-system config)))
  ([config]
   (let [{:keys [db-config http-config]} config]
     (component/system-map
       :db (hikaricp db-config)
       :http (component/using
               (jetty-server http-config) [:db])))))


(defn -main [& args]
  (let [config (make-config)
        system (make-system config)]
    (db-migrate config)
    (component/start system)))
