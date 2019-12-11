(ns advent-of-code-2019.seven
  (:require [clojure.math.combinatorics :as combo]
            [advent-of-code-2019.five :as five]))

(def permutations
  (combo/permutations (range 5)))

(defn amplify
  [intcode phases]
  (reduce #(first (five/run-intcode intcode (vector %2 %1))) (cons 0 phases)))

(defn max-amplification
  [intcode]
  (amplify intcode (last (sort-by #(amplify intcode %) permutations))))

(def puzzle-input
  [3 8 1001 8 10 8 105 1 0 0 21 38 55 68 93 118 199 280 361 442 99999 3 9 1002 9 2 9 101 5 9 9 102 4 9 9 4 9 99 3 9 101 3 9 9 1002 9 3 9 1001 9 4 9 4 9 99 3 9 101 4 9 9 102 3 9 9 4 9 99 3 9 102 2 9 9 101 4 9 9 102 2 9 9 1001 9 4 9 102 4 9 9 4 9 99 3 9 1002 9 2 9 1001 9 2 9 1002 9 5 9 1001 9 2 9 1002 9 4 9 4 9 99 3 9 101 1 9 9 4 9 3 9 102 2 9 9 4 9 3 9 101 1 9 9 4 9 3 9 101 1 9 9 4 9 3 9 101 1 9 9 4 9 3 9 101 2 9 9 4 9 3 9 1001 9 1 9 4 9 3 9 102 2 9 9 4 9 3 9 1002 9 2 9 4 9 3 9 1002 9 2 9 4 9 99 3 9 1001 9 1 9 4 9 3 9 1001 9 2 9 4 9 3 9 1002 9 2 9 4 9 3 9 101 2 9 9 4 9 3 9 1001 9 2 9 4 9 3 9 1001 9 1 9 4 9 3 9 102 2 9 9 4 9 3 9 1001 9 1 9 4 9 3 9 1002 9 2 9 4 9 3 9 102 2 9 9 4 9 99 3 9 1002 9 2 9 4 9 3 9 1001 9 1 9 4 9 3 9 102 2 9 9 4 9 3 9 102 2 9 9 4 9 3 9 101 1 9 9 4 9 3 9 1001 9 1 9 4 9 3 9 101 2 9 9 4 9 3 9 102 2 9 9 4 9 3 9 101 2 9 9 4 9 3 9 1001 9 2 9 4 9 99 3 9 1001 9 2 9 4 9 3 9 1001 9 2 9 4 9 3 9 102 2 9 9 4 9 3 9 101 1 9 9 4 9 3 9 1002 9 2 9 4 9 3 9 1002 9 2 9 4 9 3 9 1001 9 2 9 4 9 3 9 1001 9 2 9 4 9 3 9 101 1 9 9 4 9 3 9 1001 9 1 9 4 9 99 3 9 102 2 9 9 4 9 3 9 1001 9 1 9 4 9 3 9 1001 9 1 9 4 9 3 9 1002 9 2 9 4 9 3 9 1002 9 2 9 4 9 3 9 1001 9 2 9 4 9 3 9 1002 9 2 9 4 9 3 9 102 2 9 9 4 9 3 9 102 2 9 9 4 9 3 9 101 2 9 9 4 9 99])