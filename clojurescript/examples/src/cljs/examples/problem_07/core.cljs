(ns examples.problem-07.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]))

(defn app []
  [:div
   [:h2 "Пример 7: Обновления в реальном времени"]
   [:p "ClojureScript реализация в разработке..."]])

(defn ^:dev/after-load start []
  (rdom/render [app]
               (js/document.getElementById "app")))

(defn ^:export init []
  (start))