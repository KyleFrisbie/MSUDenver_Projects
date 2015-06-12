;;#1
;;recursively delete at a specific index in a list
;(defn delete-at [A B]
;	(cond	(empty? A) A 											;ensure we got a list
;			(> B 0) (cons (first A)(delete-at (rest A)(- B 1))) 	;if our delete index is > 0,
;																	;skip the first item in list
;																	;and recursively call self with rest of the list
;			:else (rest A)											;We must be at the desired index! Remove it
;	)
;)
;
;;#2
;;recursively delete at multiple indexes in a list
;(defn delete-at-all [A & B]
;	(cond
;		(empty? A) A ;Return the list if it's empty
;		(not (coll? (first B)))
;			(cond
;				(nil? B) A ;Did we get no arguments? If so delete nothing, return list
;				(= B 0) (delete-at-all (rest A)(map #(- % 1))) ;call self with rest and no arguments
;				(true (cons (first A) (delete-at-all (rest A)(map #(- % 1)))))
;			)
;		(coll? (first B)) ;now that they are in a collection
;	)
;)

;#Helper recurses through list to see if something exists
(defn does-exist? [A B]
  (cond
    (or (nil? B) (empty? B)) false
    (or (nil? A)(nil? B)) false
    (= A (first B)) true
    :else (does-exist? A (rest B))
    )
  )
;#3
;recursively check for similar items in a list
(defn similar [A B]
	(cond
		(or (empty? A)(empty? B)) '() 				;if either list is empty return an empty list
		(or (nil? A)(nil? B)) '()					;Make sure we haven't gone past the list
		(does-exist? (first A) B)					;Call helper to see if there is a match
			(cons (first A)(similar (rest A) B))	;Match! Call rest of list
		:else (similar (rest A) B)					;No match! Move on
	)
)


(println (similar '(1 2 3 4 5 6) '(2 4 6)))