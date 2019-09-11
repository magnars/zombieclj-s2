(ns dev
  (:require [clojure.tools.namespace.repl :as c.t.n.r]
            [figwheel.main]
            [figwheel.main.api]
            [reloaded.repl :refer [system reset stop start]]
            [zombieclj.system]))

(reloaded.repl/set-init! #'zombieclj.system/create-system)
(reloaded.repl/init)

(c.t.n.r/set-refresh-dirs "src" "test" "dev")

(defn cljs []
  (if (get @figwheel.main/build-registry "dev")
    (figwheel.main.api/cljs-repl "dev")
    (figwheel.main.api/start "dev")))


