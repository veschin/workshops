(ns solution
  (:require [reagent.core :as r]))

;; Reagent atom с иммутабельными данными
(def state (r/atom {:count 0 :user {:name "Alice" :age 25}}))

(defn reactivity-footguns-solution []
  ;; Деструктуризация работает корректно с иммутабельными данными
  ;; Каждый раз создаются новые значения при изменении state
  (let [{:keys [count user]} @state
        {:keys [name age]} user]
    [:div
     [:h3 "ClojureScript Immutable Data Solution"]
     [:p "Count: " count]
     [:p "User name: " name]
     [:p "User age: " age]
     [:button {:on-click #(swap! state update :count inc)} "Increment Count"]
     [:button {:on-click #(swap! state assoc :user {:name "Bob" :age 30})} "Update User"]
     [:p.small "🎯 Vue: деструктуризация ломает реактивность"]
     [:p.small "🎯 ClojureScript: иммутабельность гарантирует согласованность"]]))