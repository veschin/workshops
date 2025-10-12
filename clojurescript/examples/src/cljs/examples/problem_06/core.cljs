(ns examples.problem-06.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]))

(defn app []
  [:div
   [:h2 "Пример 6: Многошаговая форма"]
   [:p "ClojureScript реализация в разработке..."]])

(defn ^:dev/after-load start []
  (rdom/render [app]
               (js/document.getElementById "app")))

(defn ^:export init []
  (start))