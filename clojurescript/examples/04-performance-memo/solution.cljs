(ns solution
  (:require [reagent.core :as r]
            [clojure.string :as str]))

;; Reagent атомы - структурное разделение данных
(def count-a (r/atom 0))
(def data-a (r/atom (vec (range 1 1001)))) ; Большой список для демонстрации

;; Reagent track - мемоизация вычислений, кэширует результаты
;; Вычисляется только когда изменяются зависимые атомы
(def doubled-data (r/track (fn []
                             (println "🔄 Computing doubled data...")
                             (->> @data-a (map #(* % 2))))))

(def data-sum (r/track (fn []
                         (println "🔄 Computing sum...")
                         (reduce + @data-a))))

(defn performance-memo-solution []
  ;; Reagent автоматически определяет какие части нужно обновить
  ;; Структурное разделение: count-a и data-a независимы
  (let [data @data-a
        doubled @doubled-data  ; Мемоизированное значение
        sum @data-sum]         ; Мемоизированное значение
    [:div
     [:h3 "ClojureScript Structural Sharing Solution"]
     [:p "Count: " @count-a]
     [:p "Data length: " (count data)]
     [:p "Doubled (memoized): " (str/join ", " (take 10 doubled))]
     [:p "Sum (memoized): " sum]
     [:button {:on-click #(swap! count-a inc)} "Increment Count"]
     [:button {:on-click #(swap! data-a conj (rand-int 10))} "Add Random Number"]
     [:p.small "🎯 React: useMemo, useCallback, memo - ручная оптимизация"]
     [:p.small "🎯 ClojureScript: track + структурное разделение - автоматическая оптимизация"]]))