(def a-list '(1 2 3 4 5 6 7 8 9))

(defn even-sum [L]
  (cond (empty? L)
    0
    (even? (first L))
    (+ (first L) (even-sum (rest L)))
    true
    (even-sum (rest L))))

(println (even-sum a-list))