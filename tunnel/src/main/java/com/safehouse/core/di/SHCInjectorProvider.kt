package com.safehouse.core.di

import android.app.Service
import android.content.ContentProvider
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.preference.Preference

val ContentProvider.injector
  get() = (context?.applicationContext as InjectorProvider).component
val FragmentActivity.injector
  get() = (application as InjectorProvider).component
val Fragment.injector
  get() = (requireContext().applicationContext as InjectorProvider).component
val Preference.injector
  get() = (context.applicationContext as InjectorProvider).component
val Service.injector
  get() = (applicationContext as InjectorProvider).component

interface InjectorProvider {
  val component: SafehouseCoreComponent
}

fun getInjector(context: Context) = (context.applicationContext as InjectorProvider).component
