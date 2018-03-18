(ns measure.routes
  (:require [compojure.core :refer [GET PUT POST DELETE routes context]]
            [ring.util.response :refer [response content-type]]
            [ring.middleware.json :as middleware]
            [measure.db :as q]))


(defn- json-response [data]
  (-> data
    (response)
    (content-type "application/json")))

(defn find-hero-by-id [db id]
  (json-response (q/find-hero-by-id db id)))

(defn find-all-heroes [db]
  (json-response (q/find-all-heroes db)))

(defn insert-hero [db hero]
  (json-response (q/insert-hero db hero)))


;; Define all routes here
(defn app-routes [db]
  (routes
    (GET "/" [] "I'm a little teapot")
    (context "/api" []
      (GET "/heroes/:id" [id] (find-hero-by-id db id))
      (GET "/heroes" [] (find-all-heroes db))
      (POST "/heroes" request (insert-hero db (:body request))))))


;; The handler function wraps the routes in middleware
(defn handler [db]
  (-> (app-routes db)
    (middleware/wrap-json-body {:keywords? true :bigdecimals? true})
    (middleware/wrap-json-response)))
