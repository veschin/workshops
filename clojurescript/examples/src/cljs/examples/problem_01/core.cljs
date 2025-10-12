(ns examples.problem-01.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]))

;; Глобальное состояние - просто atom
(defonce cart (r/atom []))

;; Доступные товары
(def products
  [{:id 1 :name "MacBook Pro" :price 2499}
   {:id 2 :name "iPhone 15" :price 999}
   {:id 3 :name "AirPods Pro" :price 249}
   {:id 4 :name "iPad Air" :price 599}])

;; Функции для работы с корзиной
(defn add-to-cart [product]
  (swap! cart conj product))

(defn remove-from-cart [index]
  (swap! cart (fn [items]
                (vec (concat (subvec items 0 index)
                            (subvec items (inc index)))))))

(defn cart-total []
  (->> @cart
       (map :price)
       (reduce + 0)))

;; Header с счётчиком
(defn header-component []
  [:header
   [:h2 "Интернет-магазин"]
   [:div
    [:span "Корзина: "]
    [:span.badge (count @cart)]
    [:span " товаров"]
    (when (pos? (count @cart))
      [:span {:style {:margin-left "1rem"
                      :color "var(--success-color)"
                      :font-weight "bold"}}
       "$" (cart-total)])]])

;; Список товаров
(defn product-list []
  [:section
   [:h3 "Каталог товаров"]
   [:ul
    (for [product products]
      ^{:key (:id product)}
      [:li {:style {:display "flex"
                    :justify-content "space-between"
                    :align-items "center"}}
       [:div
        [:strong (:name product)]
        [:span {:style {:margin-left "1rem" :color "#6b7280"}}
         "$" (:price product)]]
       [:button {:on-click #(add-to-cart product)}
        "Добавить в корзину"]])]])

;; Корзина
(defn cart-component []
  [:section
   [:h3 "Корзина покупок"]
   (if (empty? @cart)
     [:p "Корзина пуста"]
     [:div
      [:ul
       (map-indexed
        (fn [idx item]
          ^{:key idx}
          [:li {:style {:display "flex"
                        :justify-content "space-between"
                        :align-items "center"}}
           [:div
            [:strong (:name item)]
            [:span {:style {:margin-left "1rem" :color "#6b7280"}}
             "$" (:price item)]]
           [:button {:on-click #(remove-from-cart idx)
                     :style {:background "var(--danger-color)"}}
            "Удалить"]])
        @cart)]
      [:div {:style {:margin-top "1rem"
                     :padding-top "1rem"
                     :border-top "2px solid var(--border-color)"
                     :font-size "1.25rem"
                     :font-weight "bold"}}
       "Итого: $" (cart-total)]])])

;; Главный компонент
(defn app []
  [:div
   [header-component]
   [:div {:style {:display "grid"
                  :grid-template-columns "1fr 1fr"
                  :gap "2rem"
                  :margin-top "2rem"}}
    [product-list]
    [cart-component]]])

;; Инициализация
(defn ^:dev/after-load start []
  (rdom/render [app]
               (js/document.getElementById "app")))

(defn ^:export init []
  (start))
