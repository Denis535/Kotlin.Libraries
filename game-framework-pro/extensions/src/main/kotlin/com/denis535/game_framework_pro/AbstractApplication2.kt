package com.denis535.game_framework_pro

public abstract class AbstractApplication2 : AbstractApplication {

    protected val Provider: DependencyProvider
        get() {
            check(!this.IsClosed)
            return field
        }

    public constructor(provider: DependencyProvider) {
        this.Provider = provider
    }

    protected override fun OnClose() {
        super.OnClose()
    }

}
