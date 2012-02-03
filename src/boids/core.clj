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
	42)

(defn constrain [location radius width height]
	{:x 0 :y 0})

(defn steer [boid location]
	{:x 0 :y 0})
		
(defn separate [boid boid-seq]
	{:x 0 :y 0})

(defn wander [boid]
	{:x 0 :y 0})
			
(defn move [boid boid-seq]
	{:velocity {:x 0 :y 0}
     :location {:x 0 :y 0}})
