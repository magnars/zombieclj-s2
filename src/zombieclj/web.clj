(ns zombieclj.web
  (:require [chord.http-kit :refer [with-channel]]
            [clojure.java.io :as io]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [resources]]
            [zombieclj.game-loop :as game-loop]))

(defn- ws-handler [req]
  (with-channel req ws-channel
    (game-loop/start! ws-channel)))

(defroutes app
  (GET "/ws" [] ws-handler)
  (GET "/" [] (io/resource "public/index.html"))
  (resources "/"))
