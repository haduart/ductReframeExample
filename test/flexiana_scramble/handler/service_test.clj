(ns flexiana-scramble.handler.service-test
  (:use midje.sweet
        clojure.test)
  (:require [flexiana-scramble.handler.service :as service]))

(fact "When scrambling"
      (fact "This words are scrambled corrected"
            (service/scramble? "rekqodlw" "world") => true
            (service/scramble? "cedewaraaossoqqyt" "codewars") => true)
      (fact "This words are not contained"
            (service/scramble? "katas" "steak") => false)
      (fact "Prevent numbers to get through"
            (service/scramble? "hola5" "aloah") => (throws AssertionError)
            (service/scramble? "hola" "aloah5") => (throws AssertionError))
      (fact "Prevent capital letters to get through"
            (service/scramble? "Hola" "aloah") => (throws AssertionError)
            (service/scramble? "hola" "aloaH") => (throws AssertionError)))


