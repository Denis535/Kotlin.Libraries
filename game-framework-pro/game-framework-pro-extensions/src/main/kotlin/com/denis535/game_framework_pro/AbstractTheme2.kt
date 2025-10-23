package com.denis535.game_framework_pro

public abstract class AbstractTheme2<TRouter, TApplication> : AbstractTheme {

    protected val Provider: DependencyProvider
        get() {
            assert(!this.IsClosed)
            return field
        }
    protected val Router: TRouter
        get() {
            assert(!this.IsClosed)
            return field
        }
    protected val Application: TApplication
        get() {
            assert(!this.IsClosed)
            return field
        }

    public constructor (provider: DependencyProvider) {
        this.Provider = provider
        this.Router = provider.RequireDependency<TRouter>(TRouter::class)
        this.Application = provider.RequireDependency<TApplication>(TApplication::class)
    }

    public override fun OnClose() {
        super.OnClose()
    }

}

public abstract class AbstractPlayList2 : AbstractPlayList {

    protected val Provider: DependencyProvider
        get() {
            assert(!this.IsClosed)
            return field
        }

    public constructor (provider: DependencyProvider) {
        this.Provider = provider
    }

}
