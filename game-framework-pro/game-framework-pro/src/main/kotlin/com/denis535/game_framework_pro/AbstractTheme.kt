package com.denis535.game_framework_pro

import com.denis535.state_machine_pro.*

public abstract class AbstractTheme : AbstractCloseable {

    protected val Machine: StateMachine<AbstractTheme, AbstractPlayList>
        get() {
            assert(!this.IsClosed)
            return field
        }

    public constructor() {
        this.Machine = StateMachine<AbstractTheme, AbstractPlayList>(this)
    }

    public override fun OnClose() {
        this.Machine.close()
    }

}

public abstract class AbstractPlayList {

    public val IsClosing: Boolean
        get() {
            return this.StateMutable.IsClosing
        }
    public val IsClosed: Boolean
        get() {
            return this.StateMutable.IsClosed
        }

    protected val Theme: AbstractTheme?
        get() {
            assert(!this.IsClosed)
            return this.StateMutable.Machine?.UserData
        }
    public val State: AbstractState<AbstractTheme, AbstractPlayList>
        get() {
            assert(!this.IsClosed)
            return this.StateMutable
        }
    protected val StateMutable: State<AbstractTheme, AbstractPlayList>
        get() {
            assert(!this.IsClosed)
            return field
        }

    public constructor() {
        this.StateMutable = State<AbstractTheme, AbstractPlayList>(this).apply {
            this.OnCloseCallback = this@AbstractPlayList::OnClose
            this.OnActivateCallback = this@AbstractPlayList::OnActivate
            this.OnDeactivateCallback = this@AbstractPlayList::OnDeactivate
        }
    }

    protected abstract fun OnClose()

    protected abstract fun OnActivate(argument: Any?)
    protected abstract fun OnDeactivate(argument: Any?)

}
