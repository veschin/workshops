(ns app.problems.search
  (:require [reagent.core :as r]
            [clojure.string :as str]))

(def data (r/atom (range 1000)))
(def search-term (r/atom ""))

(defn debounce [f ms]
  (let [t (atom nil)]
    (fn [& args]
      (js/clearTimeout @t)
      (reset! t (js/setTimeout #(apply f args) ms)))))

(def debounced-filter (debounce
                       (fn [_]
                         (let [term @search-term]
                           (swap! data (fn [d] (->> d (filter #(str/includes? (str %) term)))))
                           (js/history.replaceState {} "" (str "?q=" term))))
                       300))

(defn search-input []
  [:input {:type "text" :value @search-term
           :on-change (fn [e] (let [val (.. e -target -value)] (reset! search-term val) (debounced-filter)))}])

(defn results []
  [:ul (for [item @data] ^{:key item} [:li item])])

(defn search-page []
  [:div
   [:h2 "Search and Filter"]
   [:div.mb-4
    [search-input]
    [:button.bg-gray-500.hover:bg-gray-700.text-white.px-4.py-2.rounded.ml-2 {:on-click #(do (reset! search-term "") (reset! data (range 1000)))} "Clear"]]
   [:div.max-h-96.overflow-y-auto.border.border-gray-200.rounded.p-4
    [results]]])