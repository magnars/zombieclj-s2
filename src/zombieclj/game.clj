(ns zombieclj.game
  (:require [clojure.core.match :refer [match]]))

(def faces [:punch :shield :shovel :punch :heal :skull])

(def initial-game
  {:zombies {:zombie-1 {:id :zombie-1
                        :max-health 5
                        :lost-health 0}}

   :dice {:die-1 {:id :die-1 :faces faces :current-face :skull}
          :die-2 {:id :die-2 :faces faces :current-face :skull}
          :die-3 {:id :die-3 :faces faces :current-face :skull}
          :die-4 {:id :die-4 :faces faces :current-face :skull}
          :die-5 {:id :die-5 :faces faces :current-face :skull}}

   :rolls {:max 3 :used 0}})

(defn roll-dice [game die-ids]
  (let [rng (java.util.Random. (:seed game))]
    (concat
     [[:started-rolling]]
     (for [id die-ids]
       (let [{:keys [current-face faces]} (get-in game [:dice id])]
         [:rolled-die id {:from current-face
                          :to (nth faces (.nextInt rng (count faces)))
                          :seed (:seed game)}]))
     [[:used-roll (-> game :rolls :used inc)]
      [:changed-seed (-> game :seed inc)]])))

(defn update-game [game events]
  (reduce
   (fn [game event]
     (match event
       [:rolled-die id {:to face}] (assoc-in game [:dice id :current-face] face)
       [:used-roll used] (assoc-in game [:rolls :used] used)
       [:changed-seed seed] (assoc game :seed seed)
       :else game))
   game events))
