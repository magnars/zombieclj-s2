(ns zombieclj.animation
  (:require [cljs.core.async :refer [<! timeout]]
            [cljs.core.match :refer [match]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn start! [game-atom steps]
  (go
   (loop [game @game-atom
          remaining-steps steps]
     (if (empty? remaining-steps)
       (reset! game-atom game)

       (match (first remaining-steps)
         [:assoc-in path v] (recur (assoc-in game path v)
                                   (next remaining-steps))
         [:wait ms] (do (reset! game-atom game)
                        (<! (timeout ms))
                        (recur game (next remaining-steps))))))))
