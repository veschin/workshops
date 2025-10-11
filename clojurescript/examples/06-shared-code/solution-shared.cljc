(ns solution-shared)

;; Shared validation logic that works on both JVM and JS
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

(defn can-edit-post? [user post-author-id]
  (or (= "admin" (:role user))
      (= (:id user) post-author-id)))

;; Business rules that work identically on client and server
(defn user-permission-level [user]
  (case (:role user)
    "admin" :full-access
    "user" :limited-access
    :no-access))

;; Data transformation logic
(defn user-summary [user]
  {:id (:id user)
   :name (:name user)
   :role (:role user)})

;; All this code runs on both Clojure (backend) and ClojureScript (frontend)