(ns boids.test.color
  (:use [boids.color])
  (:use [clojure.test]))

(deftest test-random
	(let [result (random)]
		(is (not (nil? (re-matches #"[0-9A-F]{6}" result))))))
