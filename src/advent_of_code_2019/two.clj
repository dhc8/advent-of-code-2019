(ns advent-of-code-2019.two)

(defn run-intcode
  "Expects a vector of ints"
  [intcode]
  (loop [code intcode instruction-pointer 0]
    (let [instruction (get code instruction-pointer)
          input-positions (if (= 99 instruction)
                            nil
                            (subvec code (+ 1 instruction-pointer) (+ 3 instruction-pointer)))
          output-position (if (= 99 instruction)
                            nil
                            (get code (+ 3 instruction-pointer)))
          next-index (+ 4 instruction-pointer)]
      (case instruction
        99 code
        1 (recur (assoc code output-position (reduce + (mapv code input-positions)))
                 next-index)
        2 (recur (assoc code output-position (reduce * (mapv code input-positions)))
                 next-index)))))

(def puzzle-input [1 0 0 3 1 1 2 3 1 3 4 3 1 5 0 3 2 10 1 19 1 6 19 23 1 23 13 27 2 6 27 31 1 5 31 35 2 10 35 39 1 6 39 43 1 13 43 47 2 47 6 51 1 51 5 55 1 55 6 59 2 59 10 63 1 63 6 67 2 67 10 71 1 71 9 75 2 75 10 79 1 79 5 83 2 10 83 87 1 87 6 91 2 9 91 95 1 95 5 99 1 5 99 103 1 103 10 107 1 9 107 111 1 6 111 115 1 115 5 119 1 10 119 123 2 6 123 127 2 127 6 131 1 131 2 135 1 10 135 0 99 2 0 14 0])

(defn mangle-input
  [noun verb]
  (assoc (assoc puzzle-input 1 noun) 2 verb))

(def mangled-input (mangle-input 12 2))

(def bad-state (run-intcode mangled-input))

(def find-part-two
  (let [magic-number 19690720]
    (loop [noun 0
           verb 0]
      (let [output (get (run-intcode (mangle-input noun verb)) 0)]
        (if (= magic-number output)
          (+ (* 100 noun) verb)
          (if (= 99 noun)
            (if (= 99 verb)
              nil
              (recur 0 (+ 1 verb)))
            (recur (+ 1 noun) verb)))))))
