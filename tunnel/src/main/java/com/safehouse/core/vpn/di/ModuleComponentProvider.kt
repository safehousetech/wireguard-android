package com.safehouse.core.vpn.di

interface ModuleComponentProvider<T> {
  fun getAndroidModuleComponent(): T
}
