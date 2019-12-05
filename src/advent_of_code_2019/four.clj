(ns advent-of-code-2019.four
  (:require [clojure.set :as set]))

(defn split-digits
  [n]
  (loop [digits nil
         remainder n]
    (if (= remainder 0)
      digits
      (recur (conj digits (mod remainder 10))
             (quot remainder 10)))))

(defn test-number
  [n]
  (let [digits (split-digits n)]
    (loop [[first second & rest] digits
           repeats false]
      (let [comparison (compare second first)
            new-repeats (or repeats (= 0 comparison))]
        (if (< comparison 0)
          false
          (if (= nil rest)
            new-repeats
            (recur (conj rest second) new-repeats)))))))

(defn part-one-solution
  []
  (loop [n 165432
         matches 0]
    (if (> n 707912)
      matches
      (if (test-number n)
        (recur (inc n) (inc matches))
        (recur (inc n) matches)))))

(defn test-number-part-two
  [n]
  (let [digits (split-digits n)]
    (loop [[this & rest] digits
           repeats (set nil)
           triples (set nil)]
      (if (empty? rest)
        (not (empty? (set/difference repeats triples)))
        (let [second (first rest)
              comparison (compare second this)]
          (case comparison
            -1 false
            1 (recur rest
                     repeats
                     triples)
            0 (recur rest
                     (conj repeats this)
                     (if (contains? repeats this)
                       (conj triples this)
                       triples))))))))

(defn part-two-solution
  []
  (loop [n 165432
         matches 0]
    (if (> n 707912)
      matches
      (if (test-number-part-two n)
        (recur (inc n) (inc matches))
        (recur (inc n) matches)))))