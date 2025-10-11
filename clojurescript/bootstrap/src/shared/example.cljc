(ns shared.example)

;; This is a shared namespace that works on both Clojure and ClojureScript
;; You can put common business logic, validation, and utilities here

(defn greet [name]
  (str "Hello, " name "!"))

;; This code runs identically on JVM (backend) and JS (frontend)