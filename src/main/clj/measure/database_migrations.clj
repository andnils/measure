(ns measure.database-migrations
  (:require [ragtime.jdbc]
            [ragtime.repl]))


(defn- make-ragtime-config [config]
  {:datastore  (ragtime.jdbc/sql-database config)
   :migrations (ragtime.jdbc/load-resources "migrations")})


(defn db-migrate [config]
  (ragtime.repl/migrate (make-ragtime-config config)))


(defn db-rollback [config]
  (ragtime.repl/rollback (make-ragtime-config config)))
