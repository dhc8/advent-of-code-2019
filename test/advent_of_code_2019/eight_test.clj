(ns advent-of-code-2019.eight-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2019.eight :refer :all]))

; TODO These are kind of awful. Add more?

(deftest part-one-test
  (testing "Part 1 solution"
    (is (= part-one-solution
           828))))

(deftest part-two-test
  (testing "Part 2 solution - ZLBJF"
    (is (= (print-image (pixel-values (divide-layers puzzle-input 6 25)) 6 25)
           [["*" "*" "*" "*" " " "*" " " " " " " " " "*" "*" "*" " " " " " " " " "*" "*" " " "*" "*" "*" "*" " "]
            [" " " " " " "*" " " "*" " " " " " " " " "*" " " " " "*" " " " " " " " " "*" " " "*" " " " " " " " "]
            [" " " " "*" " " " " "*" " " " " " " " " "*" "*" "*" " " " " " " " " " " "*" " " "*" "*" "*" " " " "]
            [" " "*" " " " " " " "*" " " " " " " " " "*" " " " " "*" " " " " " " " " "*" " " "*" " " " " " " " "]
            ["*" " " " " " " " " "*" " " " " " " " " "*" " " " " "*" " " "*" " " " " "*" " " "*" " " " " " " " "]
            ["*" "*" "*" "*" " " "*" "*" "*" "*" " " "*" "*" "*" " " " " " " "*" "*" " " " " "*" " " " " " " " "]]))))