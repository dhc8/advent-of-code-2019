(ns advent-of-code-2019.nine-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2019.nine :refer :all]
            [advent-of-code-2019.five :as five]))

(deftest run-intcode-tests
  (testing "Part 1 examples"
    (is (= (five/run-intcode [109 1 204 -1 1001 100 1 100 1008 100 16 101 1006 101 0 99] [])
           [109 1 204 -1 1001 100 1 100 1008 100 16 101 1006 101 0 99]))
    (is (= (count (str (first (five/run-intcode [1102 34915192 34915192 7 4 7 99 0] []))))
           16))
    (is (= (five/run-intcode [104 1125899906842624 99] [])
           [1125899906842624])))
  (testing "Part 1 solution"
    (is (= (five/run-intcode puzzle-input [1])
           [3497884671])))
  (testing "Part 2 solution"
    (is (= (five/run-intcode puzzle-input [2])
           [46470]))))