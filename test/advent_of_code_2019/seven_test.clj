(ns advent-of-code-2019.seven-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2019.seven :refer :all]))

(deftest amplify-test
  (is (= (amplify [3 15 3 16 1002 16 10 16 1 16 15 15 4 15 99 0 0] [4 3 2 1 0])
         43210))
  (is (= (amplify [3 23 3 24 1002 24 10 24 1002 23 -1 23 
                   101 5 23 23 1 24 23 23 4 23 99 0 0] [0 1 2 3 4])
         54321))
  (is (= (amplify [3 31 3 32 1002 32 10 32 1001 31 -2 31 1007 31 0 33 
                   1002 33 7 33 1 33 31 31 1 32 31 31 4 31 99 0 0 0] [1 0 4 3 2])
         65210)))

(deftest max-amplification-test
  (testing "Part 1 examples"
    (is (= (max-amplification [3 15 3 16 1002 16 10 16 1 16 15 15 4 15 99 0 0])
           43210))
    (is (= (max-amplification [3 23 3 24 1002 24 10 24 1002 23 -1 23 
                               101 5 23 23 1 24 23 23 4 23 99 0 0])
           54321))
    (is (= (max-amplification [3 31 3 32 1002 32 10 32 1001 31 -2 31 1007 31 0 33 
                               1002 33 7 33 1 33 31 31 1 32 31 31 4 31 99 0 0 0])
           65210)))
  (testing "Part 1 solution"
    (is (= (max-amplification puzzle-input)
           277328))))