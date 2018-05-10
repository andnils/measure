(ns measure.system
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [aero.core :as aero]
            [measure.database-migrations :refer [db-migrate]]
            [measure.component.hikaricp :refer [hikaricp]]
            [measure.component.jetty :refer [jetty-server]]))


(defn make-config []
  (aero/read-config
    (clojure.java.io/resource "config.edn")
    {:resolver aero/relative-resolver}))


(defn make-system
  "Creates a system containing:
   * a database component (:db),
   * a http-server component (:http)
     depending on the database component."
  [config]
  (component/system-map
   :db (hikaricp config)
   :http (component/using (jetty-server config) [:db])))


(defn -main [& args]
  (let [config (make-config)
        system (make-system config)]
    (db-migrate config)
    (component/start system)))
