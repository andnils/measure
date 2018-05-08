(ns measure.config
  (:require [aero.core :as aero]))

(def config
  (delay
   (aero/read-config
    (clojure.java.io/resource "config.edn")
    {:resolver aero/relative-resolver})))
