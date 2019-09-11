(ns ^:figwheel-hooks zombieclj.client
  (:require [chord.client :refer [ws-ch]]
            [cljs.core.async :refer [<! put!]]
            [dumdom.core :as dd]
            [zombieclj.animation :as animation]
            [zombieclj.components :as components]
            [zombieclj.event-bus :as bus])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defonce container (.getElementById js/document "main"))
(defonce game-atom (atom nil))
(defonce ws-atom (atom nil))

(defn ^:after-load render []
  (when-let [game @game-atom]
    (dd/render (components/Game game) container)))

(add-watch game-atom :render render)

(bus/watch ::me :toggle-lock (fn [id] (swap! game-atom update-in [:dice id :locked?] not)))
(bus/watch ::me :roll-dice (fn []
                             (put! @ws-atom [:roll-dice (->> @game-atom :dice vals (remove :locked?) (map :id))])))

(defonce run-once ;; not every figwheel reload
  (go
    (let [{:keys [ws-channel error]} (<! (ws-ch "ws://localhost:8666/ws"))]
      (when error (throw error))
      (reset! game-atom (:message (<! ws-channel)))
      (reset! ws-atom ws-channel)

      (loop []
        (if-let [updates (:message (<! ws-channel))]
          (do (animation/start! game-atom updates)
              (recur))
          (prn "Websocket went to bed"))))))

(bus/watch-all ::me prn)
