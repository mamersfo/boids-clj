(ns boids.main
	(:gen-class)
	(:use boids.core)
	(:require [boids.net :as net]
			  [boids.color :as color]))
			
(def ^:dynamic *radar-url* "http://localhost:1337/boids")
(def ^:dynamic *radar-udp-port* 41234)

(def ^:dynamic *min-change* -0.25)
(def ^:dynamic *max-change* +0.25)
			
(def boid (atom 
	{:pid 0
	 :color "000000"
	 :location {:x 0 :y 0}
	 :velocity {:x 0 :y 0}
	 :theta 0.0}))

(defn init []
	(swap! boid assoc 
		:pid (Integer/parseInt (System/getProperty "pid")) 
		:color (color/random)
		:location {:x (/ *width* 2) :y (/ *height* 2)}
		:velocity {:x (random -1 1) :y (random -1 1)}))

(defn update []
	(if-let [boids (read-string (net/http-get *radar-url*))]
		(let [change (random *min-change* *max-change*)
			  boid-ref (swap! boid assoc :theta (+ (@boid :theta) change))
			  move (move boid-ref (vals (dissoc boids (boid-ref :pid))))]
			(do
				(swap! boid assoc 
					:velocity (move :velocity) 
					:location (move :location))
				(net/udp-send *radar-udp-port* @boid)))))
							
;;; execute with exec java -Dpid=$$ -jar boids.jar
(defn -main []
	(init)
	(loop []
		(update)
		(Thread/sleep 500)
		(recur)))