(ns zombieclj.ui-test
  (:require [clojure.test :refer [deftest is testing]]
            [zombieclj.ui :as sut]))

(deftest animated-updates
  (is (= (sut/create-animated-updates [[:started-rolling]
                                       [:rolled-die :die-1 {:from :skull :to :punch :seed 0}]
                                       [:rolled-die :die-3 {:from :skull :to :heal :seed 0}]
                                       [:used-roll 2]
                                       [:changed-seed 1]])
         [[:assoc-in [:rolls :rolling?] true]

          [:assoc-in [:dice :die-1 :previous-face] :skull]
          [:assoc-in [:dice :die-1 :current-face] :punch]
          [:assoc-in [:dice :die-1 :roll-id] 0]
          [:wait 100]

          [:assoc-in [:dice :die-3 :previous-face] :skull]
          [:assoc-in [:dice :die-3 :current-face] :heal]
          [:assoc-in [:dice :die-3 :roll-id] 0]
          [:wait 100]

          [:assoc-in [:rolls :used] 2]
          [:assoc-in [:rolls :rolling?] false]])))
