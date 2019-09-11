(ns zombieclj.components
  (:require [dumdom.core :as dd]
            [zombieclj.event-bus :as bus]))

(dd/defcomponent Zombie [{:keys [id max-health lost-health]}]
  [:div.zombie-position
   [:div.zombie {:className (name id)}
    [:div.zombie-health
     (for [n (range max-health)]
       [:div.heart {:className (when (< n lost-health)
                                 "lost")}])]]])

(dd/defcomponent Die [{:keys [id faces previous-face current-face locked? roll-id]}]
  [:div.die-w-lock
   [:div.die {:className (when previous-face "rolling")
              :key (str id roll-id)}
    [:div.cube {:className (if previous-face
                             (str "roll-" (.indexOf faces previous-face) "-to-" (.indexOf faces current-face))
                             (str "facing-" (.indexOf faces current-face)))}
     (for [i (range (count faces))]
       [:div.face {:className (str "face-" i " " (name (nth faces i)))}])]]
   [:div.clamp {:className (when locked? "locked")}
    [:div.lock {:onClick (bus/action-fn [[:toggle-lock id]])}
     [:div.padlock]]]])

(dd/defcomponent Rolls [{:keys [max used rolling?]}]
  [:div.rolls {:onClick (bus/action-fn [[:roll-dice]])}
   (for [i (range max)]
     [:div.roll {:className (cond
                              (and rolling? (= i used)) "rolling"
                              (< i used) "used")}])])

(dd/defcomponent Game [{:keys [zombies dice rolls]}]
  [:div.page
   [:div.surface
    [:div.skyline
     (for [n (range 16)]
       [:div.building {:className (str "building-" n)}])]
    [:div.zombies
     (for [zombie (vals zombies)]
       [Zombie zombie])]
    [:div.dice-row
     (interpose
      [:div.dice-spacing]
      (for [die (vals dice)]
        [Die die]))
     [Rolls rolls]]]])
