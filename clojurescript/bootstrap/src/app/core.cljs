(ns app.core
  (:require [reagent.core :as r]))

(defonce greeting "Hello ClojureScript!")
(def counter (r/atom 0))

(defn increment-counter []
  (swap! counter inc))

(defn decrement-counter []
  (swap! counter dec))

(defn app []
  [:div {:style {:font-family "Arial, sans-serif"
                 :max-width "600px"
                 :margin "50px auto"
                 :padding "20px"}}
   [:h1 greeting]
   [:p "Welcome to ClojureScript! This is a simple Reagent application."]
   [:div
    [:h2 "Counter Example"]
    [:p "Count: " @counter]
    [:button {:on-click increment-counter
              :style {:margin-right "10px"}} "+ Increment"]
    [:button {:on-click decrement-counter} "- Decrement"]]
   [:div {:style {:margin-top "20px"}}
    [:h3 "What you can do:"]
    [:ul
     [:li "Edit this file and see hot reload in action"]
     [:li "Open the browser console to see any errors"]
     [:li "Experiment with Reagent components"]
     [:li "Check out the ClojureScript documentation"]]]])

(defn stop []
  (js/console.log "Stopping ClojureScript app..."))

(defn start []
  (js/console.log "Starting ClojureScript app...")
  (r/render [app]
            (js/document.getElementById "app")))

(defn ^:export init []
  (start))