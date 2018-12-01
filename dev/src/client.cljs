(ns dev.client
  (:require
    [figwheel.client :as fw]
    [re-frisk.core :refer [enable-re-frisk!]]
    [flexiana-scramble.client.main]
    [devtools.core :as devtools]))

(devtools/install!)
(enable-console-print!)

(fw/start {:on-jsload flexiana-scramble.client.main/init
           :websocket-url "ws://localhost:3449/figwheel-ws"})
