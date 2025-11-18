package com.safehouse.core.vpn.databinding

import androidx.databinding.ObservableList

/** A list that is both keyed and observable. */
interface ObservableKeyedList<K, E : Keyed<out K>> : KeyedList<K, E>, ObservableList<E>
