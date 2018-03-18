(ns measure.component.hikaricp
  (:require [com.stuartsierra.component :as component]
            [hikari-cp.core :as hikari]))


;; For config options: see https://github.com/tomekw/hikari-cp
;; a minimal config looks like this:
;;
;; {:jdbc-url "jdbc:postgresql://localhost:5432/mydatabase" :username "user1" :password "secret"}


(defrecord HikariCP [config]
  component/Lifecycle
  (start [component]
    (if (:datasource component)
      component
      (assoc component :datasource (hikari/make-datasource (:config component)))))
  (stop [component]
    (if-let [ds (:datasource component)]
      (do (hikari/close-datasource ds)
          (dissoc component :datasource))
      component)))


(defn hikaricp [config]
  (map->HikariCP {:config config}))

