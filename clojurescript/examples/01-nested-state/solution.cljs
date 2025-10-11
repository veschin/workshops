(ns 01-nested-state.solution
  (:require [reagent.core :as r]))

(def state (r/atom {:user {:profile {:settings {:theme "light" :notifications true :language "en" :privacy {:enabled true}}}}))

(defn update-theme [theme] (swap! state assoc-in [:user :profile :settings :theme] theme))
(defn toggle-notifications [] (swap! state update-in [:user :profile :settings :notifications] not))

(defn nested-state-solution []
  (let [{:keys [theme notifications language privacy]} (get-in @state [:user :profile :settings])
        {:keys [enabled]} privacy]
    [:div
     [:h3 "ClojureScript Nested State Solution (5 levels)"]
     [:p "Theme: " theme]
     [:p "Notifications: " (if notifications "On" "Off")]
     [:p "Language: " language]
     [:p "Privacy Enabled: " (if enabled "Yes" "No")]
     [:button {:on-click #(update-theme "dark")} "Set Dark Theme"]
     [:button {:on-click toggle-notifications} "Toggle Notifications"]
     [:button {:on-click #(swap! state assoc-in [:user :profile :settings :language] "ru")} "Set Language RU"]
     [:button {:on-click #(swap! state update-in [:user :profile :settings :privacy :enabled] not)} "Toggle Privacy"]]))