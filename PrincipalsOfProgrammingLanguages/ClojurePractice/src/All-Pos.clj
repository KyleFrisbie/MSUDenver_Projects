(defn all-pos
  ([list]
    (if (empty? list)
      true
      (if (pos? (first list))
        (all-pos (pop list))
        false))))

(println (all-pos '(-1)))