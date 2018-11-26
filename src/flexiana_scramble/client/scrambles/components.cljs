(ns flexiana-scramble.client.scrambles.components
  (:require
    [goog.events.KeyCodes :as KeyCodes]
    [re-frame.core :as re-frame]))

(defn- input-box [placeholder dispatch-event value]
  [:input {:placeholder placeholder
           :type        "text"
           :auto-focus  true
           :value       value
           :on-change   #(re-frame/dispatch [dispatch-event (-> % .-target .-value)])}])

(defn- button []
  [:button {:type     "submit"
            :disabled false
            :on-click #(re-frame/dispatch [:scrambles/check-scramble])}
   "Check Scramble"])

(defn- display-message [response]
  (when (not (nil? response))
    (if response
      [:h2.success "True!"]
      [:h2 "False..."])))

(defn display-error [error]
  (when (not (nil? error))
      [:h2 "Words should contain only alphanumeric characters, without numbers or symbols"]))

(defn body [db]
  (let [new-first-word (-> db :scrambles :new-first-word)
        new-second-word (-> db :scrambles :new-second-word)
        error (-> db :scrambles :errors)
        response (-> db :scrambles :response)]
    [:div.todomvc-wrapper
     [:section.todoapp
      [:header.header
       [:h1 "Scramble?"]

       (input-box "Enter first word" :scrambles/update-first-word new-first-word)
       (input-box "Enter second word" :scrambles/update-second-word new-second-word)

       (button)

       (display-message response)

       (display-error error)]]]))