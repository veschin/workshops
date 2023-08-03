(ns state)

(def state (atom {:users []}))
(def tid (atom 0))

(add-watch state :transaction (fn [_k _r _o _n] (swap! tid inc)))

(doseq [i (range 100)]
  (future (swap! state update :users conj i)))
