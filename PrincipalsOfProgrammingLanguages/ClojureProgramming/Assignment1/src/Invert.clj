; invert.clj
; Author: Kyle L Frisbie
; Created: 2/4/15
;
; Takes a single list with an even number of paramaters, inverts each pair of
;   parameters and returns the newly inverted list. If lists of odd numbers are
;   passed, a list with the odd one left out is returned, with other values
;   inverted.

(defn invert
  ([L]
    (invert (first L) (first (rest L)) (rest (rest L)) '()))
  ([x y L new-L]
    (cond (or (= x nil) (= y nil))
      (reverse new-L)
      :else
      (invert (first L) (first (rest L)) (rest (rest L)) (conj new-L y x)))))

; begin testing
(println "Begin invert testing: \n")

; test lists with even numbers of items
(println "test lists with even numbers of items")
(print (invert '(1 2 3 4 5 6)) "-> ")
(println (= (invert '(1 2 3 4 5 6)) '(2 1 4 3 6 5)))
(print (invert '(3 4)) "-> ")
(println (= (invert '(3 4)) '(4 3)))
(print (invert '(dog cat cow mouse)) "-> ")
(println (= (invert '(dog cat cow mouse)) '(cat dog mouse cow)))
(print (invert '(dragon & pony (dog cat cow mouse))) "-> ")
(println (= (invert '(dragon & pony (dog cat cow mouse)))
           '(& dragon (dog cat cow mouse) pony)))

(println)

; test lists with odd numbers of items
(println "test lists with odd numbers of items")
(print (invert '(dog cat cow)) "-> ")
(println (= (invert '(dog cat cow)) '(cat dog)))
(print (invert '(dragon & pony)) "-> ")
(println (= (invert '(dragon & pony)) '(& dragon)))

(println)

; test lists with no items
(println "test lists with no items")
(print (invert '()) "-> ")
(println (= (invert '()) '()))