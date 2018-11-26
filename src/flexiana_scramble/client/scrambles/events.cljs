(ns flexiana-scramble.client.scrambles.events
  (:require
    [re-frame.core :as re-frame]
    [flexiana-scramble.client.scrambles.middlewares :as middleware]))


(re-frame/reg-event-db :scrambles/update-first-word
                       (fn [db [_ word]]
                         (assoc-in db [:scrambles :new-first-word] word)))

(re-frame/reg-event-db :scrambles/update-second-word
                       (fn [db [_ word]]
                         (assoc-in db [:scrambles :new-second-word] word)))

(re-frame/register-handler :scrambles/scramble-response
                           (fn [db [_ response]]
                             (do
                               (assoc-in db [:scrambles :errors] nil)
                               (assoc-in db [:scrambles :response] (get (js->clj response) :isScrambled false)))))

(re-frame/register-handler :scrambles/scramble-error
                           (fn [db [_ response]]
                             (assoc-in db [:scrambles :errors] true)))

(re-frame/reg-event-db :scrambles/check-scramble
                       [middleware/check-scramble]
                       (fn [db _]
                         (assoc-in db [:scrambles :errors] nil)))


