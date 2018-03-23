(ns measure.main
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [measure.system :as system]
            [ragtime.jdbc]
            [ragtime.repl]))

(defn db-migrate [config]
  (let [db-config (:db-config config)
        uri (:jdbc-url db-config)
        user (:username db-config)
        password (:password db-config)]
    (ragtime.repl/migrate
      {:datastore  (ragtime.jdbc/sql-database {:connection-uri uri :user user :password password})
       :migrations (ragtime.jdbc/load-resources "migrations")})))


(defn -main [& args]
  (let [config (system/make-config)
        system (system/make-system config)]
    (db-migrate config)
    (println "db-migrate config DONE...")
    (component/start system)
    (println "component/start system DONE...")))
