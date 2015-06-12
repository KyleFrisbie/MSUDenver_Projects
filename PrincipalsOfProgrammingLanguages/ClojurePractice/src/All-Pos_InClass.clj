(defn all-pos [L]
  (cond (empty? L)
    true
    (> (first L) 0)
    (all-pos (rest L))
    true
    false))

(println (all-pos '(1 2 3 4 5 6 7 8 9)))