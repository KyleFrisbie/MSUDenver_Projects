; delete-all-at.clj
; Author: Kyle L Frisbie
; Created: 2/3/15
;
; Removes items at multiple user defined position. Returns new list with the
;   items at specified positions removed or nil if list is empty.

; Recursive helper function, to be called by main function
(defn delete-at-all-recur [L position args new-L count]
  (cond (empty? L)
    (reverse new-L)
    (= position count)
    (delete-at-all-recur
      (rest L) (first args) (rest args) new-L (+ 1 count))
    :else
    (delete-at-all-recur
      (rest L) position args (conj new-L (first L)) (+ 1 count))))

; Main function to be called by client
(defn delete-at-all [L & args]
  (delete-at-all-recur L (first args) (rest args) '() 0)
  )

; begin testing with removal of valid items
(println "Beginning delete-all-at: \n")

(println "testing removal of positions within list:")
(print (delete-at-all '(1 2 3 4) 0 1 2) "-> ")
(println (= (delete-at-all '(1 2 3 4) 0 1 2) '(4)))
(print (delete-at-all '(1 2 3 4) 0 1 ) "-> ")
(println (= (delete-at-all '(1 2 3 4) 0 1 ) '(3 4)))
(print (delete-at-all '(1 2 3 4) 0) "-> ")
(println (= (delete-at-all '(1 2 3 4) 0) '(2 3 4)))
(print (delete-at-all '(q z y m w x r) 0 3 5) "-> ")
(println (= (delete-at-all '(q z y m w x r) 0 3 5) '(z y w r)))

(println)

(println "testing removal of postions outside of list:")
(print (delete-at-all '(1 2 3 4) 5) "-> ")
(println (= (delete-at-all '(1 2 3 4) 5) '(1 2 3 4)))
(print (delete-at-all '(q z y m w x r) -1) "-> ")
(println (= (delete-at-all '(q z y m w x r) -1) '(q z y m w x r)))