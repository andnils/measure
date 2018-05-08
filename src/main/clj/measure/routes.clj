(ns measure.routes
  (:require [compojure.core :refer [GET PUT POST DELETE defroutes context]]
            [compojure.route :as route]
            [compojure.coercions :refer [as-int]]
            [ring.util.response :refer [response created not-found content-type charset]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [measure.db :as q]))



(defn find-hero [id]
  (if-let [hero (q/find-hero id)]
    (response hero)
    (not-found {:error "not found"})))


(defn find-heroes []
  (response (q/find-heroes)))


(defn insert-hero [hero]
  (let [result (q/insert-hero hero)]
    (created (str "/heroes/" (:id result)))))


;; Define all routes here
(def app-routes
  (-> (compojure.core/routes
        (context "/api" []
           (GET "/heroes/:id" [id :<< as-int]
                (find-hero id))
           (GET "/heroes" []
                (find-heroes))
           (GET "/dudes" []
                "hopp")
           (POST "/heroes" request
                 (insert-hero (:body request))))
        (route/not-found "not found"))
      (wrap-json-body {:keywords? true :bigdecimals? true})
      (wrap-json-response)))
