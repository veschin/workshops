(ns app.problems.theme
  (:require [reagent.core :as r]))

(def theme (r/atom (or (js->clj (.getItem js/localStorage "theme")) :light)))

(defn sync-storage [k ref old new]
  (.setItem js/localStorage "theme" (name new)))

(add-watch theme :storage sync-storage)

(defn toggle-theme []
  (swap! theme (fn [t] (if (= t :light) :dark :light))))

(defn themed-component [content]
  [:div {:class (name @theme)} content])

(defn settings []
  [:div
   [:button {:on-click toggle-theme} "Toggle"]
   [themed-component "Preview"]])

(defn theme-page []
  [:div
   [:h2 "Theme Switcher"]
   [:button.bg-blue-500.hover:bg-blue-700.text-white.px-4.py-2.rounded.mr-2 {:on-click toggle-theme} "Toggle Theme"]
   [themed-component [:div.p-4.bg-gray-100.dark:bg-gray-800 "This is a preview component that responds to theme changes"]]])