(ns user
  (:require [measure.config :refer [config]]
            [measure.database-migrations :refer [db-migrate db-rollback]]
            [measure.system :refer [start-jetty stop-jetty]]
))


(defn go []
  (db-migrate @config)
  (start-jetty))

