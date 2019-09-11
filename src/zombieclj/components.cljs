(ns zombieclj.components
  (:require [dumdom.core :as dd]))

(def faces [:punch :shield :shovel :punch :heal :skull])

(def example
  {:zombies {:zombie-1 {:id :zombie-1
                        :max-health 5
                        :lost-health 3}}

   :dice {:die-1 {:id :die-1 :current-face :skull}
          :die-2 {:id :die-2 :current-face :skull}
          :die-3 {:id :die-3 :current-face :skull}
          :die-4 {:id :die-4 :current-face :skull}
          :die-5 {:id :die-5 :current-face :skull}}

   :rolls {:max 3 :used 2}})

(dd/defcomponent Game [{:keys [zombies dice rolls]}]
  [:div "Game goes here"])
