(ns flexiana-scramble.client.scrambles.components
  (:require
    [goog.events.KeyCodes :as KeyCodes]
    [re-frame.core :as re-frame]))

(defn body [db]
  (let [new-first-word (-> db :scrambles :new-first-word)
        new-second-word (-> db :scrambles :new-second-word)
        error (-> db :scrambles :errors)
        response (-> db :scrambles :response)]
    [:div.todomvc-wrapper
     [:section.todoapp
      [:header.header
       [:h1 "Scramble?"]

       [:input {:placeholder "Enter first word"
                :type        "text"
                :auto-focus  true
                :value       new-first-word
                :on-change   #(re-frame/dispatch [:scrambles/update-first-word (-> % .-target .-value)])}]

       [:input {:placeholder "Enter second word"
                :type        "text"
                :auto-focus  true
                :value       new-second-word
                :on-change   #(re-frame/dispatch [:scrambles/update-second-word (-> % .-target .-value)])}]

       [:button {:type     "submit"
                 :disabled false
                 :on-click #(re-frame/dispatch [:scrambles/check-scramble])}
        "Check Scramble"]

       (when (not (nil? response))
         (if response
           [:h2.success "True!"]
           [:h2 "False..."]))
       ]]]))