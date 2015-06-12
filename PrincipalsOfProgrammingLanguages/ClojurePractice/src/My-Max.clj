(defn my-max
  ([list]
    (if (empty? list)
      -1
      (my-max (pop list) (first list))))
  ([list high]
    (if (empty? list)
      high
    (if (> (or (first list) 0) (or high 0))
      (my-max (pop list) (first list))
      (my-max (pop list) high)))))

(println (my-max '(1 2 3)))

; solved non-recursively
;(defn my-max
;  ([list]
;    (if (empty? list)
;      -1
;      (def sorted-list (sort > list))
;      )
;    (first sorted-list)
;    )
;  )
;
;(println (my-max '(1 1 5 10 1000)))