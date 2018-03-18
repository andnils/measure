(ns dev
  "Tools for interactive development with the REPL. This file should
  not be included in a production build of the application.

  Call `(reset)` to reload modified code and (re)start the system.

  The system under development is `system`, referred from
  `com.stuartsierra.component.repl/system`.

  See also https://github.com/stuartsierra/component.repl"
  (:require
   [clojure.java.io :as io]
   [clojure.java.javadoc :refer [javadoc]]
   [clojure.pprint :refer [pprint]]
   [clojure.reflect :refer [reflect]]
   [clojure.repl :refer [apropos dir doc find-doc pst source]]
   [clojure.set :as set]
   [clojure.string :as string]
   [clojure.test :as test]
   [clojure.tools.namespace.repl :refer [refresh refresh-all clear]]
   [com.stuartsierra.component :as component]
   [com.stuartsierra.component.repl :refer [reset set-init start stop system]]
   [ragtime.jdbc]
   [ragtime.repl]
   [measure.system :refer [make-system make-config]]))


(clojure.tools.namespace.repl/set-refresh-dirs "dev" "src/main/clj" "src/test/clj")


(def dev-config (make-config))

(def ragtime-config
  (let [db-config (:db-config dev-config)
        uri (:jdbc-url db-config)
        user (:username db-config)
        password (:password db-config)]
    {:datastore  (ragtime.jdbc/sql-database {:connection-uri uri :user user :password password})
     :migrations (ragtime.jdbc/load-resources "migrations")}))

(defn db-migrate []
  (ragtime.repl/migrate ragtime-config))
(defn db-rollback []
  (ragtime.repl/rollback ragtime-config))


(set-init (fn [_] (make-system dev-config)))
