(ns advent-of-code-2019.three-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2019.three :refer :all]))

(deftest add-points-test
  (is (= (add-points empty-points [{:x 1 :y 1 :steps 1}])
         {[0 0] 0 [1 1] 1 :last {:x 1 :y 1 :steps 1}})))

(deftest add-wire-test
  (is (= (add-wire empty-points "R1")
         {[0 0] 0 [1 0] 1 :last {:x 1 :y 0 :steps 1}}))
  (is (= (add-wire empty-points "U2")
         {[0 0] 0 [0 1] 1 [0 2] 2 :last {:x 0 :y 2 :steps 2}}))
  (is (= (add-wire empty-points "D1")
         {[0 0] 0 [0 -1] 1 :last {:x 0 :y -1 :steps 1}}))
  (is (= (add-wire empty-points "L1")
         {[0 0] 0 [-1 0] 1 :last {:x -1 :y 0 :steps 1}})))

(deftest get-points-test
  (is (= (get-points "R2,U3,L3,D1")
         {[1 0] 1 [2 0] 2
          [2 1] 3 [2 2] 4 [2 3] 5
          [1 3] 6 [0 3] 7 [-1 3] 8
          [-1 2] 9})))

(deftest overlapping-points-test
  (is (= (overlapping-points ["R8,U5,L5,D3"
                              "U7,R6,D4,L4"])
         [{:x 3 :y 3 :wire-one-steps 20 :wire-two-steps 20}
          {:x 6 :y 5 :wire-one-steps 15 :wire-two-steps 15}])))

(deftest distance-test
  (is (= (distance {:x 1 :y 2}) 3))
  (is (= (distance {:x 2 :y 1}) 3))
  (is (= (distance {:x 0 :y 0}) 0))
  (is (= (distance {:x -1 :y 1}) 2))
  (is (= (distance {:x 1 :y -1}) 2))
  (is (= (distance {:x -1 :y -1}) 2)))

(deftest closest-intersection-test
  (testing "Part 1 inputs"
    (is (= (closest-intersection-distance ["R8,U5,L5,D3"
                                           "U7,R6,D4,L4"])
           6))
    (is (= (closest-intersection-distance ["R75,D30,R83,U83,L12,D49,R71,U7,L72"
                                           "U62,R66,U55,R34,D71,R55,D58,R83"])
           159))
    (is (= (closest-intersection-distance ["R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"
                                           "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"])
           135))))

(deftest combined-steps-test
  (is (= (combined-steps {:wire-one-steps 5 :wire-two-steps 4})
         9)))

(deftest earliest-intersection-test
  (testing "Part 2 inputs"
    (is (= (earliest-intersection ["R8,U5,L5,D3"
                                   "U7,R6,D4,L4"])
           30))
    (is (= (earliest-intersection ["R75,D30,R83,U83,L12,D49,R71,U7,L72"
                                   "U62,R66,U55,R34,D71,R55,D58,R83"])
           610))
    (is (= (earliest-intersection ["R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"
                                   "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"])
           410))))

(deftest part-one-test
  (is (= (part-one-solution) 403)))

(deftest part-two-test
  (is (= (part-two-solution) 4158)))