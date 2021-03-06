## Instruction to Reproduce the Possible Bug
	0. clone this repo
	1. use latest JDK 8
	2. download cljs.jar from clojurescript 1.9.946 https://github.com/clojure/clojurescript/releases/tag/r1.9.946
	3. execute this on repo directory
	   `java -cp cljs.jar:src clojure.main repl.clj`
	4. Open Chrome browser on http://localhost:9000 and open the inspector tool
	5. Click the `Load Bar!` button
	6. See the error on console log
	
## Description	

Fails to load required google closure's namespace in the loaded module.

The codes here are taken from the guide https://clojurescript.org/guides/code-splitting and modified to reproduce the bugs.

basically, I modified the `src/bar/core.cljs` from above guide like this:

```clojure
(ns bar.core
  (:require 
  [goog.net.cookies :as gcookies] ;; I require goog.net.cookies
  [cljs.loader :as loader]))

(enable-console-print!)

(defn cookie-enabled? ;; I create wrapper function
  []
  (.isEnabled goog.net.cookies))

(println "I'm bar!")

(defn woz []
  (println "WOZ!")
  (println "cookie enabled?" (cookie-enabled?))) ;; call cookie-enabled?

(loader/set-loaded! :bar)
```

The result in chrome's console when I click the `Load Bar!` button in the browser are: 

```
Uncaught TypeError: Cannot read property 'isEnabled' of undefined
    at bar$core$cookie_enabled_QMARK_ (eval at goog.globalEval (base.js:2184), <anonymous>:8:24)
    at bar$core$woz (eval at goog.globalEval (base.js:2184), <anonymous>:14:85)
    at cljs.core.Var.G__9692__1 (core.cljs:1123)
    at cljs.core.Var.G__9692 [as call] (core.cljs:1164)
    at core.cljs:17
    at goog.module.ModuleLoadCallback.execute (moduleloadcallback.js:60)
    at goog.module.ModuleInfo.callCallbacks_ (moduleinfo.js:324)
    at goog.module.ModuleInfo.onLoad (moduleinfo.js:271)
    at goog.module.ModuleManager.setLoaded (modulemanager.js:831)
    at cljs$loader$set_loaded_BANG_ (loader.cljs:80)
```

but if I click the `Load Bar!` button again, everything works and the chrome's console prints:

```
WOZ!
cookie enabled? true
```
