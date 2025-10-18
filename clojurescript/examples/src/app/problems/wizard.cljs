(ns app.problems.wizard
  (:require [reagent.core :as r]))

(def form-state (r/atom {:step 0 :data {:step1 {} :step2 {} :step3 {}} :progress 0}))

(defn validate-step [step data]
  (case step
    0 (not-empty (:step1 data))
    1 (not-empty (:step2 data))
    2 true))

(defn wizard []
  (let [update-step! (fn [s] (swap! form-state assoc :step s :progress (/ (* s 33) 1)))
        update-data! (fn [path val] (swap! form-state update-in (conj [:data] path) merge val))]
    (fn []
      (let [{:keys [step data progress]} @form-state]
        [:div.wizard
         [:div.progress {:style {:width (str progress "%")}}]
         (case step
           0 [:div [:input {:on-change #(update-data! [:step1 :name] (.. % -target -value))}]
              [:button {:on-click #(when (validate-step 0 (:step1 data)) (update-step! 1))} "Next"]]
           1 [:div [:input {:on-change #(update-data! [:step2 :email] (.. % -target -value))}]
              [:button {:on-click #(update-step! 0)} "Back"] [:button {:on-click #(update-step! 2)} "Next"]]
           2 [:div (str "Name from step1: " (-> data :step1 :name))
              [:button {:on-click #(update-step! 1)} "Back"] [:button {:on-click #(js/console.log "Save draft")} "Save"]])]))))

(defn wizard-page []
  [:div
   [:h2 "Multi-step Form (Wizard)"]
   [wizard]])