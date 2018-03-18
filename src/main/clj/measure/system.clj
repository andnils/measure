(ns measure.system
  (:require [com.stuartsierra.component :as component]
            [environ.core :refer [env]]
            [measure.component.hikaricp :refer [hikaricp]]
            [measure.component.jetty :refer [jetty-server]]))

(defn make-config
  "Pull the configs from the env map"
  []
  (let [{:keys [measure-http-port measure-db-url measure-db-user measure-db-password]} env]
    {:http-config {:port (or measure-http-port 3000)}
     :db-config {:jdbc-url measure-db-url
                 :username measure-db-user
                 :password measure-db-password}}))


(defn make-system
  "Construct the system.
  Either pull the config from the environment, or supply
  a config."
  ([]
   (let [config (make-config)]
     (make-system config)))
  ([config]
   (let [{:keys [db-config http-config]} config]
     (component/system-map
       :db (hikaricp db-config)
       :http (component/using
               (jetty-server http-config) [:db])))))
