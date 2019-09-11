(defproject zombieclj "0.1.0-SNAPSHOT"
  :description "A series of zombie-themed games written with Clojure and ClojureScript."
  :url "http://www.zombieclj.no"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main zombieclj.system
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.520" :exclusions [com.cognitect/transit-clj]]
                 [com.cognitect/transit-clj "0.8.313"]
                 [http-kit "2.3.0"]
                 [com.stuartsierra/component "0.4.0"]
                 [compojure "1.6.1"]
                 [cjohansen/dumdom "2019.09.05-1"]
                 [jarohen/chord "0.8.1" :exclusions [com.cognitect/transit-cljs]]
                 [com.cognitect/transit-cljs "0.8.256"]
                 [org.clojure/core.async "0.4.500"]
                 [org.clojure/core.match "0.3.0"]]
  :profiles {:dev {:dependencies [[reloaded.repl "0.2.4"]
                                  [com.bhauman/figwheel-main "0.2.3"]
                                  [com.bhauman/rebel-readline-cljs "0.1.4"]
                                  [cider/piggieback "0.4.1"]
                                  [lambdaisland/kaocha "0.0-529"]
                                  [kaocha-noyoda "2019-06-03"]]
                   :source-paths ["dev"]
                   :resource-paths ["cljs-out"]
                   :clean-targets ^{:protect false} ["cljs-out"]
                   :repl-options {:init-ns dev}}}
  :aliases {"kaocha" ["run" "-m" "kaocha.runner"]
            "fig" ["trampoline" "run" "-m" "figwheel.main"]})
