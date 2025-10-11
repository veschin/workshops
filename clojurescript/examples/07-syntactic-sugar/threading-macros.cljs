(ns syntactic-sugar.threading-macros)

(def data {:users [{:name "Alice" :age 25 :city "NYC"} {:name "Bob" :age 30 :city "LA"} {:name "Charlie" :age 35 :city "NYC"}]})

(defn first-user-name [data] (-> data :users first :name))
(defn adult-names [data] (->> data :users (filter #(>= (:age %) 30)) (map :name) sort))
(defn safe-city [id data] (some-> data :users (nth id) :city))
(defn format-user [user city?] (cond-> {:name (:name user) :age (:age user)} city? (assoc :city (:city user))))
(defn calc [nums] (as-> nums $ (filter even? $) (map #(* % %) $) (reduce + $) (/ $ (count nums))))

(println "First name:" (first-user-name data))
(println "Adults:" (adult-names data))
(println "Safe city:" (safe-city 0 data) (safe-city 10 data))
(println "User:" (format-user (first (:users data)) true))
(println "Calc:" (calc [1 2 3 4 5 6]))