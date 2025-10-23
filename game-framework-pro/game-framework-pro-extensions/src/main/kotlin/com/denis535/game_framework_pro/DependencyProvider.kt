package com.denis535.game_framework_pro

import kotlin.reflect.KClass

public interface DependencyProvider {

    public fun RequireDependency(clazz: KClass<*>, argument: Any? = null): Any {
        return this.GetDependency(clazz, argument) ?: error("Dependency $clazz (${argument ?: "Null"}) was not found")
    }

    fun GetDependency(clazz: KClass<*>, argument: Any? = null): Any?

}

inline fun <reified T : Any> DependencyProvider.RequireDependency(argument: Any? = null): T {
    return this.RequireDependency(T::class, argument) as T
}

inline fun <reified T : Any> DependencyProvider.GetDependency(argument: Any? = null): T {
    return this.GetDependency(T::class, argument) as T
}
