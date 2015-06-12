; alone.clj
; Author: Kyle L Frisbie
; Created: 2/3/15
;
; Takes two lists as paramters, returns a new list containing only the items
;   with no match between the two lists

; get length of list
(defn get-length
  ([L]
    (if (empty? L)
      0
      (get-length (rest L) 1)))
  ([L length]
    (if (empty? L)
      length
      (get-length (rest L) (+ length 1)))))

; build a new list from two seperate lists
(defn build-list [L-a L-b]
  (if (empty? L-b)
    (reverse L-a)
    (build-list (conj L-a (first L-b)) (rest L-b))))

; overloaded function, returns new list of unique items from comparison of two
;   lists
(defn alone
  ([L R]
    (alone L L R R '()))
  ([L L-pure R R-pure new-L]
    (cond (and (empty? L) (empty? R))
        (alone R-pure R-pure L-pure L-pure '() new-L)
      (empty? R)
      (alone (rest L) L-pure R-pure R-pure (conj new-L (first L)))
      (= (first L) (first R))
      (alone (rest L) L-pure (rest R) R-pure new-L)
      :else
      (alone L L-pure (rest R) R-pure new-L)))
  ([L L-pure R R-pure new-L final-L]
    (cond
      (and (empty? L) (empty? R))
        (build-list final-L (reverse new-L))
      (empty? R)
      (alone (rest L) L-pure R-pure R-pure (conj new-L (first L)) final-L)
      (= (first L) (first R) )
      (alone (rest L) L-pure (rest R) R-pure new-L final-L)
      :else
      (alone L L-pure (rest R) R-pure new-L final-L))))

; begin testing
(println "Begin alone testing: \n")

; test lists with some unique items
(println "test lists with some unique items")
(print (alone '(3 4 5 6 7) '(1 2 3 4 5)) "-> ")
(println (= (alone '(3 4 5 6 7) '(1 2 3 4 5)) '(6 7 1 2)))
(print (alone '(3 4) '(3)) "-> ")
(println (= (alone '(3 4) '(3)) '(4)))
(print (alone '(dog cat cow) '(cat cow)) "-> ")
(println (= (alone '(dog cat cow) '(cat cow)) '(dog)))

(println)

; test lists with no unique items
(println "test lists with no unique items")
(print (alone '(dog cat cow pig) '(dog cat cow pig)) "-> ")
(println (= (alone '(dog cat cow pig) '(dog cat cow pig)) '()))
(print (alone '(dragon & pony 90000) '(dragon & pony 90000)) "-> ")
(println (= (alone '(dragon & pony 90000) '(dragon & pony 90000)) '()))

(println)

; test lists with no items
(println "test lists with no items")
(print (alone '() '()) "-> ")
(println (= (alone '() '()) '()))
(print (alone '(house honey bee) '()) "-> ")
(println (= (alone '(house honey bee) '()) '(house honey bee)))