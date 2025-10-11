(ns solution
  (:require [cljs.spec.alpha :as s]))

(s/def ::name string?)
(s/def ::age (s/and int? #(<= 0 % 150)))
(s/def ::email (s/and string? #(re-matches #".+@.+\..+" %)))
(s/def ::user (s/keys :req-un [::name ::age ::email]))

(defn validate-user [data]
  (when-not (s/valid? ::user data)
    (println "Invalid:" data "Errors:" (s/explain-str ::user data))
    nil))

(def users
  [{:name "Alice" :age 25 :email "alice@example.com"}
   {:name "Bob" :age "30" :email "bob"}
   {:name "Charlie"}])

(defn runtime-validation-solution []
  [:div
   [:h3 "ClojureScript Spec Validation Solution"]
   (for [user users]
     [:div {:key (str user)}
      [:p (pr-str user)]
      [:p "Valid? " (s/valid? ::user user)]
      [:p "Validated: " (pr-str (validate-user user))]])])