(ns zombieclj.game-test
  (:require [clojure.test :refer [deftest is testing]]
            [zombieclj.game :as sut]))

(def example (assoc game/initial-game :seed 0))

(deftest roll-dice-test
  (is (= (sut/roll-dice example [:die-1 :die-3])
         [[:started-rolling]
          [:rolled-die :die-1 {:from :skull :to :punch :seed 0}]
          [:rolled-die :die-3 {:from :skull :to :heal :seed 0}]
          [:used-roll 1]
          [:changed-seed 1]])))

(deftest update-game-test
  (is (= (sut/update-game example [[:started-rolling]])
         example))

  (is (= (->> (sut/update-game example [[:rolled-die :die-1 {:from :skull :to :punch :seed 0}]
                                        [:rolled-die :die-3 {:from :skull :to :heal :seed 0}]])
              :dice vals (map (juxt :id :current-face)))
         [[:die-1 :punch]
          [:die-2 :skull]
          [:die-3 :heal]
          [:die-4 :skull]
          [:die-5 :skull]]))

  (is (= (-> (sut/update-game example [[:used-roll 1]])
             :rolls
             :used)
         1))

  (is (= (-> (sut/update-game example [[:changed-seed 7]])
             :seed)
         7)))
