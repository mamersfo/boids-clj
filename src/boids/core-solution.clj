(ns boids.core
	(require [boids.vector :as v]))

(def ^:dynamic *max-force* 0.05)
(def ^:dynamic *max-speed* 3.0)
(def ^:dynamic *wander-r* 16.0)
(def ^:dynamic *wander-d* 60.0)
(def ^:dynamic *radius* 4.0)
(def ^:dynamic *width* 400)
(def ^:dynamic *height* 400)

(defn random [lower upper]
	(+ lower (rand (- upper lower))))

(defn constrain [location radius width height]
	(let [x (location :x)
		  y (location :y)]
		{:x (Math/floor
				(cond
					(< x (- radius)) (+ width radius)
					(> x (+ width radius)) (- radius)
					:else x))
		 :y (Math/floor
				(cond
					(< y (- radius)) (+ height radius)
					(> y (+ height radius)) (- radius)
					:else y))}))

(defn steer [boid location]
	(let [desired (v/subtract location (boid :location))]
		(if (> (v/magnitude desired) 0)
			(-> desired
				(v/normalize)
				(v/multiply *max-speed*)
				(v/subtract (boid :velocity))
				(v/limit *max-force*))
			{:x 0 :y 0})))
		
(defn separate [boid boid-seq]
	(if (> (count boid-seq) 0)
		(let [boids (map #(assoc % :distance (v/distance (boid :location) (% :location))) boid-seq)
			  sum (reduce (fn [sum b]
						(v/add sum (-> (boid :location)
									   (v/subtract (b :location))
							   	   	   (v/normalize)
							  	   	   (v/divide (b :distance)))))
						{:x 0 :y 0} boids)]
			(v/divide sum (count boid-seq)))
		 {:x 0 :y 0}))

(defn wander [boid]
	(do
		(let [circle-loc (-> (boid :velocity)
							 (v/multiply *wander-d*)
							 (v/add (boid :location)))
			  circle-offset {:x (* *wander-r* (Math/cos (boid :theta)))
							 :y (* *wander-r* (Math/sin (boid :theta)))}]
			(steer boid (v/add circle-loc circle-offset)))))
			
(defn move [boid boid-seq]
	(let [velocity (-> (boid :velocity)
					   (v/add (wander boid))
					   (v/add (separate boid boid-seq))
					   (v/limit *max-speed*))
		  location (-> (boid :location)
					   (v/add velocity)
					   (constrain *radius* *width* *height*))]
		{:velocity velocity
		 :location location}))