(ns syntactic-sugar.composition)

(def process-num (comp #(/ % 2) #(* % 3) inc))
(def add-five (partial + 5))
(def get-stats (juxt count #(apply + %) #(apply min %) #(apply max %)))

(def numbers [1 2 3 4 5])

(println "Process 10:" (process-num 10))
(println "Add five:" (add-five 3))
(println "Stats:" (get-stats numbers))

(defn score [user] (->> user :stats (select-keys [:points :bonus]) vals (apply +) (* 1.1) int))
(def users [{:name "Alice" :stats {:points 100 :bonus 20}} {:name "Bob" :stats {:points 80 :bonus 15}}])

(def process-scores (comp #(map score %) #(filter (fn [u] (>= (:points (:stats u)) 50)) %) #(sort-by (fn [u] (- (:points (:stats u)))) %)))
(println "Scores:" (process-scores users))