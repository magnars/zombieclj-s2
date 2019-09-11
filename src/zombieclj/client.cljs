(ns ^:figwheel-hooks zombieclj.client
  (:require [chord.client :refer [ws-ch]]
            [cljs.core.async :refer [<!]]
            [dumdom.core :as dd]
            [zombieclj.components :as components]
            [zombieclj.event-bus :as bus])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defonce container (.getElementById js/document "main"))

(defn ^:after-load render []
  (dd/render (components/Game components/example) container))

(defonce run-once ;; not every figwheel reload
  (go
    (let [{:keys [ws-channel error]} (<! (ws-ch "ws://localhost:8666/ws"))]
      (when error (throw error))
      (render))))

(bus/watch-all ::me prn)
