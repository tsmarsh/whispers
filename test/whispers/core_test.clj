(ns whispers.core-test
  (:require [clojure.test :refer :all]
            [tephalome.core :as t]
            [tephalome.encryption.rsa :as rsa]
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

(deftest client-test
  (with-redefs [rsa/generate-keys (constantly {:public "public"
                                           :private "private"})]
    (let [c (client "a url")]
      (testing "sets private key"
        (is (= "private" (:private c))))
      (testing "sets public key"
        (is (= "public" (:public c))))
      (testing "sets server url"
        (is (= "a url" (:server-url c)))))))

#_(deftest join
  (testing "can join a room")
  (let [c (client "localhost:6666")
        room (join c "test-room")]
    (is (= [:public c] (:members room)))))
