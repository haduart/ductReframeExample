(ns flexiana-scramble.client.scrambles.middlewares
  (:require
    [ajax.core :refer [GET POST]]
    [re-frame.core :as re-frame]))


(def check-scramble
  (re-frame/after
    (fn [db _]
      (POST "/scramble/"
            :params {:str1 (-> db :scrambles :new-first-word)
                     :str2 (-> db :scrambles :new-second-word)}
            :format :json
            :response-format :json
            :keywords? true
            :handler #(re-frame/dispatch [:scrambles/scramble-response %])
            :error-handler #(re-frame/dispatch [:scrambles/scramble-error %])))))
