(ns measure.component.c3po
  (:require [com.stuartsierra.component :as component]
            [hikari-cp.core :as hikari])
  (:import com.mchange.v2.c3p0.ComboPooledDataSource))

(defn- pool
  [spec]
  (let [cpds (doto (ComboPooledDataSource.)
               (.setJdbcUrl (:connection-uri spec))
               (.setUser (:user spec))
               (.setPassword (:password spec))
               ;; expire excess connections after 1 minute of inactivity:
               (.setMaxIdleTimeExcessConnections 60)
               ;; expire connections after 20 secs of inactivity:
               (.setMaxIdleTime 20))]
    cpds))


(defrecord C3P0 [config]
  component/Lifecycle
  (start [component]
    (if (:datasource component)
      component
      (assoc component :datasource (pool config))))

  (stop [component]
    (if-let [ds (:datasource component)]
      (do (.close ds)  ;; TODO: verify how to close the datasource!
          (dissoc component :datasource))
      component)))


(defn make-connection-pool [config]
  (->C3P0 config))
