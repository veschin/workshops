(ns syntactic-sugar.destructuring)

(def user {:name "Alice" :profile {:age 25 :address {:city "NYC" :country "USA"}} :hobbies ["reading" "coding" "gaming"]})

(let [{:keys [name profile]} user
      {:keys [age address]} profile
      {:keys [city]} address
      [first-hobby & rest] (:hobbies user)]
  (println name "is" age "in" city)
  (println "Hobbies:" first-hobby rest))

(defn greet [{:keys [name profile]}]
  (let [{:keys [age]} profile]
    (str "Hello " name ", " age " years old")))

(defn process [data & {:keys [filter-fn sort-fn limit]}]
  (->> data
       (filter filter-fn)
       (sort sort-fn)
       (take limit)
       println))

(println (greet user))
(process [1 2 3 4 5] :filter-fn even? :sort-fn > :limit 2)