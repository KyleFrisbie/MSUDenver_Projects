; mcons.clj
; Author: Kyle L Frisbie
; Created: 2/4/15
;
; Takes any number of list arguments and returns a list of all arguments
;   "consed" into penultiate argument.

; get length of list
(defn get-length
  ([L]
    (if (or (empty? L) (symbol? L)
      0
      (get-length (rest L) 1)))
  ([L length]
    (if (empty? L)
      length
      (get-length (rest L) (+ length 1)))))

; strip list of embedded items and return new list
(defn strip-list [L new-L]
  (if (empty? L)
    (reverse new-L)
    (strip-list (rest L) (conj new-L (first L)))))

;; mcons recursive helper function, returns new list of mcons items
;(defn mcons-recur [L R new-L]
;  (cond (nil? L)
;    new-L
;    (symbol? L)
;    (mcons-recur (first R) (rest R) (conj new-L L))
;    (> (get-length L) 1)
;    (mcons-recur (first R) (rest R) (strip-list L new-L))
;    :else
;    (mcons-recur (first R) (rest R) (conj new-L L))))
;
;; function to be called by client for doing mcons work
;(defn mcons
;  [& args]
;    (if (empty? args)
;      args
;      (mcons-recur (first(reverse args)) (rest (reverse args)) '())))

; mcons recursive helper function, returns new list of mcons items
(defn mcons-recur [L R new-L]
  (cond (nil? L)
    new-L

    :else
    (mcons-recur (first R) (rest R) (conj new-L L))))

; function to be called by client for doing mcons work
(defn mcons
  [& args]
  (cond (empty? args)
    args
    (> (get-length (first (reverse args))) 1)
    (mcons-recur (second (reverse args)) (rest (rest (reverse args))) (strip-list (first (reverse args)) '()))
    :else
    (mcons-recur (first(reverse args)) (rest (reverse args)) '())))

; begin testing
(println "Begin mcons testing: \n")

; test lists
(println "test lists")
(print (mcons 'a 'b 'c '(d e)) "-> ")
(println (= (mcons 'a 'b 'c '(d e)) '(a b c d e)))
(print (mcons 'ranger 'danger 'stranger '(1 2)) "-> ")
(println (= (mcons 'ranger 'danger 'stranger '(1 2))
           '(ranger danger stranger 1 2)))
(print (mcons '(dog cat cow mouse)) "-> ")
(println (= (mcons '(dog cat cow mouse)) '(dog cat cow mouse)))
(print (mcons '(dog cat cow mouse) 'dragon '& 'pony) "-> ")
(println (= (mcons '(dog cat cow mouse) 'dragon '& 'pony)
           '(pony & dragon dog cat cow mouse)))

(println)

; test lists with no items
(println "test lists with no items")
(print (mcons '()) "-> ")
(println (= (mcons '()) '(())))