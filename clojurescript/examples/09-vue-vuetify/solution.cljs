(ns 09-vue-vuetify.solution
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            ["vuetify/lib/components/VBtn/index.mjs" :default VBtn]
            ["vuetify/lib/components/VTextField/index.mjs" :default VTextField]))

(def name-atom (r/atom ""))

(defn submit []
  (println "Submitted:" @name-atom))

(defn vuetify-wrapper [props & children]
  (into [:> VBtn (clj->js (assoc props :variant "flat"))] children))

(defn form []
  [:div
   [:h3 "CLJS Vuetify Interop"]
   [:> VTextField {:label "Name"
                   :model-value @name-atom
                   :on-update:model-value #(reset! name-atom %)}]
   [vuetify-wrapper {:on-click submit} "Submit"]
   [:p "CLJS wraps Vuetify (Vue lib) via JS interop, flexible for any npm pkg."]])

(defn mount []
  (rdom/render [form] (.getElementById js/document "app")))

(mount)