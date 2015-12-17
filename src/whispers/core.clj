(ns whispers.core
  (:require [tephalome.encryption.rsa :as rsa]))

(defn client 
  [server-url]
  (let [{:keys [public private]} (rsa/generate-keys)]
    {:public public
     :private private
     :server-url server-url}))
