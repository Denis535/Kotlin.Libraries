package com.denis535.game_framework_pro

public abstract class AbstractScreen2<TRouter, TApplication> : AbstractScreen where TRouter : AbstractRouter, TApplication : AbstractApplication {

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
        this.Router = provider.RequireDependency<TRouter>(AbstractRouter::class)
        this.Application = provider.RequireDependency<TApplication>(AbstractApplication::class)
    }

    public override fun OnClose() {
        super.OnClose()
    }

}

public abstract class AbstractWidget2 : AbstractWidget {

    protected val Provider: DependencyProvider
        get() {
            assert(!this.IsClosed)
            return field
        }

    public constructor (provider: DependencyProvider) {
        this.Provider = provider
    }

}

public abstract class AbstractViewableWidget2 : AbstractViewableWidget {

    protected val Provider: DependencyProvider
        get() {
            assert(!this.IsClosed)
            return field
        }

    public constructor (view: Any, provider: DependencyProvider) : super(view) {
        this.Provider = provider
    }

}
