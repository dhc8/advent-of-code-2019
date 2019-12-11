(ns advent-of-code-2019.five-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2019.five :refer :all]))

(deftest run-intcode-test
  (testing "Day 2 examples"
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
  (testing "Day 5 part 1 examples"
    (is (= (run-intcode [1002 4 3 4 33])
           [1002 4 3 4 99]))
    (is (= (run-intcode [3 0 4 0 99] [1])
           [1]))
    (is (= (run-intcode [3 0 4 0 99] [5])
           [5])))
  (testing "Day 5 part 1 solution"
    (is (and (map #(= 0 %) (drop-last (run-intcode puzzle-input [1])))))
    (is (= (last (run-intcode puzzle-input [1])))
        15314507))
  (testing "Day 5 part 2 examples"
    (is (= (run-intcode [3 9 8 9 10 9 4 9 99 -1 8] [8])
           [1]))
    (is (= (run-intcode [3 9 8 9 10 9 4 9 99 -1 8] [7])
           [0]))
    (is (= (run-intcode [3 9 7 9 10 9 4 9 99 -1 8] [8])
           [0]))
    (is (= (run-intcode [3 9 7 9 10 9 4 9 99 -1 8] [7])
           [1]))
    (is (= (run-intcode [3 3 1108 -1 8 3 4 3 99] [8])
           [1]))
    (is (= (run-intcode [3 3 1108 -1 8 3 4 3 99] [7])
           [0]))
    (is (= (run-intcode [3 3 1107 -1 8 3 4 3 99] [8])
           [0]))
    (is (= (run-intcode [3 3 1107 -1 8 3 4 3 99] [7])
           [1]))
    (is (= (run-intcode [3 12 6 12 15 1 13 14 13 4 13 99 -1 0 1 9] [0])
           [0]))
    (is (= (run-intcode [3 12 6 12 15 1 13 14 13 4 13 99 -1 0 1 9] [5])
           [1]))
    (is (= (run-intcode [3 3 1105 -1 9 1101 0 0 12 4 12 99 1] [0])
           [0]))
    (is (= (run-intcode [3 3 1105 -1 9 1101 0 0 12 4 12 99 1] [5])
           [1]))
    (is (= (run-intcode [3 21 1008 21 8 20 1005 20 22 107 8 21 20 1006 20 31
                         1106 0 36 98 0 0 1002 21 125 20 4 20 1105 1 46 104
                         999 1105 1 46 1101 1000 1 20 4 20 1105 1 46 98 99] [7])
           [999]))
    (is (= (run-intcode [3 21 1008 21 8 20 1005 20 22 107 8 21 20 1006 20 31
                         1106 0 36 98 0 0 1002 21 125 20 4 20 1105 1 46 104
                         999 1105 1 46 1101 1000 1 20 4 20 1105 1 46 98 99] [8])
           [1000]))
    (is (= (run-intcode [3 21 1008 21 8 20 1005 20 22 107 8 21 20 1006 20 31
                         1106 0 36 98 0 0 1002 21 125 20 4 20 1105 1 46 104
                         999 1105 1 46 1101 1000 1 20 4 20 1105 1 46 98 99] [9])
           [1001])))
  (testing "Day 5 part 2 solution"
    (is (= (run-intcode puzzle-input [5])
           [652726]))))