(ns flexiana-scramble.handler.api
  (:require [compojure.core :refer :all]
            [clojure.java.io :as io]
            [integrant.core :as ig]
            [clojure.data.json :as json]
            [clojure.walk :refer [keywordize-keys]]
            [ring.middleware.json :refer [wrap-json-response]]
            [flexiana-scramble.handler.service :as service]))

(defn- read-json [request]
  (-> request
      :body-params
      keywordize-keys))

(defn- json-response [data & [status]]
  {:status (or status 200)
   :body   data})

(defn- wrap-scramble-response [is-scramble?]
  {:isScrambled is-scramble?})

(defn- scramble [{first-string :str1 second-string :str2}]
  (service/scramble? first-string second-string))

(defmethod ig/init-key :flexiana-scramble.handler/api [_ options]
  (wrap-json-response
    (context "/scramble" []
      (POST "/" request
        (->>
          request
          read-json
          scramble
          wrap-scramble-response
          json-response)))))