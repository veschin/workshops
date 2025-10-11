(ns 08-live-coding.todo-app
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]))

(def todos (r/atom []))

(defn add-todo [text]
  (let [id (str (js/Date.))]
    (swap! todos conj {:id id :text text :done false})))

(defn toggle-todo [id]
  (swap! todos (fn [ts] (mapv (fn [t] (if (= (:id t) id) (update t :done not) t)) ts))))

(defn delete-todo [id]
  (swap! todos (fn [ts] (filterv #(not= (:id %) id) ts))))

(defn filter-todos [filter-val]
  (case filter-val
    :all @todos
    :active (filterv (comp not :done) @todos)
    :completed (filterv :done @todos)))

;; Persistence
(defn save-todos []
  (.setItem (.-localStorage js/window) "todos" (pr-str @todos)))

(defn load-todos []
  (let [saved (.getItem (.-localStorage js/window) "todos")]
    (when saved
      (reset! todos (read-string saved)))))

(load-todos)
(add-watch todos :persist (fn [_ _ _ _] (save-todos)))

(defn todo-item [{:keys [id text done]}]
  [:li {:class (if done "completed" "")}
   [:input {:type "checkbox" :checked done :on-change #(toggle-todo id)}]
   [:span text]
   [:button {:on-click #(delete-todo id)} "Delete"]])

(defn todo-list []
  (let [filter-val (r/atom :all)]
    (fn []
      [:div
       [:input {:type        "text"
                :placeholder "Add todo"
                :on-key-down #(when (= "Enter" (.-key %))
                                (let [val (.-value (.-target %))]
                                  (when (not-empty val)
                                    (add-todo val)))
                                (set! (.-value (.-target %)) ""))}]
       [:ul (map todo-item (filter-todos @filter-val))]
       [:div
        [:button {:on-click #(reset! filter-val :all)} "All"]
        [:button {:on-click #(reset! filter-val :active)} "Active"]
        [:button {:on-click #(reset! filter-val :completed)} "Completed"]]])))

(defn mount []
  (rdom/render [todo-list] (.getElementById js/document "app")))

(mount)
