package com.safehouse.core.vpn.databinding



/** A list that is both sorted/keyed and observable. */
interface ObservableSortedKeyedList<K, E : Keyed<out K>> :
  ObservableKeyedList<K, E>, SortedKeyedList<K, E>
