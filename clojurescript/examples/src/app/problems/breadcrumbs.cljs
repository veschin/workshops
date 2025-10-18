(ns app.problems.breadcrumbs
  (:require [reagent.core :as r]
            [clojure.string :as str]))

(def path (r/atom ["Home"]))

(defn update-path [new-path]
  (reset! path new-path)
  (js/history.pushState {} "" (str "/" (str/join "/" new-path))))

(defn navigate-to [idx]
  (update-path (subvec @path 0 (inc idx))))

(defn breadcrumbs []
  [:nav.breadcrumbs
   (->> @path
        (map-indexed (fn [i p] [:span {:key i :on-click #(navigate-to i)} p " >"]))
        (interpose " / ")
        vec)])

(defn breadcrumbs-page []
  [:div
   [:h2 "Breadcrumb Navigation"]
   [:div.mb-4
    [:button.bg-blue-500.hover:bg-blue-700.text-white.px-4.py-2.rounded.mr-2 {:on-click #(update-path (conj @path "Subfolder"))} "Go Deeper"]
    [:button.bg-gray-500.hover:bg-gray-700.text-white.px-4.py-2.rounded {:on-click #(update-path ["Home"])} "Reset"]]
   [breadcrumbs]])