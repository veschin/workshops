(ns main
  ;; any :require and/or :import clauses
  (:gen-class)
  (:require [ring.adapter.jetty :as jetty]))

(defn handler [_request]
  {:status  200
   :headers {"Content-Type" "text/plain"}
   :body    "Ok"})

(defonce server
  (jetty/run-jetty
   #'handler
   {:port 3000
    :join? false}))

(defn -main []
  (.start server))

(comment

  (.stop server)

  ;;
  )
