(ns 09-interop.mui-example
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            ["@mui/material/Button" :default Button]
            ["@mui/material/TextField" :default TextField]))

(def name-atom (r/atom ""))

(defn submit []
  (println "Submitted:" @name-atom))

(defn mui-wrapper [props & children]
  (into [:> Button (clj->js props)] children))

(defn form []
  [:div
   [:h3 "CLJS MUI Interop"]
   [:> TextField {:label "Name"
                  :value @name-atom
                  :on-change #(reset! name-atom (.. % -target -value))}]
   [mui-wrapper {:variant "contained" :on-click submit} "Submit"]])

(defn mount []
  (rdom/render [form] (.getElementById js/document "app")))

(mount)