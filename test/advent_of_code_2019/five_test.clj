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
  (testing "Day 5 part 1 example"
    (is (= (run-intcode [1002 4 3 4 33])
           [1002 4 3 4 99]))))
;TODO figure out tests for input/output using with-in-str and with-out-str