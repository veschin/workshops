(ns 01-nested-state.solution
  (:require [reagent.core :as r]))

;; Reagent atom - автоматически вызывает ререндер компонентов при изменении
;; Иммутабельные данные - assoc-in создает новые структуры вместо мутации
(def state (r/atom {:user {:profile {:settings {:theme "light" :notifications true :language "en" :privacy {:enabled true}}}}))

;; Декларативное обновление вложенного состояния за 1 строку
;; assoc-in: [путь значение] - обновляет значение по вложенному пути
;; update-in: [путь функция] - применяет функцию к значению по пути
(defn update-theme [theme] 
  (swap! state assoc-in [:user :profile :settings :theme] theme))
(defn toggle-notifications [] 
  (swap! state update-in [:user :profile :settings :notifications] not))

(defn nested-state-solution []
  ;; get-in извлекает вложенное значение по пути - декларативно и безопасно
  ;; Деструктуризация работает корректно с иммутабельными данными
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
     [:button {:on-click #(swap! state update-in [:user :profile :settings :privacy :enabled] not)} "Toggle Privacy"]
     [:p.small "🎯 React/Vue требуют многословный spread синтаксис для каждого уровня вложенности"]
     [:p.small "🎯 ClojureScript: 1 строка с assoc-in/update-in для любой глубины"]]))