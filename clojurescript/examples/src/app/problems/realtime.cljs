(ns app.problems.realtime
  (:require [reagent.core :as r]))

(def todos (r/atom #{{:id 1 :text "Task" :done false}}))

(defn simulate-realtime-update []
  (let [new-id (inc (apply max (map :id @todos)))]
    (swap! todos conj {:id new-id
                       :text (str "Simulated task " new-id)
                       :done false})))

(defn toggle-todo [id]
  (let [optimistic {:id id :done true}]
    (swap! todos (fn [ts] (conj (disj ts (first (filter #(= (:id %) id) ts))) optimistic)))
    (js/setTimeout simulate-realtime-update 1000)))

(defn checklist []
  [:ul
   (for [{:keys [id text done]} @todos]
     ^{:key id}
     [:li [:input {:type "checkbox" :checked done :on-change #(toggle-todo id)}] text])])

(defn realtime-page []
  [:div
   [:h2 "Real-time Updates"]
   [:p.text-gray-600.mb-4 "Simulated real-time collaboration (click tasks to see simulated updates)"]
   [:div.mb-4
    [:button.bg-blue-500.hover:bg-blue-700.text-white.px-4.py-2.rounded.mr-2 {:on-click simulate-realtime-update} "Add Simulated Task"]]
   [checklist]])