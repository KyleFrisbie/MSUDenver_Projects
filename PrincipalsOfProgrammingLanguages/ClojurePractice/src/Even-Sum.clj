;(defn even-sum
;  ([list]
;    (if (empty? list)
;      0
;      (if (= 0 (mod (first list) 2)
;        even-sum (pop list) (first list)))))
;  ([list sum]
;    (if (empty? list)
;      sum
;      (if (= 0 (mod (first list) 2)
;        (even-sum (pop list) (+ sum (first list)))
;        (even-sum (pop list) sum)))))
;
;(println (even-sum '())))

(def even-count
  (fn [x]
    (if (even? x)
      x
      0)))

(defn even-sum
  ([list]
    (if (empty? list)
      0
      (even-sum list 0)
      )
    )
  ([list sum]
    (if (empty? list)
      sum
      (even-sum (pop list) (+ sum (even-count(first list)))))))

(println (even-sum '(2 7 3 11 5 1 6)))