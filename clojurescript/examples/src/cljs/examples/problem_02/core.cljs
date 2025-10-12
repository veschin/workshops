(ns examples.problem-02.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]))

(defn app []
  [:div
   [:h2 "Пример 2: Состояние модальных окон"]
   [:p "ClojureScript реализация в разработке..."]])

(defn ^:dev/after-load start []
  (rdom/render [app]
               (js/document.getElementById "app")))

(defn ^:export init []
  (start))