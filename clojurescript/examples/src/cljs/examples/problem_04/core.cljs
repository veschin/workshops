(ns examples.problem-04.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]))

(defn app []
  [:div
   [:h2 "Пример 4: Поиск и фильтрация"]
   [:p "ClojureScript реализация в разработке..."]])

(defn ^:dev/after-load start []
  (rdom/render [app]
               (js/document.getElementById "app")))

(defn ^:export init []
  (start))