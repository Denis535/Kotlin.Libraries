package com.denis535.game_framework_pro

public abstract class AbstractRouter2<TTheme, TScreen, TApplication> : AbstractRouter where TTheme : AbstractTheme, TScreen : AbstractScreen, TApplication : AbstractApplication {

    protected val Provider: DependencyProvider
        get() {
            check(!this.IsClosed)
            return field
        }
    protected val Theme: TTheme
        get() {
            check(!this.IsClosed)
            return this.Provider.RequireDependency(AbstractTheme::class)
        }
    protected val Screen: TScreen
        get() {
            check(!this.IsClosed)
            return this.Provider.RequireDependency(AbstractScreen::class)
        }
    protected val Application: TApplication
        get() {
            check(!this.IsClosed)
            return field
        }

    public constructor(provider: DependencyProvider) {
        this.Provider = provider
        this.Application = provider.RequireDependency<TApplication>(AbstractApplication::class)
    }

    protected override fun OnClose() {
        super.OnClose()
    }

}
