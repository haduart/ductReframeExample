(defproject flexiana-scramble "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.439"]
                 [duct/core "0.6.2"]
                 [duct/module.logging "0.3.1"]
                 [duct/module.web "0.6.4"]
                 [duct/module.cljs "0.3.2"]
                 [ring/ring-json "0.4.0"]
                 [reagent "0.8.1"]
                 [re-frame "0.10.6"]
                 [cljs-ajax "0.8.0"]]

  :plugins [[duct/lein-duct "0.10.6"]
            [lein-midje "3.1.3"]
            [lein-cljsbuild "1.1.7"]
            [lein-kibit "0.1.6"]
            [lein-figwheel "0.5.16"]]

  :main ^:skip-aot flexiana-scramble.main
  :resource-paths ["resources" "target/resources"]
  :prep-tasks ["javac" "compile" ["run" ":duct/compiler"]]

  :profiles {:dev          [:project/dev :profiles/dev]
             :repl         {:prep-tasks   ^:replace ["javac" "compile"]
                            :repl-options {:init-ns          user
                                           :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}
             :uberjar      {:aot :all}
             :profiles/dev {}
             :project/dev  {:source-paths   ["dev/src"]
                            :resource-paths ["dev/resources"]
                            :dependencies   [[integrant/repl "0.2.0"]
                                             [eftest "0.4.1"]
                                             [kerodon "0.9.0"]
                                             [midje "1.9.4"]
                                             [ring/ring-mock "0.3.2"]
                                             [figwheel "0.5.16"]
                                             [figwheel-sidecar "0.5.16"]
                                             [re-frisk "0.5.4"]
                                             [binaryage/devtools "0.9.10"]]}}

  :cljsbuild {
              :builds [{:id           "dev"
                        :figwheel     true
                        :source-paths ["src" "dev/src"]
                        :compiler     {:main                 dev.client
                                       :output-to            "resources/flexiana_scramble/public/js/main.js"
                                       :output-dir           "resources/flexiana_scramble/public/js/out"
                                       :asset-path           "js/out"
                                       :optimizations        :none
                                       :source-map-timestamp true
                                       :preloads             [devtools.preload]
                                       :external-config      {:devtools/config {:features-to-install :all}}}}

                       {:id           "min"
                        :source-paths ["src"]
                        :compiler     {:main            flexiana-scramble.client.main
                                       :output-to       "resources/flexiana_scramble/public/js/main.js"
                                       :output-dir      "resources/flexiana_scramble/public/js/out-min"
                                       :optimizations   :advanced
                                       :closure-defines {goog.DEBUG false}
                                       :pretty-print    false
                                       :parallel-build  true}
                        :warning-handlers
                                      [(fn [warning-type env extra]
                                         (when (warning-type cljs.analyzer/*cljs-warnings*)
                                           (when-let [s (cljs.analyzer/error-message warning-type extra)]
                                             (binding [*out* *err*]
                                               (println "WARNING:" (cljs.analyzer/message env s)))
                                             (System/exit 1))))]
                        }]}
  :aliases {"min-app" ["do" "clean," "cljsbuild" "once" "min"]})
