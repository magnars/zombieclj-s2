(ns zombieclj.event-bus)

(defonce watchers (atom []))

(defn reset []
  (reset! watchers []))

(defn publish [topic & args]
  (doseq [watcher @watchers]
    (cond
      (= topic (:topic watcher))
      (apply (:handler watcher) args)

      (nil? (:topic watcher))
      (apply (:handler watcher) topic args))))

(defn remove-watcher [watchers watcher]
  (->> watchers
       (remove (fn [w] (= (select-keys w [:topic :name])
                          (select-keys watcher [:topic :name]))))
       vec))

(defn add-watcher [watchers watcher]
  (-> watchers
      (remove-watcher watcher)
      (conj watcher)))

(defn watch [name topic handler]
  (swap! watchers add-watcher
         {:name name :topic topic :handler handler}))

(defn unwatch [name topic]
  (swap! watchers remove-watcher
         {:name name :topic topic}))

(defn watch-all [name handler]
  (swap! watchers add-watcher
         {:name name :handler handler}))

(defn publish-actions [actions]
  (doseq [[topic & args] (remove nil? actions)]
    (apply publish topic args)))

(defn action-fn [actions]
  (when (seq actions)
    (fn [e]
      (.preventDefault e)
      (publish-actions actions))))
