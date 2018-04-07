(defproject measure "0.1.0-SNAPSHOT"
  :description "TODO"
  :url "https://github.com/andnils/measure"
  :license {:name "TODO: Choose a license"
            :url "http://choosealicense.com/"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/java.jdbc "0.7.5"]
                 [org.clojure/tools.logging "0.4.0"]
                 [ch.qos.logback/logback-classic "1.2.3"]
                 [org.postgresql/postgresql "42.2.2"]
                 [com.stuartsierra/component "0.3.2"]
                 [com.mchange/c3p0 "0.9.5.2"]
                 [ring/ring "1.6.3"]
                 [ring/ring-json "0.4.0"]
                 [compojure "1.6.0"]
                 [cheshire "5.8.0"]
                 [aero "1.1.3"]
                 [hikari-cp "2.2.0"]
                 [ragtime "0.7.2"]]
  :main measure.system
  :source-paths ["src/main/clj"]
  :test-paths ["src/test/clj"]
  :resource-paths ["src/main/resources" "config"]
  :target-path "target/%s/"
  :repl-options {:init-ns user}
  :plugins [[lein-pprint "1.2.0"]]
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [com.stuartsierra/component.repl "0.2.0"]
                                  [com.h2database/h2 "1.0.60"]
                                  [restpect "0.2.1"]]
                   :source-paths ["dev"]}})
