(ns app.problems.notifications
  (:require [reagent.core :as r]))

(def notifications (r/atom []))

(defn add-notification [type msg]
  (let [id (random-uuid)]
    (swap! notifications conj {:id id :type type :msg msg})
    (js/setTimeout #(swap! notifications (fn [ns] (filterv (comp not #{id}) ns))) 3000)
    (when (> (count @notifications) 3)
      (swap! notifications (partial take 3)))))

(defn dismiss-all []
  (reset! notifications []))

(defn notification []
  [:div.toasts
   (for [{:keys [id type msg]} @notifications]
     ^{:key id}
     [:div {:class (str "toast " (name type)) :on-click #(swap! notifications (fn [ns] (filterv (comp not #{id}) ns)))}
      msg])
   [:button {:on-click dismiss-all} "Clear"]])

(defn notifications-page []
  [:div
   [:h2 "Notification System"]
   [:div.mb-4
    [:button.bg-green-500.hover:bg-green-700.text-white.px-4.py-2.rounded.mr-2 {:on-click #(add-notification :success "Success! This is a success message.")} "Add Success"]
    [:button.bg-red-500.hover:bg-red-700.text-white.px-4.py-2.rounded.mr-2 {:on-click #(add-notification :error "Error! This is an error message.")} "Add Error"]
    [:button.bg-gray-500.hover:bg-gray-700.text-white.px-4.py-2.rounded {:on-click dismiss-all} "Clear All"]]
   [notification]])