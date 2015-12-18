(ns whispers.core
  (:require [tephalome.encryption.rsa :as rsa]
            [tephalome.encryption.aes :as aes]
            [org.httpkit.client :as http]
            [clojure.edn :as edn]))

(defn client 
  [server-url]
  (let [{:keys [public private]} (rsa/generate-keys)]
    {:public public
     :private private
     :server-url server-url
     :serialized-key (rsa/serialize public)
     :decrypt (rsa/decrypt-key private)}))

(defn get-key
  [members]
  (if (> (count members) 1)
    (print "nope")
    (aes/generate-key)))

(defn join
  [client
   name]
  (let [{:keys [status
                body
                headers] :as resp} @(http/post (str (:server-url client) "/rooms/" name)
                                               {:headers {"x-public-key" (:serialized-key client)}})
        {x-key :x-key} headers
        k ((:decrypt client) x-key)
        aes-fn (aes/decrypt k)
        body (edn/read-string (aes-fn (slurp  body)))
        members (map rsa/gen-public body)]
    
    {:key (get-key members)
     :members members}))
