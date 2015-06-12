; similar.clj
; Author: Kyle L Frisbie
; Created: 2/3/15
;
; Takes two lists as paramters, returns a new list containing only the items
;   matching in each of the two lists

(defn get-length
  ([L]
    (if (empty? L)
      0
      (get-length (rest L) 1)))
  ([L length]
    (if (empty? L)
      length
      (get-length (rest L) (+ length 1)))))

(defn similar
  ([L-a L-b]
    (similar L-a L-b L-b '() (get-length L-b) 1))
  ([L-a L-b-pure L-b L-new length current]
    (cond (empty? L-a)
      (reverse L-new)
      (= current (+ 1 length))
      (similar (rest L-a) L-b-pure L-b-pure L-new length 1)
      (= (first L-a) (first L-b))
      (similar (rest L-a) L-b-pure L-b-pure (conj L-new (first L-a)) length 1)
      :else (similar L-a L-b-pure (rest L-b) L-new length (+ 1 current)))
    )
  )

; begin testing
(println "Begin similar testing: \n")

; test lists with similar items
(println "test lists with similar items")
(print (similar '(3 4 5 6 7) '(1 2 3 4 5)) "-> ")
(println (= (similar '(3 4 5 6 7) '(1 2 3 4 5)) '(3 4 5)))
(print (similar '(3) '(3)) "-> ")
(println (= (similar '(3) '(3)) '(3)))
(print (similar '(dog cat cow) '(cat cow)) "-> ")
(println (= (similar '(dog cat cow) '(cat cow)) '(cat cow)))

(println)

; test lists with no similar items
(println "test lists with no similar items")
(print (similar '(dog cat) '(cow pig)) "-> ")
(println (= (similar '(dog cat) '(cow pig)) '()))
(print (similar '(1 2 3) '(dragon & pony 90000)) "-> ")
(println (= (similar '(1 2 3) '(dragon & pony 90000)) '()))

(println)

; test lists with no items
(println "test lists with no items")
(print (similar '() '()) "-> ")
(println (= (similar '() '()) '()))
(print (similar '(house honey bee) '()) "-> ")
(println (= (similar '(house honey bee) '()) '()))
