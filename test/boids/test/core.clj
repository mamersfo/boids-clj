(ns boids.test.core
  (:use [boids.core])
  (:use [clojure.test]))

(deftest test-random
	(let [result (random -1 +1)]
		(is (> result -1))
		(is (< result +1))))

(deftest test-constrain
	(let [radius 4.0 width 400 height 400]
		(is (= (constrain {:x 200.0 :y 200.0} radius width height) {:x 200.0 :y 200.0}))
		(is (= (constrain {:x  -5.0 :y  -5.0} radius width height) {:x 404.0 :y 404.0}))
		(is (= (constrain {:x 405.0 :y 405.0} radius width height) {:x  -4.0 :y  -4.0}))))
		
(deftest test-steer
	(let [boid {:location {:x 200 :y 200} :velocity {:x 0.25 :y 0.25}}
		  target {:x 100 :y 100}
		  result (steer boid target)]
		(is (= result {:x -0.03535533905932738, :y -0.03535533905932738}))))
		
(deftest test-separate
	(let [boid {:location {:x 100 :y 100}}
		  boids [{:pid 1 :location {:x 95 :y 95}} {:pid 2 :location {:x 115 :y 115}}]
		  result (separate boid boids)]
		(is (= result {:x 0.03333333333333333, :y 0.03333333333333333}))))
		
(deftest test-wander
	(let [boid {:location {:x 200 :y 200} :velocity {:x 0.25 :y 0.25} :theta 0.23411792607289378}
		  result (wander boid)]
		(is (= result {:x 0.04343451419472347, :y 0.02476778102027644}))))

(deftest test-move
	(let [boid {:location {:x 100 :y 100} :velocity {:x 0.5 :y 0.5} :theta 0.5}
		  boids [{:pid 1 :location {:x 95 :y 95}} {:pid 2 :location {:x 105 :y 105}}]
		  result (move boid boids)]
		(is (= result {:velocity {:x 0.5387636441786201 :y 0.5315813218531671}
					   :location {:x 100.0 :y 100.0}}))))
