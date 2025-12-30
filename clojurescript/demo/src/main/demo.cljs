(ns main.demo
  (:require [reagent.core :as r]))

(defonce state
  (r/atom {:count 0
           :message "Hello!"
           :color "#6366f1"
           :items []
           :multiplier 1}))

(defn display-block []
  (let [animate? (r/atom false)]
    (r/create-class
     {:component-did-update
      (fn []
        (reset! animate? true)
        (js/setTimeout #(reset! animate? false) 200))
      :reagent-render
      (fn []
        (let [{:keys [count message multiplier items]} @state]
          [:div.display
           [:div.count {:class (when @animate? "animate")} (* count multiplier)]
           [:div.message message]
           [:div.stats
            [:div
             [:span count]
             "Base"]
            [:div
             [:span multiplier]
             "x Mult"]
            [:div
             [:span (count items)]
             "Items"]]
           [:div.items
            (for [i (reverse (take 5 items))]
              ^{:key i}
              [:div.item i])]]))})))

(defn button [text on-click class]
  [:button {:on-click on-click :class class} text])

(defn controls []
  [:div.buttons
   [button "+1" #(swap! state update :count inc)]
   [button "+10" #(swap! state update :count + 10)]
   [button "×2" #(swap! state update :multiplier * 2)]
   [button "÷2" #(swap! state update :multiplier (fn [x] (max 1 (quot x 2))))]
   [button "🎲 Random" #(swap! state update :count + (inc (rand-int 100)))]
   [button "🎯 Set 100" #(swap! state assoc :count 100)]
   [button "✨ Add Msg" #(swap! state update :items conj (str "Msg " (rand-int 1000)))]
   [button "🧹 Clear Items" #(swap! state assoc :items [])]
   [button "🔄 Reset" #(swap! state assoc :count 0 :multiplier 1 :items []) :class "full"]
   [button "📢 Change Message" #(swap! state assoc :message (["Awesome!" "Nice!" "Cool!" "Great!"] (rand-int 4))) :class "full"]])

(defn app []
  [:div.container
   [display-block]
   [controls]])

(defn ^:dev/after-load mount-root []
  (r/clear-cache!)
  (r/render [app] (js/document.getElementById "app")))

(defn init []
  (mount-root))
