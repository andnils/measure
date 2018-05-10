(ns measure.routes
  (:require [compojure.core :refer [GET PUT POST DELETE routes context]]
            [compojure.route :as route]
            [compojure.coercions :refer [as-int]]
            [ring.util.response :refer [response created not-found content-type charset]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
    ;; [ring.middleware.file :refer [wrap-file]]
            [measure.db :as q]))


(defn find-hero-by-id [db id]
  (let [hero (first (q/find-hero-by-id db id))]
    (if hero
      (response hero)
      (not-found {:error "not found"}))))

(defn find-all-heroes [db]
  (response (q/find-all-heroes db)))

(defn insert-hero [db hero]
  (let [id (q/insert-hero db hero)]
    (created (str "/heroes/" id))))


;; Define all routes here
(defn app-routes [db]
  (routes
    (context "/api" []
      (GET "/heroes/:id" [id :<< as-int] (find-hero-by-id db id))
      (GET "/heroes" [] (find-all-heroes db))
      (POST "/heroes" request (insert-hero db (:body request))))
    (route/not-found "not found")))


;; The handler function wraps the routes in middleware
(defn handler [db]
  (-> (app-routes db)
      (wrap-json-body {:keywords? true :bigdecimals? true})
      (wrap-json-response)
      ;;(wrap-file "./dist")
      ))
