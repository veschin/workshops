(ns parse
  (:require
   [clojure.string :as str])
  (:import
   (org.jsoup Jsoup)))

(def links {:google "https://google.com"
            :github "https://github.com"
            :ya     "https://ya.ru"})

(defn parse-n-spit! [path url]
  (let [html        (.get (Jsoup/connect url))
        split-words #(str/split % #" ")]
    (->> (.select html "p")
         (.text)
         (split-words)
         (partition-all 5)
         (map #(str/join " " %))
         (str/join "\n")
         (spit path))))

(doseq [[k url] links
        :let [path (str "resources/sites/" (name k))]]
  (future
    (parse-n-spit! path url)))
