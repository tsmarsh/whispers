(ns whispers.core-test
  (:require [clojure.test :refer :all]
            [tephalome.core]
            [whispers.core :refer :all]))

(defn start-server
  [f]
  (t/start 6666)
  (f)
  (t/stop))

(defn rooms-and-corners
  [f]
  (reset! t/rooms {})
  (f))

(use-fixtures :once start-server)

(use-fixtures :each rooms-and-corners)

(deftest join
  (testing "can join a room"))
