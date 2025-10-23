package com.denis535.game_framework_pro

import kotlin.reflect.KClass

public abstract class AbstractProgram2<TTheme, TScreen, TRouter, TApplication> : AbstractProgram, DependencyProvider where TTheme : AbstractTheme, TScreen : AbstractScreen, TRouter : AbstractRouter, TApplication : AbstractApplication {

    protected var Theme: TTheme
        get() {
            assert(!this.IsClosed)
            return field
        }
    protected var Screen: TScreen
        get() {
            assert(!this.IsClosed)
            return field
        }
    protected var Router: TRouter
        get() {
            assert(!this.IsClosed)
            return field
        }
    protected var Application: TApplication
        get() {
            assert(!this.IsClosed)
            return field
        }

    public constructor (theme: TTheme, screen: TScreen, router: TRouter, application: TApplication) {
        this.Theme = theme
        this.Screen = screen
        this.Router = router
        this.Application = application
    }

    public override fun OnClose() {
        super.OnClose()
    }

    public override fun GetDependency(clazz: KClass<*>, argument: Any?): Any? {
        assert(!this.IsClosed)
        return when(clazz) {
            this.Theme::class -> this.Theme
            this.Screen::class -> this.Screen
            this.Router::class -> this.Router
            this.Application::class -> this.Application
            else -> null
        }
    }

}
