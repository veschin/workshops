(ns 06-shared-code.backend-solution
  (:require [06-shared-code.solution-shared :as shared]))

;; Mock database
(def users-db (atom {1 {:id 1 :name "Alice" :email "alice@example.com" :role "admin"}}))

(defn get-user [user-id]
  (get @users-db user-id))

(defn handle-edit-post [user-id post-id post-author-id]
  (let [user (get-user user-id)]
    (cond
      (not (shared/valid-user? user))
      {:error "Invalid user"}

      (not (shared/can-edit-post? user post-author-id))
      {:error "Permission denied"}

      :else
      (do
        ;; Edit post logic...
        (println "Post" post-id "edited by user" user-id)
        {:success true :message "Post edited successfully"}))))

;; API endpoint
(defn api-handler [request]
  (let [user-id (get-in request [:params :user-id])
        post-id (get-in request [:params :post-id])
        post-author-id 2] ; Mock post author ID - в реальном приложении из базы данных
    (handle-edit-post user-id post-id post-author-id)))

;; Example usage
(println "API response:" (api-handler {:params {:user-id 1 :post-id 123}}))
;; Output: {:success true, :message "Post edited successfully"}

;; Same validation logic as frontend, no duplication!