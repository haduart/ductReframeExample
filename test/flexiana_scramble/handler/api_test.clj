(ns flexiana-scramble.handler.api-test
  (:use midje.sweet
        clojure.test)
  (:require [flexiana-scramble.handler.service :as service]
            [integrant.core :as ig]
            [ring.mock.request :as mock]
            [clojure.data.json :as json]))

(fact "How the API for invoking the scramble should look like"
      (let [handler (ig/init-key :flexiana-scramble.handler/api {})
            strings-to-scramble {:str1 "rekqodlw"
                                 :str2 "world"}
            request (mock/request :post "/scramble/")
            response (delay (handler (assoc request :body-params strings-to-scramble)))
            expected-response (json/write-str {"isScrambled" true})]

        "The response should be 200"
        (:status @response) => 200
        (provided
          (service/scramble? "rekqodlw" "world") => true)
        "The expected JSON object response"
        (:body @response) => expected-response))


