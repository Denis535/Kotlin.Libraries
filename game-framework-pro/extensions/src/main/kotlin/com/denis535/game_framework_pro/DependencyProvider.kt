package com.denis535.game_framework_pro

import kotlin.reflect.KClass

public interface DependencyProvider {
    public companion object {

        internal var Instance: DependencyProvider? = null
            get() {
                return field
            }
            set(value) {
                if (value != null) {
                    check(field == null)
                    field = value
                } else {
                    check(field != null)
                    field = null
                }
            }
    }

    fun GetDependency(clazz: KClass<*>, argument: Any? = null): Any?

}

fun <T : Any> DependencyProvider.GetDependency(clazz: KClass<*>, argument: Any? = null): T? {
    return this.GetDependency(clazz, argument) as T?
}

fun <T : Any> DependencyProvider.RequireDependency(clazz: KClass<*>, argument: Any? = null): T {
    return this.GetDependency(clazz, argument) as T? ?: error("Dependency $clazz (${argument ?: "Null"}) was not found")
}
