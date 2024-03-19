(ns db
  (:require [clojure.java.jdbc :as jdbc]
            [honeysql.core :as hsql]
            [toucan.db :as t.db]
            [toucan.models :as t.m]))

(def db-spec
  {:dbtype   "postgresql"
   :dbname   "workshop"
   :user     "postgres"
   :password "postgres"})

;; ----JDBC + Honey

(def fruit-table-ddl
  (jdbc/create-table-ddl
   :fruit
   [[:id :serial]
    [:name :text]
    [:cost :int]
    [:grade :text]]))

(comment
  (jdbc/db-do-commands
   db-spec
   ["DROP TABLE IF EXISTS fruit;"
    fruit-table-ddl])

  (jdbc/insert! db-spec :table {:col1 42 :col2 "123"})               ;; Create
  (jdbc/query   db-spec ["SELECT * FROM table WHERE id = ?" 13])     ;; Read
  (jdbc/update! db-spec :table {:col1 77 :col2 "456"} ["id = ?" 13]) ;; Update
  (jdbc/delete! db-spec :table ["id = ?" 13])                        ;; Delete

  (jdbc/query
   db-spec
   (hsql/format
    {:select [:*]
     :from   [:fruit]}))

  ;;
)


;; ----TOUCAN

(t.db/set-default-db-connection! db-spec)
(declare Fruit)
(t.m/defmodel Fruit :fruit)

(comment

  (t.db/select Fruit)
  (t.db/insert! Fruit {:name  "orange"
                       :cost  100
                       :grade "high"})
  (t.db/update-where! Fruit {:name "orange"} {:cost 200})
  (t.db/delete! Fruit)
  (t.db/delete! Fruit :id 1)

  ;;
  )
