(ns measure.db
  (:require [clojure.java.jdbc :as jdbc]))


(defn find-hero-by-id [db id]
  (jdbc/query db
    ["SELECT id, firstname, lastname, heroname
      FROM heroes WHERE id = ?" id]))

(defn find-all-heroes [db]
  (jdbc/query db
    ["SELECT id, firstname, lastname, heroname 
      FROM heroes ORDER BY id"]))

(defn insert-hero [db hero]
  (jdbc/insert! db :heroes hero))
