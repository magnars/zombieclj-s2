(ns zombieclj.game-loop
  (:require [clojure.core.async :refer [>! <! go]]
            [clojure.core.match :refer [match]]
            [zombieclj.game :as game]
            [zombieclj.ui :as ui]))

(defn start! [ws-channel]
  (go
    (>! ws-channel game/initial-game)
    (loop [game (assoc game/initial-game :seed (System/currentTimeMillis))]
      (if-let [message (:message (<! ws-channel))]
        (match message
          [:roll-dice die-ids] (let [events (game/roll-dice game die-ids)]
                                 (>! ws-channel (ui/create-animated-updates events))
                                 (recur (game/update-game game events))))
        (prn "Websocket went to bed")))))
