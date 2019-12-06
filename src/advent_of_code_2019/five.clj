(ns advent-of-code-2019.five)

(defn three
  []
  (Integer/parseInt (read-line)))

(defn five
  [x]
  (not= x 0))

(defn six
  [x]
  (= x 0))

(def instructions
  {1 {:params 3 :fn +}
   2 {:params 3 :fn *}
   3 {:params 1 :fn three}
   4 {:params 1 :fn println :has-output false}
   5 {:params 2 :fn five :update-pointer true}
   6 {:params 2 :fn six :update-pointer true}
   7 {:params 3 :fn <}
   8 {:params 3 :fn =}})

(defn get-param-values
  [code params param-mode]
  (loop [values []
         [this-param & rest-params] params
         modes  param-mode]
    (if (nil? this-param)
      values
      (let [current-mode    (mod modes 10)
            remaining-modes (quot modes 10)
            this-value      (if (= current-mode 1)
                              this-param
                              (get code this-param))]
        (recur (conj values this-value)
               rest-params
               remaining-modes)))))

(defn intify
  [result]
  (if (boolean? result)
    (if result 1 0)
    result))

(defn run-intcode
  "Expects a vector of ints"
  [intcode]
  (loop [code                intcode
         instruction-pointer 0]
    ;(println code instruction-pointer)
    (let [instruction (get code instruction-pointer)]
      (if (= instruction 99)
        code
        (let [opcode         (mod instruction 100)
              fn             (get-in instructions [opcode :fn])
              update-pointer (get-in instructions [opcode :update-pointer] false)
              num-params     (get-in instructions [opcode :params])
              has-output     (get-in instructions [opcode :has-output] (not update-pointer))
              params         (subvec code
                                     (+ instruction-pointer 1)
                                     (+ instruction-pointer (+ num-params 1)))
              param-mode     (quot instruction 100)
              param-values   (get-param-values code
                                               params
                                               param-mode)
              input-values   (if (or has-output update-pointer)
                               (drop-last param-values)
                               param-values)
              output-pointer (if (or has-output)
                               (last params))
              output-value   (do
                               ;(println fn input-values)
                               (intify (apply fn input-values)))
              new-code       (if has-output
                               (assoc code output-pointer output-value)
                               code)
              new-pointer    (if (and update-pointer (= output-value 1))
                               (last param-values)
                               (+ instruction-pointer (+ num-params 1)))]

          (recur new-code new-pointer))))))

(def puzzle-input
  [3 225 1 225 6 6 1100 1 238 225 104 0 1002 114 19 224 1001 224 -646 224 4 224 102 8 223 223 1001 224 7 224 1 223 224 223 1101 40 62 225 1101 60 38 225 1101 30 29 225 2 195 148 224 1001 224 -40 224 4 224 1002 223 8 223 101 2 224 224 1 224 223 223 1001 143 40 224 101 -125 224 224 4 224 1002 223 8 223 1001 224 3 224 1 224 223 223 101 29 139 224 1001 224 -99 224 4 224 1002 223 8 223 1001 224 2 224 1 224 223 223 1101 14 34 225 102 57 39 224 101 -3420 224 224 4 224 102 8 223 223 1001 224 7 224 1 223 224 223 1101 70 40 225 1102 85 69 225 1102 94 5 225 1 36 43 224 101 -92 224 224 4 224 1002 223 8 223 101 1 224 224 1 224 223 223 1102 94 24 224 1001 224 -2256 224 4 224 102 8 223 223 1001 224 1 224 1 223 224 223 1102 8 13 225 1101 36 65 224 1001 224 -101 224 4 224 102 8 223 223 101 3 224 224 1 223 224 223 4 223 99 0 0 0 677 0 0 0 0 0 0 0 0 0 0 0 1105 0 99999 1105 227 247 1105 1 99999 1005 227 99999 1005 0 256 1105 1 99999 1106 227 99999 1106 0 265 1105 1 99999 1006 0 99999 1006 227 274 1105 1 99999 1105 1 280 1105 1 99999 1 225 225 225 1101 294 0 0 105 1 0 1105 1 99999 1106 0 300 1105 1 99999 1 225 225 225 1101 314 0 0 106 0 0 1105 1 99999 8 677 226 224 1002 223 2 223 1006 224 329 1001 223 1 223 1108 226 226 224 1002 223 2 223 1005 224 344 101 1 223 223 1108 226 677 224 1002 223 2 223 1006 224 359 101 1 223 223 107 226 226 224 1002 223 2 223 1005 224 374 101 1 223 223 1107 226 226 224 1002 223 2 223 1005 224 389 101 1 223 223 107 677 677 224 102 2 223 223 1006 224 404 101 1 223 223 1008 226 226 224 1002 223 2 223 1006 224 419 101 1 223 223 108 677 226 224 1002 223 2 223 1006 224 434 101 1 223 223 1108 677 226 224 102 2 223 223 1005 224 449 101 1 223 223 1008 677 226 224 102 2 223 223 1006 224 464 1001 223 1 223 108 677 677 224 102 2 223 223 1005 224 479 101 1 223 223 7 677 677 224 102 2 223 223 1005 224 494 1001 223 1 223 8 226 677 224 102 2 223 223 1006 224 509 101 1 223 223 107 677 226 224 1002 223 2 223 1005 224 524 1001 223 1 223 7 677 226 224 1002 223 2 223 1005 224 539 1001 223 1 223 1007 226 677 224 1002 223 2 223 1005 224 554 1001 223 1 223 8 677 677 224 102 2 223 223 1006 224 569 101 1 223 223 7 226 677 224 102 2 223 223 1006 224 584 1001 223 1 223 1008 677 677 224 102 2 223 223 1005 224 599 101 1 223 223 1007 677 677 224 1002 223 2 223 1006 224 614 101 1 223 223 1107 677 226 224 1002 223 2 223 1006 224 629 101 1 223 223 1107 226 677 224 1002 223 2 223 1006 224 644 101 1 223 223 1007 226 226 224 102 2 223 223 1005 224 659 1001 223 1 223 108 226 226 224 102 2 223 223 1006 224 674 101 1 223 223 4 223 99 226])
