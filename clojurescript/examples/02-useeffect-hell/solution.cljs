(ns 02-useeffect-hell.solution
  (:require [reagent.core :as r]))

;; Reagent атомы - реактивное состояние без зависимостей
(def count-a (r/atom 0))
(def mult-a (r/atom 2))

;; WebSocket example - демонстрация работы с внешними событиями
(def ws-messages (r/atom []))
(def ws-conn (r/atom nil))

(defn connect-ws []
  (let [ws (js/WebSocket. "wss://echo.websocket.org")]
    (set! (.-onopen ws) #(println "WS connected"))
    (set! (.-onmessage ws) #(swap! ws-messages conj (.-data %)))
    (set! (.-onclose ws) #(println "WS closed"))
    (reset! ws-conn ws)))

(defn simulate-ws-message []
  (swap! ws-messages conj (str "Simulated at " (js/Date.))))

;; add-watch - реактивность без useEffect зависимостей
;; Автоматически отслеживает изменения атома без ручного управления
(add-watch ws-messages :ui #(println "New message received"))

(connect-ws)

;; Побочные эффекты через add-watch - не нужно управлять зависимостями
(defn update-title []
  (set! (.-title js/document) (str "Count: " (* @count-a @mult-a)))
  (println "Product:" (* @count-a @mult-a)))

;; Добавляем вотчеры для нескольких атомов - нет stale closures!
(doseq [a [count-a mult-a]]
  (add-watch a :logger #(println (name %1) "changed:" %4))
  (add-watch a :updater (fn [_ _ _ _] (update-title))))

(defn useeffect-hell-solution []
  [:div
   [:h3 "ClojureScript add-watch Solution"]
   [:p "Count: " @count-a]
   [:p "Multiplier: " @mult-a]
   [:p "Product: " (* @count-a @mult-a)]
   [:button {:on-click #(swap! count-a inc)} "Increment Count"]
   [:button {:on-click #(swap! mult-a inc)} "Increase Multiplier"]
   [:h4 "WebSocket Demo"]
   [:p "Messages count: " (count @ws-messages)]
   [:button {:on-click simulate-ws-message} "Simulate WS Message"]
   [:p.small "🎯 React: useEffect зависимости, stale closures, infinite loops"]
   [:p.small "🎯 ClojureScript: add-watch автоматически отслеживает изменения"]])