package com.denis535.game_framework_pro

internal typealias ELifecycle = Lifecycle

public abstract class AbstractCloseable : AutoCloseable {

    private var Lifecycle = ELifecycle.Alive

    public val IsClosing: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closing
        }
    public val IsClosed: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closed
        }

    public constructor()

    public final override fun close() {
        assert(!this.IsClosing)
        assert(!this.IsClosed)
        this.Lifecycle = ELifecycle.Closing
        this.OnClose()
        this.Lifecycle = ELifecycle.Closed
    }

    public abstract fun OnClose()

}

internal enum class Lifecycle {
    Alive, Closing, Closed,
}
