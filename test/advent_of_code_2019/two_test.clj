(ns advent-of-code-2019.two-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2019.two :refer :all]))

(deftest run-intcode-test
  (testing "Part 1 examples"
    (is (= (run-intcode [1 9 10 3
                         2 3 11 0
                         99 30 40 50])
           [3500 9 10 70
            2 3 11 0
            99 30 40 50]))
    (is (= (run-intcode [1 0 0 0 99])
           [2 0 0 0 99]))
    (is (= (run-intcode [2 3 0 3 99])
           [2 3 0 6 99]))
    (is (= (run-intcode [2 4 4 5 99 0])
           [2 4 4 5 99 9801]))
    (is (= (run-intcode [1 1 1 4
                         99 5 6 0
                         99])
           [30 1 1 4
            2 5 6 0
            99])))
  (testing "Part 1 bad state"
    (is (= (get bad-state 0) 2782414))))

(deftest part-two-test
  (testing "Part 2"
    (is (= find-part-two 9820))))