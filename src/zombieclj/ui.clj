(ns zombieclj.ui
  (:require [clojure.core.match :refer [match]]))

(defn create-animated-updates [events]
  (mapcat
   (fn [event]
     (match event
       [:started-rolling] [[:assoc-in [:rolls :rolling?] true]]
       [:rolled-die id {:from from :to to :seed seed}] [[:assoc-in [:dice id :previous-face] from]
                                                        [:assoc-in [:dice id :current-face] to]
                                                        [:assoc-in [:dice id :roll-id] seed]
                                                        [:wait 100]]
       [:used-roll n] [[:assoc-in [:rolls :used] n]
                       [:assoc-in [:rolls :rolling?] false]]
       :else []
       ))
   events)
  )
