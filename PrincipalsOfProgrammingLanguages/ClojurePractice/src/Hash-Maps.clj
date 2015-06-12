;(def phones {:small :flip :medium :iphone5 :large :galaxy6})
;
;(println (dissoc phones :medium))

;(print (str "Type your name: "))
;(def a (read))
;(println a)

; tick tack toe game

;(def board {0 :X 5 :O})

(def board {0 nil 1 nil 2 nil 3 nil 4 nil 5 nil 6 nil 7 nil 8 nil})

(defn is-winner? [who]
  (or (= who (board 0) (board 1) (board 2))
    (= who (board 0) (board 3) (board 6))
    (= who (board 0) (board 4) (board 8))
    (= who (board 4) (board 1) (board 7))
    (= who (board 3) (board 8) (board 2))
    (= who (board 3) (board 4) (board 5))
    (= who (board 6) (board 7) (board 8))
    (= who (board 2) (board 4) (board 6))))

(defn print-board []
  (println (board 0) " | " (board 1) " | " (board 2))
  (println "-------------------")
  (println (board 3) " | " (board 4) " | " (board 5))
  (println "-------------------")
  (println (board 6) " | " (board 7) " | " (board 8))
  )

(defn play-move [spot who]
  (if (= (board spot) nil)
    (do (def board (assoc board spot who))
      (println "Successful play!")
      (print-board)
      (cond (is-winner? who)
        (println (str who " is winner!!!"))))
    (println "Choose a different spot, spot is filled: ")))