; delete-at.clj
; Author: Kyle L Frisbie
; Created: 1/30/15
;
; Removes item at user defined position, accepts either positive or negative
;   numbers. Negative numbers are interpreted as counting back from the end of
;   the list. Returns new list with item at specified position removed or nil
;   if list is empty, or position is outside of list parameters.

;   helper function, returns length of list
(defn get-length
  ([L]
    (if (empty? L)
      0
      (get-length (rest L) 1)))
  ([L length]
    (if (empty? L)
      length
      (get-length (rest L) (+ length 1)))))

; builds single list by combining two lists
(defn build-list [L-a L-b]
  (if (empty? L-b)
    (reverse L-a)
    (build-list (conj L-a (first L-b)) (rest L-b))))

; Removes item at specified position in list, returns nil if list is
; empty or specified position exceeds length of list. Negative positions
; interperted as removal of item from position "x" spaces from back of
; list (ie position = -1 is last item in list)
(defn delete-at
  ([L position]
    (def length (get-length L))
    ; if list is empty return nil
    (cond (empty? L)
      nil
      ; if position exceeds length of list return nil
      (or (< length (+ position 1)) (< length (* -1 position)))
      nil
      ; if position equals 0 return rest of list
      (or (= position 0) (= 0 (+ length position)))
      (rest L)
      (> 0 position)
      (delete-at (list (first L)) (rest L) (+ length position) 1)
      :else ; call overloaded function
      (delete-at (list (first L)) (rest L) position 1)))
  ([L-a L-b position current]
    ; if position exceeds current place in list, add next item from L-b to
    ;   L-a, add 1 to current, and call delete-at recursively
    (if (> position current)
      (delete-at (conj L-a (first L-b)) (rest L-b) position (+ 1 current))
      ; else found position, build new list excluding item at position,
      ;   return new list
      (build-list L-a (rest L-b)))))

; begin testing
(println "Begin delete-at testing: \n")

; test get-length
(println "test get-length")
(println (get-length '(1 2 3 4)))
(println)

; test build-list
(println "test build-list")
(println (build-list '(1) '(2 3 4)))
(println)

; test delete-at with positive positions within list paramters
(println "test positive positions within list paramters")
(print (delete-at '(1 2 3 4) 0) "-> ")
(println (= (delete-at '(1 2 3 4) 0) '(2 3 4)))
(print (delete-at '(1 2 3 4) 1) "-> ")
(println (= (delete-at '(1 2 3 4) 1) '(1 3 4)))
(print (delete-at '(1 2 3 4) 2) "-> ")
(println (= (delete-at '(1 2 3 4) 2) '(1 2 4)))
(print (delete-at '(1 2 3 4) 3) "-> ")
(println (= (delete-at '(1 2 3 4) 3) '(1 2 3)))
(print (delete-at
         '(cat dog cow mike kyle alyssa katie clark) 5) "-> ")
(println (= (delete-at
              '(cat dog cow mike kyle alyssa katie clark) 5)
           '(cat dog cow mike kyle katie clark)))
(print (delete-at
         '(1 one 2 two one-hundred 100 fifty five 5 55 & *) 2) "-> ")
(println (= (delete-at
              '(1 one 2 two one-hundred 100 fifty five 5 55 & *) 2)
           '(1 one two one-hundred 100 fifty five 5 55 & *)))
(println)

; test delete-at with positions greather than length of list
;   should return nil
(println "test positions greather than length of list")
(print (delete-at '(1 2 3 4) 4) " -> ")
(println (= (delete-at '(1 2 3 4) 4) nil))
(print (delete-at '(cat dog cow mike kyle alyssa katie clark) 100) "-> ")
(println (= (delete-at '(cat dog cow mike kyle alyssa katie clark) 100)
           nil))
(print (delete-at
         '(1 one 2 two one-hundred 100 fifty five 5 55 & *) 12) "-> ")
(println (=
          (delete-at
             '(1 one 2 two one-hundred 100 fifty five 5 55 & *) 12) nil))
(println)

; test negative positions within length of list
(println "test negative positions within length of list")
(print (delete-at '(1 2 3 4) -1) " -> ")
(println (= (delete-at '(1 2 3 4) -1) '(1 2 3)))
(print (delete-at '(1 2 3 4) -4) " -> ")
(println (= (delete-at '(1 2 3 4) -4) '(2 3 4)))
(print (delete-at
         '(1 one 2 two one-hundred 100 fifty five 5 55 & *) -3) "-> ")
(println (= (delete-at
              '(1 one 2 two one-hundred 100 fifty five 5 55 & *) -3)
           '(1 one 2 two one-hundred 100 fifty five 5 & *)))
(println)

; test negative positions greater than length of list
;   should return nil
(println "test negative positions greater than length of list")
(print (delete-at '(1 2 3 4) -5) " -> ")
(println (= (delete-at '(1 2 3 4) -5) nil))
(print (delete-at '(cat dog cow mike kyle alyssa katie clark) -11) "-> ")
(println (= (delete-at '(cat dog cow mike kyle alyssa katie clark) -11) nil))
(println)