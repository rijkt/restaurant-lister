(ns core
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))

(defn make-step [access-token]
  (fn [next-token]
    (let [params {"query" "restaurant" "key" access-token "pageToken" next-token}
          places-url "https://maps.googleapis.com/maps/api/place/textsearch/json"]
      (->
        (client/get places-url {:accept :json :query-params params})
        :body
        (json/read-str :key-fn keyword)))))

(defn -main [& args]
  (let [access-token (first args)]
    (prn (take 21 (flatten (lazy-seq (iteration (make-step access-token) {:vf :results :kf :next_page_token})))))))
