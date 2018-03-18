(ns measure.component.jetty
    (:import org.eclipse.jetty.server.Server)
    (:require [com.stuartsierra.component :as component]
              [measure.routes :as routes]
              [ring.adapter.jetty :as jetty]))



(defrecord JettyServer [config db]
  component/Lifecycle
  (start [component]
    (if (:server component)
      component
      (let [port (Integer/parseInt (get-in component [:config :port]))
            options {:join? false :port port}
            handler (routes/handler (:db component))
            server  (jetty/run-jetty (fn [req] (handler req)) options)]
        (assoc component
               :server server))))
  (stop [component]
    (if-let [^Server server (:server component)]
      (do (.stop server)
          (.join server)
          (dissoc component :server))
      component)))


(defn jetty-server [config]
  (map->JettyServer {:config config}))
