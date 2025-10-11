(ns solution-shared)

;; Общая логика валидации для JVM (backend) и JS (frontend)
;; .cljc файлы компилируются для обеих платформ
(defn valid-email? [email]
  (boolean (re-matches #"[^\s@]+@[^\s@]+\.[^\s@]+" email)))

(defn valid-user? [user]
  (and (map? user)
       (int? (:id user))
       (string? (:name user))
       (>= (count (:name user)) 2)
       (string? (:email user))
       (valid-email? (:email user))
       (#{"admin" "user"} (:role user))))

;; Бизнес-правила работают одинаково на клиенте и сервере
(defn can-edit-post? [user post-author-id]
  (or (= "admin" (:role user))
      (= (:id user) post-author-id)))

;; Уровни доступа - единая логика для frontend/backend
(defn user-permission-level [user]
  (case (:role user)
    "admin" :full-access
    "user" :limited-access
    :no-access))

;; Трансформация данных - общий код для обеих платформ
(defn user-summary [user]
  {:id (:id user)
   :name (:name user)
   :role (:role user)})

;; Весь этот код работает на Clojure (backend) и ClojureScript (frontend)
;; Нет дублирования логики валидации и бизнес-правил!