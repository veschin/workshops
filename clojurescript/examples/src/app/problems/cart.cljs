(ns app.problems.cart
  (:require [reagent.core :as r]
            [clojure.string :as str]))

(def cart (r/atom #{}))

(defn add-to-cart [item]
  (swap! cart conj item))

(defn remove-from-cart [item]
  (swap! cart disj item))

(defn header-counter []
  [:div.header [:span "Cart: " (count @cart)]])

(defn cart-page []
  [:div.cart
   (for [item @cart]
     ^{:key item}
     [:div [:button {:on-click #(remove-from-cart item)} "Remove"]])
   [header-counter]])

(defn product-page []
  [:div
   [:h2 "Shopping Cart Counter"]
   [:div.mb-4
    [:button.bg-green-500.hover:bg-green-700.text-white.px-4.py-2.rounded.mr-2 {:on-click #(add-to-cart :item1)} "Add Item 1"]
    [:button.bg-green-500.hover:bg-green-700.text-white.px-4.py-2.rounded.mr-2 {:on-click #(add-to-cart :item2)} "Add Item 2"]
    [:button.bg-green-500.hover:bg-green-700.text-white.px-4.py-2.rounded.mr-2 {:on-click #(add-to-cart :item3)} "Add Item 3"]]
   [:div.bg-gray-100.p-4.rounded.mb-4 [header-counter]]
   [:div.bg-white.border.border-gray-200.rounded.p-4
    [:h3 "Cart Items"]
    (if (empty? @cart)
      [:p.text-gray-500 "Cart is empty"]
      (for [item @cart]
        ^{:key item}
        [:div.flex.justify-between.items-center.mb-2
         [:span (str "Item " (str/capitalize (name item)))]
         [:button.bg-red-500.hover:bg-red-700.text-white.px-3.py-1.rounded.text-sm {:on-click #(remove-from-cart item)} "Remove"]]))
    [header-counter]]])