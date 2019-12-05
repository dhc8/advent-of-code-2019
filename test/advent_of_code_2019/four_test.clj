(ns advent-of-code-2019.four-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2019.four :refer :all]))

(deftest split-digits-test
  (is (= (split-digits 123)
         [1 2 3]))
  (is (= (split-digits 1223)
         [1 2 2 3])))

(deftest part-one-test
  (testing "given examples"
    (is (= (test-number 111111)
           true))
    (is (= (test-number 223450)
           false))
    (is (= (test-number 123789)
           false)))
  (testing "solution"
    (is (= (part-one-solution)
           1716))))

(deftest part-two-test
  (testing "given examples"
    (is (= (test-number-part-two 112233)
           true))
    (is (= (test-number-part-two 123444)
           false))
    (is (= (test-number-part-two 111122)
           true)))
  (testing "solution"
    (is (= (part-two-solution)
           1163))))