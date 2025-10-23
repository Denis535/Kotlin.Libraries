package com.denis535.game_framework_pro

import kotlin.reflect.KClass

public interface DependencyProvider {

    fun GetDependency(clazz: KClass<*>, argument: Any? = null): Any?

}

//inline fun <reified T : Any> DependencyProvider.RequireDependency(argument: Any? = null): T {
//    return this.GetDependency(T::class, argument) as T? ?: error("Dependency ${T::class} (${argument ?: "Null"}) was not found")
//}
//
//inline fun <reified T : Any> DependencyProvider.GetDependency(argument: Any? = null): T? {
//    return this.GetDependency(T::class, argument) as T?
//}

fun <T : Any> DependencyProvider.RequireDependency(clazz: KClass<*>, argument: Any? = null): T {
    return this.GetDependency(clazz, argument) as T? ?: error("Dependency ${clazz} (${argument ?: "Null"}) was not found")
}

fun <T : Any> DependencyProvider.GetDependency(clazz: KClass<*>, argument: Any? = null): T? {
    return this.GetDependency(clazz, argument) as T?
}
