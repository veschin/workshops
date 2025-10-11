(ns 06-shared-code.solution-frontend
  (:require [06-shared-code.solution-shared :as shared]
            [reagent.core :as r]))

(def user-a (r/atom {:id 1 :name "Alice" :email "alice@example.com" :role "admin"}))

(defn user-interface []
  (let [user @user-a]
    [:div
     [:h3 "Frontend - Shared Code Solution"]
     [:p "User: " (pr-str (shared/user-summary user))]
     [:p "Level: " (shared/user-permission-level user)]
     [:p "Can edit post 2? " (shared/can-edit-post? user 2)]
     [:p "Can edit post 1? " (shared/can-edit-post? user 1)]]))