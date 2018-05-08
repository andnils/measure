(ns measure.system
  (:gen-class)
  (:require [measure.config :refer [config]]
            [measure.database-migrations :refer [db-migrate]]
            [measure.routes :refer [app-routes]]
            [ring.adapter.jetty :as jetty]))


(defonce http-server (atom nil))


(defn start-jetty []
  (let [jetty-opts {:join? false :port (:http-port @config)}]
    (reset! http-server (jetty/run-jetty #'app-routes jetty-opts))))


(defn stop-jetty []
  (let [server @http-server]
    (when-not (nil? server)
      (.stop server)
      (.join server)
      (reset! http-server nil))))


(defn -main [& args]
  (db-migrate @config)
  (start-jetty))
