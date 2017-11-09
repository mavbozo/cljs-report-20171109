(ns bar.core
  (:require
   [goog.net.cookies :as gcookies]
   [cljs.loader :as loader]))

(enable-console-print!)

(defn cookie-enabled?
  []
  (.isEnabled goog.net.cookies))

(println "I'm bar!")

(defn woz []
  (println "WOZ!")
  (println "cookie enabled?" (cookie-enabled?)))

(loader/set-loaded! :bar)
