(ns advent-of-code-2019.six-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2019.six :refer :all]))

(deftest add-child-edge-test
  (is (= (add-child-edge {} :parent :child)
         {:parent [:child]}))
  (is (= (add-child-edge {:parent [:child-one]} :parent :child-two)
         {:parent [:child-one :child-two]}))
  (is (= (add-child-edge {:parent-one [:child-one]} :parent-two :child-two)
         {:parent-one [:child-one]
          :parent-two [:child-two]}))
  (is (= (add-child-edge {:parent [:child]} :child :grandchild)
         {:parent [:child]
          :child [:grandchild]})))

(deftest add-parent-edge-test
  (is (= (add-parent-edge {} :parent :child)
         {:child :parent}))
  (is (= (add-parent-edge {:child-one :parent} :parent :child-two)
         {:child-one :parent
          :child-two :parent}))
  (is (= (add-parent-edge {:child :parent} :grandparent :parent)
         {:child :parent
          :parent :grandparent})))

(deftest build-orbit-tree-test
  (testing "Part 1"
    (is (= (build-orbit-tree "COM)B\nB)C\nC)D\nD)E\nE)F\nB)G\nG)H\nD)I\nE)J\nJ)K\nK)L"
                             :child)
           {"K" ["L"]
            "G" ["H"]
            "J" ["K"]
            "COM" ["B"]
            "E" ["J" "F"]
            "C" ["D"]
            "B" ["G" "C"]
            "D" ["I" "E"]})))
  (testing "Part 2"
    (is (= (build-orbit-tree "COM)B\nB)C\nC)D\nD)E\nE)F\nB)G\nG)H\nD)I\nE)J\nJ)K\nK)L\nK)YOU\nI)SAN"
                             :parent)
           {"K" "J"
            "L" "K"
            "G" "B"
            "J" "E"
            "H" "G"
            "E" "D"
            "C" "B"
            "F" "E"
            "B" "COM"
            "SAN" "I"
            "I" "D"
            "D" "C"
            "YOU" "K"}))))

(deftest calculate-checksum-test
  (is (= (calculate-checksum {"K" ["L"]
                              "G" ["H"]
                              "J" ["K"]
                              "COM" ["B"]
                              "E" ["J" "F"]
                              "C" ["D"]
                              "B" ["G" "C"]
                              "D" ["I" "E"]})
         42)))

(deftest get-ancestors-test
  (is (= (get-ancestors "YOU" {"K" "J"
                               "L" "K"
                               "G" "B"
                               "J" "E"
                               "H" "G"
                               "E" "D"
                               "C" "B"
                               "F" "E"
                               "B" "COM"
                               "SAN" "I"
                               "I" "D"
                               "D" "C"
                               "YOU" "K"})
         ["K" "J" "E" "D" "C" "B" "COM"]))
  (is (= (get-ancestors "SAN" {"K" "J"
                               "L" "K"
                               "G" "B"
                               "J" "E"
                               "H" "G"
                               "E" "D"
                               "C" "B"
                               "F" "E"
                               "B" "COM"
                               "SAN" "I"
                               "I" "D"
                               "D" "C"
                               "YOU" "K"})
         ["I" "D" "C" "B" "COM"])))

(deftest steps-between-test
  (is (= (steps-between "YOU" "SAN" {"K" "J"
                                     "L" "K"
                                     "G" "B"
                                     "J" "E"
                                     "H" "G"
                                     "E" "D"
                                     "C" "B"
                                     "F" "E"
                                     "B" "COM"
                                     "SAN" "I"
                                     "I" "D"
                                     "D" "C"
                                     "YOU" "K"})
         4)))

(deftest solutions-test
  (testing "Part 1"
    (is (= solve-part-one 241064)))
  (testing "Part 2"
    (is (= solve-part-two 418))))