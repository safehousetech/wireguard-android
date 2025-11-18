package com.safehouse.core.vpn.databinding

/**
 * A list containing elements that can be looked up by key. A `KeyedList` cannot contain `null`
 * elements.
 */
interface KeyedList<K, E : Keyed<out K>> : List<E> {
  fun containsAllKeys(keys: Collection<K>): Boolean

  fun containsKey(key: K): Boolean

  operator fun get(key: K): E?

  fun getLast(key: K): E?

  fun indexOfKey(key: K): Int

  fun lastIndexOfKey(key: K): Int
}
