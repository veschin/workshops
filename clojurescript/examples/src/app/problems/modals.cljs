(ns app.problems.modals
  (:require [reagent.core :as r]))

(def modals (r/atom {}))

(defn open-modal [id data]
  (swap! modals assoc id {:open true :data data}))

(defn close-modal [id]
  (swap! modals update id dissoc :open))

(defn modal [{:keys [id open? data on-submit]}]
  (when open?
    [:div.modal-overlay
     [:div.modal
      [on-submit data]
      [:button {:on-click #(close-modal id)} "Close"]]]))

(defn modals-root []
  [:div
   (for [[id {:keys [open data]}] @modals]
     ^{:key id}
     [modal {:id id :open? open :data data :on-submit (fn [d] (js/console.log "Submit" d))}])])

(defn modals-page []
  [:div
   [:h2 "Modal Management"]
   [:div.mb-4
    [:button.bg-blue-500.hover:bg-blue-700.text-white.px-4.py-2.rounded.mr-2 {:on-click #(open-modal :modal1 {:msg "Hello from Modal 1"})} "Open Modal 1"]
    [:button.bg-purple-500.hover:bg-purple-700.text-white.px-4.py-2.rounded {:on-click #(open-modal :modal2 {:msg "Hello from Modal 2"})} "Open Modal 2"]]
   [modals-root]])