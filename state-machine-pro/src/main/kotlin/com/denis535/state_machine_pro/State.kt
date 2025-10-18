package com.denis535.state_machine_pro

import com.denis535.state_machine_pro.Lifecycle as ELifecycle

public class State<TMachineUserData, TStateUserData> : AbstractStateInternal<TMachineUserData, TStateUserData> {

    private var Lifecycle = ELifecycle.Alive

    public override val IsClosing: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closing
        }
    public override val IsClosed: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closed
        }

    public constructor() {
    }

    public override fun close() {
    }

}
