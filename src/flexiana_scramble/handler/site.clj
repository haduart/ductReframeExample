(ns flexiana-scramble.handler.site
  (:require
    [compojure.core :refer [GET]]
    [integrant.core :as ig]
    [ring.util.response :as response]))


(defmethod ig/init-key :flexiana-scramble.handler/site
  [_ conf]
  (GET "/" [:as request]
    (response/content-type
      (response/resource-response "flexiana_scramble/pages/index.html")
      "text/html")))
