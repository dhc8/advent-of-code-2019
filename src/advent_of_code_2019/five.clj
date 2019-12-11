(ns advent-of-code-2019.five)

(def instructions
  {1 {:params 3 :fn +}
   2 {:params 3 :fn *}
   3 {:params 1 :fn (fn [n] n) :type :input}
   4 {:params 1 :fn (fn [n] n) :type :output}
   5 {:params 2 :fn #(not= % 0) :type :update-pointer}
   6 {:params 2 :fn #(= % 0) :type :update-pointer}
   7 {:params 3 :fn <}
   8 {:params 3 :fn =}
   9 {:params 1 :fn + :type :relative-base}})

(defn intify
  [result]
  (if (boolean? result)
    (if result 1 0)
    result))

(defn get-param-values
  [code params param-modes relative-base]
  (loop [values []
         [this-param & rest-params] params
         [this-mode & rest-modes] param-modes]
    (if (nil? this-param)
      values
      (let [this-value (case this-mode
                         0 (get code this-param 0)
                         1 this-param
                         2 (get code (+ this-param relative-base) 0))]
        (recur (conj values this-value)
               rest-params
               rest-modes)))))

(defn get-input-values
  [param-values fn-type remaining-inputs relative-base]
  (case fn-type
    :input (vector (first remaining-inputs))
    :output param-values
    :relative-base (conj param-values relative-base)
    (drop-last param-values)))

(defn get-output-pointer
  [param-pointers, fn-type, num-params, param-modes, relative-base]
  (if (contains? #{:input :standard} fn-type)
    (if (= (last param-modes) 2)
      (+ (last param-pointers) relative-base)
      (last param-pointers))
    nil))

(defn run-intcode
  "Expects a vector of ints and a vector of inputs (also ints)"
  ([intcode] (run-intcode intcode [] :code))
  ([intcode inputs] (run-intcode intcode inputs :output))
  ([intcode inputs return-type]
   (loop [code                (zipmap (range (count intcode)) intcode)
          instruction-pointer 0
          relative-base       0
          remaining-inputs    inputs
          outputs             []]
     #_(println (vals (into (sorted-map) code)))
     (let [instruction (get code instruction-pointer)]
       (if (= instruction 99)
         (case return-type
           :code (vals (into (sorted-map) code))
           :output outputs)
         (let [opcode            (mod instruction 100)
               fn                (get-in instructions [opcode :fn])
               num-params        (get-in instructions [opcode :params])
               type              (get-in instructions [opcode :type] :standard)
               param-pointers    (vals (select-keys code (range
                                                           (inc instruction-pointer)
                                                           (+ (inc instruction-pointer) num-params))))
               param-modes       (map #(Character/digit % 10) (reverse (format (str "%0" num-params "d") (quot instruction 100))))
               param-values      (get-param-values code
                                                   param-pointers
                                                   param-modes
                                                   relative-base)
               input-values      (get-input-values param-values
                                                   type
                                                   remaining-inputs
                                                   relative-base)
               output-pointer    (get-output-pointer param-pointers
                                                     type
                                                     num-params
                                                     param-modes
                                                     relative-base)
               output-value      (intify (apply fn input-values))
               new-code          (if (contains? #{:standard :input} type)
                                   (assoc code output-pointer output-value)
                                   code)
               new-pointer       (if (and (= type :update-pointer) (= output-value 1))
                                   (last param-values)
                                   (+ (inc instruction-pointer) num-params))
               new-relative-base (if (= type :relative-base)
                                   output-value
                                   relative-base)
               new-inputs        (if (= type :input)
                                   (drop 1 remaining-inputs)
                                   remaining-inputs)
               new-outputs       (if (= type :output)
                                   (conj outputs output-value)
                                   outputs)]
           (recur new-code new-pointer new-relative-base new-inputs new-outputs)))))))

(def puzzle-input
  [3 225 1 225 6 6 1100 1 238 225 104 0 1002 114 19 224 1001 224 -646 224 4 224 102 8 223 223 1001 224 7 224 1 223 224 223 1101 40 62 225 1101 60 38 225 1101 30 29 225 2 195 148 224 1001 224 -40 224 4 224 1002 223 8 223 101 2 224 224 1 224 223 223 1001 143 40 224 101 -125 224 224 4 224 1002 223 8 223 1001 224 3 224 1 224 223 223 101 29 139 224 1001 224 -99 224 4 224 1002 223 8 223 1001 224 2 224 1 224 223 223 1101 14 34 225 102 57 39 224 101 -3420 224 224 4 224 102 8 223 223 1001 224 7 224 1 223 224 223 1101 70 40 225 1102 85 69 225 1102 94 5 225 1 36 43 224 101 -92 224 224 4 224 1002 223 8 223 101 1 224 224 1 224 223 223 1102 94 24 224 1001 224 -2256 224 4 224 102 8 223 223 1001 224 1 224 1 223 224 223 1102 8 13 225 1101 36 65 224 1001 224 -101 224 4 224 102 8 223 223 101 3 224 224 1 223 224 223 4 223 99 0 0 0 677 0 0 0 0 0 0 0 0 0 0 0 1105 0 99999 1105 227 247 1105 1 99999 1005 227 99999 1005 0 256 1105 1 99999 1106 227 99999 1106 0 265 1105 1 99999 1006 0 99999 1006 227 274 1105 1 99999 1105 1 280 1105 1 99999 1 225 225 225 1101 294 0 0 105 1 0 1105 1 99999 1106 0 300 1105 1 99999 1 225 225 225 1101 314 0 0 106 0 0 1105 1 99999 8 677 226 224 1002 223 2 223 1006 224 329 1001 223 1 223 1108 226 226 224 1002 223 2 223 1005 224 344 101 1 223 223 1108 226 677 224 1002 223 2 223 1006 224 359 101 1 223 223 107 226 226 224 1002 223 2 223 1005 224 374 101 1 223 223 1107 226 226 224 1002 223 2 223 1005 224 389 101 1 223 223 107 677 677 224 102 2 223 223 1006 224 404 101 1 223 223 1008 226 226 224 1002 223 2 223 1006 224 419 101 1 223 223 108 677 226 224 1002 223 2 223 1006 224 434 101 1 223 223 1108 677 226 224 102 2 223 223 1005 224 449 101 1 223 223 1008 677 226 224 102 2 223 223 1006 224 464 1001 223 1 223 108 677 677 224 102 2 223 223 1005 224 479 101 1 223 223 7 677 677 224 102 2 223 223 1005 224 494 1001 223 1 223 8 226 677 224 102 2 223 223 1006 224 509 101 1 223 223 107 677 226 224 1002 223 2 223 1005 224 524 1001 223 1 223 7 677 226 224 1002 223 2 223 1005 224 539 1001 223 1 223 1007 226 677 224 1002 223 2 223 1005 224 554 1001 223 1 223 8 677 677 224 102 2 223 223 1006 224 569 101 1 223 223 7 226 677 224 102 2 223 223 1006 224 584 1001 223 1 223 1008 677 677 224 102 2 223 223 1005 224 599 101 1 223 223 1007 677 677 224 1002 223 2 223 1006 224 614 101 1 223 223 1107 677 226 224 1002 223 2 223 1006 224 629 101 1 223 223 1107 226 677 224 1002 223 2 223 1006 224 644 101 1 223 223 1007 226 226 224 102 2 223 223 1005 224 659 1001 223 1 223 108 226 226 224 102 2 223 223 1006 224 674 101 1 223 223 4 223 99 226])
