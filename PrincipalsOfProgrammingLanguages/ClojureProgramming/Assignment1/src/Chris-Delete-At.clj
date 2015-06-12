;(def num-count 0)
;(def x '(1 2 3 4))
;(def y '())
;
;(defn delete-at [x pos]
;  (cond (empty? x)
;    (do (println y) y)
;    (< num-count pos)
;    (do (println y) (inc num-count) (conj y (first x)) (delete-at (rest x) pos))
;    :else
;    (do (println y) (conj y (rest x)) (delete-at '() pos))))
;
;(println (delete-at x 3))

;(defn build-list [L-a L-b]
;  (if (empty? L-b)
;    (reverse L-a)
;    (build-list (conj L-a (first L-b)) (rest L-b))))

(defn delete-at
  ([x pos]
    (if (empty? x)
      nil
      (delete-at x pos '() 0)))
  ([x pos y num-count]
    (if (< num-count pos)
      (delete-at (rest x) pos (conj y (first x)) (inc num-count))
      (build-list y (rest x)))))

(println (delete-at '(1 2 3 4) 1))