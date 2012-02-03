(ns boids.test.vector
  (:use [boids.vector])
  (:use [clojure.test]))

(deftest test-add
	(let [result (add {:x 100 :y 100} {:x 100 :y 100})]
		(is (= result {:x 200 :y 200}))))

(deftest test-subtract
	(let [result (subtract {:x 100 :y 100} {:x 100 :y 100})]
		(is (= result {:x 0 :y 0}))))
		
(deftest test-multiply
	(let [result (multiply {:x 100 :y 100} 10)]
		(is (= result {:x 1000 :y 1000}))))
		
(deftest test-divide
	(let [result (divide {:x 100 :y 100} 10)]
		(is (= result {:x 10 :y 10}))))

(deftest test-do-not-divide-by-zero
	(let [result (divide {:x 100 :y 100} 0)]
		(is (= result {:x 100 :y 100}))))

(deftest test-magnitude
	(let [result (magnitude {:x 100 :y 100})]
		(is (= result 141.4213562373095))))

(deftest test-normalize
	(let [result (normalize {:x 100 :y 100})]
		(is (= result {:x 0.7071067811865475, :y 0.7071067811865475}))))
		
(deftest test-distance
	(let [result (distance {:x 50 :y 50} {:x 100 :y 100})]
		(is (= result 70.71067811865476))))
		
(deftest test-limit
	(let [result (limit {:x 50 :y 50} 10.0)]
		(is (= result {:x 7.071067811865475 :y 7.071067811865475}))))
