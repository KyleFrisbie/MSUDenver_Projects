(defn my-max [list]
  (cond (empty? list)
    -1
    (> (first list) (my-max (rest list)))
    (first list)
    :else (my-max (rest list))))

(println (my-max '(1 2 3 4 5 6 7 8 9)))

; alternative solution

(defn my-max2 [L]
  (if (empty? L)
    -1
    (let [me (first L)
          others (my-max2 (rest L))]
      (if (> me others)
        me
        others))))

(println (my-max2 '(1 2 3 4 5 6 7 8 9)))