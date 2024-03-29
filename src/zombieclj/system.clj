(ns zombieclj.system
  (:require [com.stuartsierra.component :as component]
            [org.httpkit.server :refer [run-server]]
            [zombieclj.web :refer [app]]))

(defn- start-server [handler port]
  (let [server (run-server handler {:port port})]
    (println (str "Started server on localhost:" port))
    server))

(defn- stop-server [server]
  (when server
    (server))) ;; run-server returns a fn that stops itself

(defrecord ZombieCLJ []
  component/Lifecycle
  (start [this]
    (assoc this :server (start-server #'app 8666)))
  (stop [this]
    (stop-server (:server this))
    (assoc this :server nil)))

(defn create-system []
  (ZombieCLJ.))

(defn -main [& args]
  (.start (create-system)))
