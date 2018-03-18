(ns measure.main
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [measure.system :as system]))


(defn -main [& args]
  (let [config (system/make-config)
        system (system/make-system config)]
    ;; TODO: run the db migrations here
    (component/start system)))

