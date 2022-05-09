(ns core
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))

(defn -main [& args]
  (let [params {"query" "restaurant" "key" (first args)}
        places-url "https://maps.googleapis.com/maps/api/place/textsearch/json"]
    (prn (->
           (client/get places-url {:accept :json :query-params params})
           :body
           (json/read-str :key-fn keyword)))))
