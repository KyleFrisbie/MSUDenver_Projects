(def tasks '((purchase_lot 2)
              (design_house 5)
              (get_permit 1 purchase_lot design_house)
              (get_bids 14 purchase_lot design_house)
              (select_subs 2 get_bids)
              (excavate 1 get_permit select_subs)
              (construct_basement 7 excavate)
              (order_windows_doors 3 purchase_lot design_house)
              (get_windows_doors 10 order_windows_doors)
              (frame 12 get_permit select_subs)
              (rough_plumbing 5 frame)
              (rough_electric 3 frame)
              (roof 4 frame)
              (install_windows_doors 7 get_windows_doors rough_plumbing rough_electric)
              (vapor_barrier_insulation 2 roof install_windows_doors)
              (drywall 5 vapor_barrier_insulation)
              (inside_paint 3 drywall)
              (cupboards 3 inside_paint)
              (carpet_floor 5 inside_paint)
              (lights 2 inside_paint)
              (plumbing_heating 6 inside_paint)
              (siding 2 roof install_windows_doors)
              (outside_paint 3 siding)
              (move_house 1 cupboards carpet_floor lights plumbing_heating outside_paint)
              (connections 2 construct_basement move_house)
              (landscape 4 construct_basement move_house)
              (move_in 0 landscape connections)))


;helper function: gets the time it takes for a single job
(defn getSecond [L_Helper]
  (second L_Helper))


;The helper function goes into the list, otherwise it would get the whole 
;data in the parenthesis, such as (purchase_lot 2) where we would like 
;to retrieve the number and not the data in front of it
(defn sum [L]
  (if (empty? L) 0
    (+ (getSecond (first L)) (sum (rest L)))))


;helper function: compares the job against the first of the list of task,
;returns true if they are the same meaning that the certain job is contained
;within the list
(defn getJob [Job L_Helper]
  (= Job (first (first L_Helper))))


;calling the job as first of Job, becuase when predecessors is called
;the function is called as (predecessors 'NAME_JOB tasks)
(defn predecessors [J L]
  (cond
    (empty? L) ()
    (getJob  J L)  (rest (rest (first L)))
    :else (predecessors J (rest L))))


;Helper function: gets the time that one single job takes to complete, without counting
;its predecessors
(defn gettime [J Job]
  (cond
    (getJob J Job) (getSecond (first Job))
    :else (gettime J (rest Job))))


;Helper function to put together the two list of the predecessors that have been 
;found from the main job and then the predecessors of the predecessors since we want to put the two
;lists into a single list
(defn concatenate_lists [a b]
  (cond
    (empty? a) b
    :else (cons (first a) (concatenate_lists (rest a) b))))


;Takes Jobs and a list of tasks as parametes, checks if the job is empty and returns and
;empty list, otherwise it would set a variable x to be the list of predecessors from 
;the first list of jobs and then it would create a list from the predecessors from the 
;job that we input and then the predecessors of the predecessors of the job that we entered
;by using the helper function concatenate_lists
(defn get_predecessors [J L]
  (cond
    (empty? J) ()
    :else (let [x (predecessors (first J) L) ] x
            (concatenate_lists x (get_predecessors (concatenate_lists (rest J) x) L)))))


;Takes a Job and the list of tasks from the user and will concatenate the job to nil
;in order to make the job into a list with a nil at the end that will help to
;tell the job that there is nothing at the end 
(defn get_all_preds [J L]
  (cond
    (nil? J) ()
    :else(let [job (cons J nil)] (get_predecessors job L))))


;The function takes two variables, the second job from the two jobs that the use inputs
;then finds the predecessors of the second job and puts it into a variable named x
(defn get_predecessors_job_two [J L]
  (let [x (get_all_preds J L)] x))


;Function takes two parameter, the first job and the list of the all the predecessors
;of the second job in the list, returns true if the first job matches any of the 
;predecessors, nil otherwise
(defn comparison [a b]
  (cond
    (empty? b) nil
    (= a (first b)) true
    :else (comparison a (rest b))))


;The function takes a list of two jobs and the tasks from which it sees if the 
;first job must predece the second job (is a predecessors of the second job), function 
;must be entered in the form of (predeces '(job1 job2) tasks)
(defn predeces [jobs L]
  (cond
    (empty? jobs) ()
    :else (comparison (first jobs)(get_predecessors_job_two (second jobs) L))))


;The function takes the list of the predecessors from the main job that the
;user inputs then it looks for the time it takes to complete individually
;and adds them together
(defn getnumber [x L]
  (cond
    (empty? x) 0
    :else (+ (gettime (first x) L) (getnumber (rest x) L))))


;Grabs the list of the predecessors and will remove the elements of the list that are 
;being repeated by converting the list into a set and then back to a list
(defn filtering [a b]
  (into () (set (get_all_preds a b))))


;Puts the list of the predecessors into a variable x which is passed into the
;function getnumber 
(defn gettimepredecessors [J L]
  (let [x (filtering J L)]
    (getnumber x L)))


;Funtion takes a job and a list from which it would look for the time of the predecessors
;+1 in order to find the day that a job can start, it will display an message output to the user
;of the number that it would take for that job to start
(defn start_day [a b]
  (println "The job would start on day "
    (+ (gettimepredecessors a b) 1) "\nsince it would take"
    (gettimepredecessors a b) " \nday(s)to complete the other task(s)"))


;Grabs the number of the start day of the job
(defn starttime_job [J L]
  (cond
    (nil? J) 0
    :else (+ (gettimepredecessors J L) 1 )))


;constructs a list in which the times of each job are added to the list
(defn gettime_jobs[jobs L]
  (cond
    (empty? jobs) ()
    :else (cons (starttime_job (first jobs) L)(gettime_jobs (rest jobs) L))))


;gets the maximum number from the list that contains the times of the jobs 
(defn getmaximum_jobs [jobs L]
  (apply max (gettime_jobs jobs L)))


;Function compares the time of the maximum time of the jobs and then finds the specific job that 
;takes that specific time to complete, returns the time and the name of the job that finishes 
;at the greates time
(defn get_max [jobs L]
  (cond
    (empty? jobs) ()
    (= (getmaximum_jobs jobs L) (starttime_job (first jobs) L))
    (cons (starttime_job (first jobs) L)(list(first jobs)))
    :else (get_max (rest jobs) L)))


(defn get_name_max [J L]
  (cond
    (nil? J) ()
    :else (second (get_max (predecessors J L) L))))


(defn critical_path [J L]
  (cond
    (nil? (get_name_max J L))
    ()
    :else (cons
            (get_name_max J L)
            (critical_path
              (get_max
                (predecessors
                  (get_name_max J L) L) L) L))))

(println (critical_path 'roof tasks))