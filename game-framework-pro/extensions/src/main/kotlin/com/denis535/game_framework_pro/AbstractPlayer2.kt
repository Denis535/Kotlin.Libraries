package com.denis535.game_framework_pro

public abstract class AbstractPlayer2 : AbstractPlayer {

    protected val Provider: DependencyProvider
        get() {
            return DependencyProvider.Instance!!
        }

    public constructor() {
    }

    protected override fun OnClose() {
        super.OnClose()
    }

}
