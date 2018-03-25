(ns measure.routes
  (:require [compojure.core :refer [GET PUT POST DELETE routes context]]
            [ring.util.response :refer [response content-type charset]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
    ;; [ring.middleware.file :refer [wrap-file]]
            [measure.db :as q]))



(defn- json-response [data]
  (-> data
      (response)
      (content-type "application/json")
      (charset "UTF-8")))

(defn find-hero-by-id [db id]
  (json-response
    (first (q/find-hero-by-id db id))))

(defn find-all-heroes [db]
  (json-response
    (q/find-all-heroes db)))

(defn insert-hero [db hero]
  (json-response
    (first (q/insert-hero db hero))))


;; Define all routes here
(defn app-routes [db]
  (routes
    (context "/api" []
      (GET "/heroes/:id" [id] (find-hero-by-id db id))
      (GET "/heroes" [] (find-all-heroes db))
      (POST "/heroes" request (insert-hero db (:body request))))))


;; The handler function wraps the routes in middleware
(defn handler [db]
  (-> (app-routes db)
      (wrap-json-body {:keywords? true :bigdecimals? true})
      (wrap-json-response)
      ;;(wrap-file "./dist")
      ))
