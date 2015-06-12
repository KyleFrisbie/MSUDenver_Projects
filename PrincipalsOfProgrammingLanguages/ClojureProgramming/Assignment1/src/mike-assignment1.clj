;
;;Write a Clojure function delete-at which takes two arguments:
;; a list and an integer and returns the list with the high-level
;; element at that location (zero-based indexing) deleted.
;; Example: (delete-at '(a b c d e) 3) should return (a b c e)
;
(defn delete-at
	"notes"
	[L x]
	(cond
		(empty? L) nil
		(<= x 0) (first L)
		:else (delete-at (rest L) (- x 1))
	)
)

(println (delete-at '(1 2 3 4 5) 2))

;
;; Write a Clojure function delete-all-at which takes as arguments
;; a list and one or more integers and returns the list with the
;; high-level elements at all the locations (zero-based indexing)
;; deleted. You can assume the integers are in ascending order.
;; Example: (delete-all-at '(a b c d e) 0 1 3) should return (c e)
;; ********* Bonus: Make no assumptions about the integers.
;
;
;	seems like if i could figure out how to deal with the variable amount
;	of arguments, it seems like i could just call delete-at, or the Clojure
;	built in remeove at nth.
;
;
;
;; Write the Clojure function similar that takes two lists as arguments
;;  and returns a list of the high-level elements that are in both lists.
;; For example: (similar '(a b c d e) '( x (a c) b r l)) should return (b)
;
;	(fn similar [L Q]
;		(cond (empty? L) nil
;			(empy? Q) nil
;			:else (fn [x Q]
;				(contains? x Q)) (first L) (set Q))
;				(similar ((rest L) Q)))
;
;	-if the anonymous function returns true, add (first L) to a list
;
;	-note that contains works with sets only.
;
;	-check to make sure first list isn't empty
;	-pass first from the first list into a helper function that takes an element
;	from the first list and checks to see if it exists in the second.. if it does
;	add it to a list.
;	-return the new third list.
;
;
;; Write the Clojure function alone that takes two lists as arguments and
;; returns a list of the high-level elements that are in one or the other
;; but not in both lists.
;; For example: (alone '(a b c d e) '( x (a c) b r l))
;; should return (a c d e x (a c) r l)
;
;		; -cant you combine the two lists and do count on each element.
;		; 	if the count is 1, then add to a list.
;
;
;; Write a version of cons called mcons that takes any number of arguments.
;; The penultimate argument should be consed into the last--the one before
;; that should be consed into the resulting value and so on. For example,
;; (mcons 'a 'b 'c '(d e)) should return (a b c d e).
;
;
;
;
;
;; Write the Clojure function invert that takes one parameter, a list with an
;; even-number of elements, and returns the list with each pair of elements swapped.
;; For example: (invert '(a b c d e f g h)) should return (b a d c f e h g).
;
;
;
;
;
;
;
;
;
