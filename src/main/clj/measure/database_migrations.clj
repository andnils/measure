(ns measure.database-migrations
  (:require [ragtime.jdbc]
            [ragtime.repl]))


(defn- make-ragtime-config
  [{{:keys [:jdbc-url :username :password]} :db-config}]
  {:datastore  (ragtime.jdbc/sql-database {:connection-uri jdbc-url
                                           :user username
                                           :password password})
   :migrations (ragtime.jdbc/load-resources "migrations")})


(defn db-migrate [config]
  (ragtime.repl/migrate (make-ragtime-config config)))


(defn db-rollback [config]
  (ragtime.repl/rollback (make-ragtime-config config)))
