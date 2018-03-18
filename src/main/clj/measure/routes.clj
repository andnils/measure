(ns measure.routes
  (:require [compojure.core :refer [GET PUT POST DELETE routes context]]
            [ring.util.response :refer [response content-type]]
            [cheshire.core :as json]
            [measure.db :as q]))


(defn- json-response [data]
  (-> data
    (json/encode)
    (response)
    (content-type "application/json")))

(defn find-hero-by-id [db id]
  (json-response (q/find-hero-by-id db id)))

(defn find-all-heroes [db]
  (json-response (q/find-all-heroes db)))



(defn make-routes [db]
  (routes
    (GET "/" [] "I'm a little teapot")
    (context "/api" []
      (GET "/heroes/:id" [id] (find-hero-by-id db id))
      (GET "/heroes" [] (find-all-heroes db)))))
