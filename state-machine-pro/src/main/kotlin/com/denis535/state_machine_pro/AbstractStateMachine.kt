package com.denis535.state_machine_pro

public abstract class AbstractStateMachine<TMachineUserData, TStateUserData> : AutoCloseable {

    public abstract val IsClosing: Boolean
    public abstract val IsClosed: Boolean

    public abstract val Root: AbstractState<TMachineUserData, TStateUserData>?

    public abstract val UserData: TMachineUserData

    public constructor()

    public final override fun toString(): String {
        return if (this.UserData != null) {
            "StateMachine: " + this.UserData.toString()
        } else {
            "StateMachine"
        }
    }

}
