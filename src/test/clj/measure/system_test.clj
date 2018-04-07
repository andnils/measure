(ns measure.system-test
  (:require [restpect.core :refer [created ok not-found]]
            [restpect.json :refer [GET POST]]
            [clojure.test :refer [deftest use-fixtures]]
            [measure.system :refer [make-system]]
            [com.stuartsierra.component :as component]
            [measure.database-migrations :refer [db-migrate db-rollback]]))

(def test-system (atom nil))

(def test-config
  {:http-config {:port 35127}
   :db-config   {:jdbc-url "jdbc:h2:mem;TRACE_LEVEL_FILE=0;TRACE_LEVEL_SYSTEM_OUT=0"
                 :maximum-pool-size 1}})

(defn url [path]
  (let [port (get-in test-config [:http-config :port])]
    (str "http://localhost:" port path)))

(defn start-test-system []
  (when-not @test-system
    (println "Starting test system")
    (let [system (make-system test-config)]
      (reset! test-system (component/start system)))))

(defn stop-test-system []
  (println "Stopping test system")
  (component/stop @test-system))

(defn system-fixture [test-fn]
  (start-test-system)
  (test-fn)
  (stop-test-system))

(defn db-fixture [test-fn]
  (db-migrate test-config)
  (test-fn)
  (db-rollback test-config))


(use-fixtures :once system-fixture)


;;;;;;;;;;;;;;;;;;;;
;;  Test section  ;;
;;;;;;;;;;;;;;;;;;;;

(deftest get-heroes
  (db-migrate test-config)
  (ok
    (GET (url "/api/heroes"))
    #{{:id 1 :firstname "Clark" :lastname "Kent" :heroname "Superman"}
      {:id 2 :firstname "Bruce" :lastname "Wayne" :heroname "Batman"}
      {:id 3 :firstname "Diana" :lastname "Prince" :heroname "Wonder Woman"}})
  (db-rollback test-config))

(deftest get-hero
  (db-migrate test-config)
  (ok
    (GET (url "/api/heroes/1"))
    {:id 1 :firstname "Clark" :lastname "Kent" :heroname "Superman"})
  (db-rollback test-config))

(deftest create-hero
  (db-migrate test-config)
  (created
    (POST (url "/api/heroes") {:firstname "Foo" :lastname "Bar" :heroname "xyzzy"}
      {:id integer?}))
  (db-rollback test-config))

(deftest no-hero-found
  (db-migrate test-config)
  (not-found
   (GET (url "/api/heroes/999"))
   {:error #"not found"})
   (db-rollback test-config))
