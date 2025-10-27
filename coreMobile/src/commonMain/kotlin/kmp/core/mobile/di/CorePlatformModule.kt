package kmp.core.mobile.di

import com.arkivanov.decompose.ComponentContext
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

expect fun corePlatformModule(): Module

expect inline fun <reified T : ComponentContext> Module.commonViewModel(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T>