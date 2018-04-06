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
