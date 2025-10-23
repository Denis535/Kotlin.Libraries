package com.denis535.game_framework_pro

import com.denis535.tree_machine_pro.*

public abstract class AbstractScreen : AbstractCloseable {

    protected val Machine: TreeMachine<AbstractScreen, AbstractWidget>
        get() {
            assert(!this.IsClosed)
            return field
        }

    public constructor() {
        this.Machine = TreeMachine<AbstractScreen, AbstractWidget>(this)
    }

    public override fun OnClose() {
        this.Machine.close()
    }

}
