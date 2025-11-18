package com.safehouse.core.vpn.di

import com.safehouse.core.vpn.di.WireguardComponent
import dagger.Module

@Module(subcomponents = [WireguardComponent::class]) class WireguardModule
