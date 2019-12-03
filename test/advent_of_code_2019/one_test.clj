(ns advent-of-code-2019.one-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2019.one :refer :all]))

(deftest fuel-required-test
  (testing "Part 1 examples"
    (is (= (fuel-required 12) 2))
    (is (= (fuel-required 14) 2))
    (is (= (fuel-required 1969) 654))
    (is (= (fuel-required 100756) 33583))))

(deftest total-fuel-required-test
  (testing "Combo of part 1 examples"
    (is (= (total-fuel-required [14 1969]) 656)))
  (testing "Puzzle inputs"
    (is (= (total-fuel-required puzzle-inputs) 3263354))))

(deftest recursive-fuel-required-test
  (testing "Part 2 examples"
    (is (= (recursive-fuel-required 14) 2))
    (is (= (recursive-fuel-required 1969) 966))
    (is (= (recursive-fuel-required 100756) 50346))))

(deftest total-recursive-fuel-required-test
  (testing "Combo of part 1 examples"
    (is (= (total-recursive-fuel-required [14 1969]) 968)))
  (testing "Puzzle inputs"
    (is (= (total-recursive-fuel-required puzzle-inputs) 4892166))))

(deftest puzzle-answers-test
  (testing "Part 1"
    (is (= total-fuel-required-part-one 3263354)))
  (testing "Part 2"
    (is (= total-fuel-required-part-two) 4892166)))
