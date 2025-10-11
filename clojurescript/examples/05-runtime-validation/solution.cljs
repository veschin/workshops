(ns solution
  (:require [cljs.spec.alpha :as s]))

;; ClojureSpec - встроенная валидация данных во время выполнения
;; Декларативные спецификации с подробными сообщениями об ошибках
(s/def ::name string?)
(s/def ::age (s/and int? #(<= 0 % 150)))
(s/def ::email (s/and string? #(re-matches #".+@.+\..+" %)))
(s/def ::user (s/keys :req-un [::name ::age ::email]))

;; Валидация с объяснением ошибок
(defn validate-user [data]
  (if (s/valid? ::user data)
    data
    (do
      (println "❌ Invalid user:" data)
      (println "🔍 Errors:" (s/explain-str ::user data))
      {:error "Invalid user data" :details (s/explain-str ::user data)})))

(def test-users
  [{:name "Alice" :age 25 :email "alice@example.com"}  ; ✅ Valid
   {:name "Bob" :age "30" :email "bob"}                ; ❌ Age not int, email invalid
   {:name "Charlie"}                                   ; ❌ Missing age, email
   {:name "Dave" :age 25 :email "dave@example.com"}]) ; ✅ Valid

(defn runtime-validation-solution []
  [:div
   [:h3 "ClojureScript Spec Validation Solution"]
   (for [user test-users]
     (let [validation-result (validate-user user)
           is-valid (s/valid? ::user user)]
       [:div {:key (str user)}
        [:p "User: " (pr-str user)]
        [:p "✅ Valid? " is-valid]
        (if is-valid
          [:p "✅ Validated: " (pr-str validation-result)]
          [:p "❌ Errors: " (:details validation-result)])]))
   [:p.small "🎯 TypeScript: compile-time only, runtime validation требует внешних библиотек"]
   [:p.small "🎯 ClojureScript: spec/malli встроенные, декларативные, с детальными ошибками"]]))