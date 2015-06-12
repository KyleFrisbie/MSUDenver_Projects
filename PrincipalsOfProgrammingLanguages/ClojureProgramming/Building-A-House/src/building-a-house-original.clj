; build-list helper method
;(defn build-list [L-a L-b]
;  (if (or (symbol? L-b) (empty? L-b))
;    L-a
;    (build-list (conj L-a (first L-b)) (rest L-b))))

(defn build-list [L-a L-b]
  (cond (symbol? L-b)
    (conj L-a L-b)
    (empty? L-b)
    L-a
    :else
    (build-list (conj L-a (first L-b)) (rest L-b))))
(def tasks
  '((purchase_lot 2) (design_house 5) (get_permit 1 purchase_lot design_house)
     (get_bids 14 purchase_lot design_house) (select_subs 2 get_bids)
     (excavate 1 get_permit select_subs) (construct_basement 7 excavate)
     (order_windows_doors 3 purchase_lot design_house)
     (get_windows_doors 10 order_windows_doors)
     (frame 12 get_permit select_subs) (rough_plumbing 5 frame)
     (rough_electric 3 frame) (roof 4 frame)
     (install_windows_doors 7 get_windows_doors rough_plumbing rough_electric)
     (vapor_barrier_insulation 2 roof install_windows_doors)
     (drywall 5 vapor_barrier_insulation)
     (inside_paint 3 drywall) (cupboards 3 inside_paint)
     (carpet_floor 5 inside_paint) (lights 2 inside_paint)
     (plumbing_heating 6 inside_paint) (siding 2 roof install_windows_doors)
     (outside_paint 3 siding)
     (move_house 1 cupboards carpet_floor lights plumbing_heating outside_paint)
     (connections 2 construct_basement move_house)
     (landscape 4 construct_basement move_house)
     (move_in 0 landscape connections)))
; #1 sum - takes the list of tasks as an argument then returns the sum of all of the days needed for the individual jobs.
(defn sum
  ([tasks]
    (sum tasks 0 0))
  ([tasks days i]
    (if (= i (count tasks))
      days
      (sum tasks (+ (second (nth tasks i)) days) (inc i)))))
(println (sum tasks))
; #2 predecessors - takes a specific job and the list of tasks then returns
;   a list of the immediate predecessors for that job.
;   (predecessors 'vapor_barrier_insulation tasks) should return
;   (roof install_windows_doors)
(defn predecessors
  ([job tasks]
    (if (nil? job)
      '()
      (predecessors job tasks 0)))
  ([job tasks i]
    (cond (= i (count tasks))
      '()
      (= job (first (nth tasks i)))
      (rest (rest (nth tasks i)))
      :else (predecessors job tasks (inc i)))))
(println (predecessors 'vapor_barrier_insulation tasks))
; #3 gettime - takes a job and the list of tasks then returns the time that
;   job takes
(defn gettime
  ([job tasks]
    (if (nil? job)
      nil
      (gettime job tasks 0)))
  ([job tasks i]
    (cond (= i (count tasks))
      nil
      (= job (first (nth tasks i)))
      (second (nth tasks i))
      :else (gettime job tasks (inc i)))))
(println (gettime 'get_permit tasks))
; conj item x to list L only if item doesn't currently exist in list
(defn conj-no-dupe
  ([L x]
    (conj-no-dupe L x 0))
  ([L x i]
    (cond (= i (count L))
      (conj L x)
      (= (nth L i) x)
      L
      :else
      (conj-no-dupe L x (inc i)))))
; #4 get-all-preds - takes a specific job and the list of tasks then returns
;   a list of all of the predecessors for that job
(defn get-all-preds
  ([job tasks]
    (get-all-preds job (predecessors job tasks) tasks '()))
  ([job preds tasks all-preds]
    (if (empty? preds)
      all-preds
      (get-all-preds (first preds)
        (build-list
          (predecessors
            (first preds) tasks) (rest preds))
        tasks (conj-no-dupe all-preds (first preds))))))
(println (get-all-preds 'move_in tasks))
; #5 precedes - takes two specific jobs and the list of tasks then returns
;   true if the first job must precede the other and nil otherwise
; check to see if item exitsts in list
(defn not-exists?
  ([L x]
    (not-exists? L x 0))
  ([L x i]
    (cond (= i (count L))
      true
      (= (nth L i) x)
      false
      :else
      (not-exists? L x (inc i)))))
(defn precedes [J1 J2 L]
  (if (not-exists? (get-all-preds J1 tasks) J2)
    true
    nil))
(println (precedes 'purchase_lot 'move_in tasks))
; #6 start_day - takes a specific job and the list of tasks then returns the
;   day that this job can start
(defn start-day-help [job jobs days tasks]
  (if (empty? jobs)
    (+ 1 (- days (gettime job tasks)))
    (start-day-help job (rest jobs)
      (+ (gettime (first jobs) tasks) days) tasks)))
(defn start-day
  ([job tasks]
    (start-day-help job (get-all-preds job tasks) (gettime job tasks) tasks)))
(println (start-day 'roof tasks))
; #7 get_max - takes a list of job names and the list of tasks then returns a
;   list with the time and the job that finishes at the greatest time
;   ==> (get_max '(frame roof select_subs) tasks)
;   should return:
;   (37 roof)	;since roof cannot finish until day 37
;   (assuming the entire project started on day 1).
(defn get-max
  ([job-list tasks]
    (get-max (first job-list) (rest job-list) tasks))
  ([job job-list tasks]
    (cond (empty? job-list)
      (list (start-day job tasks) job)
      (precedes job (first job-list) tasks)
      (get-max (first job-list) (rest job-list) tasks)
      :else
      (get-max job (rest job-list) tasks))))
(println (get-max '(frame roof select_subs) tasks))
; #8 critical_path - takes a job and the list of tasks then returns a list of
;   the jobs on the critical path to getting this job done in the least
;   amount of time

(defn critical-path
  ([job tasks]
    (critical-path (first (predecessors job tasks)) (rest (predecessors job tasks)) tasks))
  ([critical preds tasks]
    (cond
      (nil? critical)
      '()
      (empty? preds)
      (cons critical (critical-path critical tasks))
      (> (gettime critical tasks) (gettime (first preds) tasks))
      (critical-path critical (rest preds) tasks)
      :else
      (critical-path (first preds) (rest preds) tasks))))

(println (critical-path 'move_in tasks))

; #9 depends_on - takes a job and the list of tasks then returns a list of the
;   jobs that cannot be started until this job has completed. This should
;   return all future jobs, not just the ones immediately affected.
(defn depends-on
  ([job tasks]
    (depends-on job tasks '()))
  ([job tasks dependents]
    (cond (empty? tasks)
      dependents
      (= job (first (first tasks)))
      (depends-on job (rest tasks) dependents)
      (precedes job (first (first tasks)) tasks)
      (depends-on job
        (rest tasks)
        (build-list dependents
          (first (first tasks))))
      :else
      (depends-on job (rest tasks) dependents))))
(println (depends-on 'roof tasks))