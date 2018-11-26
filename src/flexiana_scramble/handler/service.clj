(ns flexiana-scramble.handler.service
  (:require [clojure.spec.alpha :as s]
            [clojure.set :refer [subset?]]))

(defn valid-string? [e] (re-matches #"^[a-z]+$" e))
(s/def ::valid-string (s/and string? valid-string?))

(defn scramble? [first-string second-string]
  {:pre [(s/valid? ::valid-string first-string)
         (s/valid? ::valid-string second-string)]
   :post [boolean?]}
  (subset? (set second-string)
           (set first-string)))

