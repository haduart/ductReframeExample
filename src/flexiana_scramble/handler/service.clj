(ns flexiana-scramble.handler.service
  (:require [clojure.spec.alpha :as s]
            [clojure.set :refer [subset?]]
            [clojure.string :refer :all]))

(defn valid-string? [e] (re-matches #"^[a-z]+$" e))
(s/def ::valid-string (s/and string? valid-string?))

(defn- has-not-enough-characters? [value-first value-second]
  (or (nil? value-first) (< value-first value-second)))

(defn scramble? [first-string second-string]
  {:pre  [(s/valid? ::valid-string first-string)
          (s/valid? ::valid-string second-string)]
   :post [boolean?]}
  (let [freq-seq-fn (comp frequencies seq)
        freq-second (freq-seq-fn second-string)
        freq-first (freq-seq-fn first-string)
        get-value-fn #(get freq-first (first %))]
    (->>
      freq-second
      (not-any? #(has-not-enough-characters? (get-value-fn %) (second %))))))
