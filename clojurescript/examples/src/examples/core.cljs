(ns examples.core
  (:require [reagent.core :as r]
            [reagent.dom :as dom]
            [app.problems.cart :as cart]
            [app.problems.theme :as theme]
            [app.problems.notifications :as notifications]
            [app.problems.modals :as modals]
            [app.problems.wizard :as wizard]
            [app.problems.breadcrumbs :as breadcrumbs]
            [app.problems.search :as search]
            [app.problems.realtime :as realtime]))

(defonce current-page (r/atom :home))

(defn navigate-to [page-id]
  (reset! current-page page-id)
  (let [path (if (= page-id :home) "/" (str "/" (name page-id)))]
    (.pushState js/history #js {} "" path)))

(defn handle-popstate [event]
  (let [path (.-pathname js/location)
        page-id (if (= path "/") :home (keyword (subs path 1)))]
    (reset! current-page page-id)))

(def problems
  {:cart {:id :cart
          :title "Счётчик корзины покупок"
          :description "Синхронизация состояния между несвязанными компонентами. Счётчик в header обновляется при изменении корзины."
          :component cart/product-page}

   :theme {:id :theme
           :title "Переключатель темы"
           :description "Глобальное изменение темы с синхронизацией в localStorage и реактивным обновлением всего интерфейса."
           :component theme/theme-page}

   :notifications {:id :notifications
                   :title "Система уведомлений"
                   :description "Toast-уведомления доступные из любой точки приложения с управлением очередью и автоудалением."
                   :component notifications/notifications-page}

   :modals {:id :modals
            :title "Состояние модальных окон"
            :description "Управление модальными окнами из любого места приложения. Координация между множеством компонентов."
            :component modals/modals-page}

   :wizard {:id :wizard
            :title "Многошаговая форма (Wizard)"
            :description "Передача данных между шагами, валидация каждого этапа, навигация с сохранением информации."
            :component wizard/wizard-page}

   :breadcrumbs {:id :breadcrumbs
                 :title "Навигация хлебными крошками"
                 :description "Динамическое построение breadcrumbs с синхронизацией URL, истории браузера и состояния навигации."
                 :component breadcrumbs/breadcrumbs-page}

   :search {:id :search
            :title "Поиск и фильтрация"
            :description "Синхронизация поисковой строки с фильтрацией данных в реальном времени и интеграция с URL."
            :component search/search-page}

   :realtime {:id :realtime
              :title "Обновления в реальном времени"
              :description "Совместное редактирование с оптимистичными обновлениями, разрешением конфликтов и синхронизацией."
              :component realtime/realtime-page}})

(defn nav-item []
  [:nav.bg-blue-600.text-white.p-4.mb-8
   [:div.max-w-6xl.mx-auto.flex.justify-between.items-center
    [:h1.text-xl.font-bold "ClojureScript Workshop"]
    [:button.bg-blue-500.hover:bg-blue-700.px-4.py-2.rounded.transition-colors
     {:on-click #(reset! current-page :home)}
     "На главную"]]])

(defn problem-card [{:keys [id title description]}]
  [:div.bg-white.border.border-gray-200.rounded-lg.p-6.shadow-md.hover:shadow-lg.transition-shadow.cursor-pointer
   {:on-click #(reset! current-page id)}
   [:div.flex.items-center.mb-4
    [:div.w-12.h-12.bg-blue-600.text-white.rounded-full.flex.items-center.justify-center.font-bold.mr-4
     (inc (.indexOf (keys problems) id))]
    [:h3.text-xl.font-semibold title]]
   [:p.text-gray-600 description]
   [:div.mt-4.text-sm.text-blue-600.font-medium "Нажмите чтобы открыть пример →"]])

(defn home-page []
  [:div.max-w-6xl.mx-auto.p-6
   [:div.mb-8
    [:h1.text-4xl.font-bold.mb-2 "ClojureScript Workshop"]
    [:p.text-xl.text-gray-600.mb-4 "Типичные проблемы взаимодействия компонентов"]

    [:div.bg-blue-50.border-l-4.border-blue-600.p-4.mb-6
     [:h2.text-lg.font-semibold.mb-2 "Реализации на ClojureScript + Reagent"]
     [:p.text-gray-700 "Все примеры реализованы с использованием ClojureScript и библиотеки Reagent. Нажмите на карточку чтобы открыть интерактивный пример."]]]

   [:div.grid.grid-cols-1.md:grid-cols-2.lg:grid-cols-3.gap-6
    (for [[_ problem] problems]
      ^{:key (:id problem)}
      [problem-card problem])]])

(defn problem-page [problem-id]
  (let [problem (get problems problem-id)]
    (if problem
      [:div.max-w-6xl.mx-auto.p-6
       [nav-item]
       [:div.mb-6
        [:h2.text-3xl.font-bold.mb-2 (:title problem)]
        [:p.text-gray-600.mb-4 (:description problem)]]
       [:div.bg-white.border.border-gray-200.rounded-lg.p-6
        [(:component problem)]]]
      [:div.max-w-6xl.mx-auto.p-6
       [nav-item]
       [:div.text-center.py-12
        [:h2.text-2xl.font-bold.mb-4 "Пример не найден"]
        [:p.text-gray-600.mb-4 "Запрошенный пример не существует."]
        [:button.bg-blue-600.hover:bg-blue-700.text-white.px-6.py-2.rounded.transition-colors
         {:on-click #(reset! current-page :home)}
         "Вернуться на главную"]]])))

(defn app []
  [:div.min-h-screen.bg-gray-50
   (case @current-page
     :home [home-page]
     [problem-page @current-page])])

(defn ^:export start []
  (dom/render [app] (.getElementById js/document "app")))

(defn ^:export stop []
  (js/console.log "App stopped"))

;; Initialize the app
(start)