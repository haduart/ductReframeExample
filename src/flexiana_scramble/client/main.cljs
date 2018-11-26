(ns flexiana-scramble.client.main
  (:require
    [reagent.core :as reagent]
    [re-frame.core :as re-frame]
    [flexiana-scramble.client.events]
    [flexiana-scramble.client.subs]
    [flexiana-scramble.client.scrambles.components :refer [body]]
    [flexiana-scramble.client.scrambles.events]))


(def init-db
  {:scrambles {:new-first-word  nil
               :new-second-word nil
               :response        nil
               :errors          nil}})


(defn- main []
  (reagent/create-class
    {:display-name "main"

     :reagent-render
                   (fn []
                     (let [app (re-frame/subscribe [:app])]
                       (fn []
                         (body @app))))}))


(defn ^:export init
  []
  (let [app-element (.getElementById js/document "app")]
    (re-frame/dispatch-sync [:init-db init-db])
    (re-frame/clear-subscription-cache!)
    (reagent/render [main] app-element)))

