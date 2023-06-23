(ns main
  ;; any :require and/or :import clauses
  (:gen-class)
  (:require [ring.adapter.jetty :as jetty]))


(defn find-route [{uri :uri}]
  (case uri
    "/test" {:status 200
             :header {"Content-Type" "text/plain"}
             :body   "Test"}
    "/"     {:status 200
             :header {"Content-Type" "text/plain"}
             :body   (apply str (repeatedly 10 #(rand-int 10)))}
    {:status 200
     :body   "404"}))

(defonce server
  (jetty/run-jetty
   #'find-route
   {:port 3000
    :join? false}))

(defn -main [& _]
  (.start server))

(comment

  (.stop server)

  ;;
  )
