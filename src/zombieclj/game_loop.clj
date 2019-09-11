(ns zombieclj.game-loop
  (:require [clojure.core.async :refer [go]]
            [zombieclj.game :as game]))

(defn start! [ws-channel]
  (go
    ;; start game-loop
    ))
