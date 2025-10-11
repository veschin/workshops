(ns solution
  (:require [reagent.core :as r]
            [clojure.string :as str]))

(def count-a (r/atom 0))
(def data-a (r/atom (vec (range 1 1001)))) ; Large list for perf demo

(defn calc-sum [data]
  (println "Calculating sum...")
  (reduce + data))

(defn performance-memo-solution []
  (let [data @data-a
        doubled (->> data (map #(* % 2)))]
    [:div
     [:h3 "ClojureScript Structural Sharing Solution"]
     [:p "Count: " @count-a]
     [:p "Data length: " (count data)] ; Truncate for display
     [:p "Doubled: " (str/join ", " doubled)]
     [:p "Sum: " (calc-sum data)]
     [:button {:on-click #(swap! count-a inc)} "Increment"]
     [:button {:on-click #(swap! data-a conj (rand-int 10))} "Add Random"]]))