(ns measure.db
  (:require [clojure.java.jdbc :as jdbc]))

(defn- ->int [s]
  (Integer/parseInt s))

(defn find-hero-by-id [db id]
  (jdbc/query db
    ["SELECT id, firstname, lastname, heroname
      FROM heroes WHERE id = ?" (->int id)]))

(defn find-all-heroes [db]
  (jdbc/query db
    ["SELECT id, firstname, lastname, heroname 
      FROM heroes ORDER BY id"]))
