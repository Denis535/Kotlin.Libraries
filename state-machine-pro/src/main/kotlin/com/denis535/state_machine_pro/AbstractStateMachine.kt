package com.denis535.state_machine_pro

public abstract class AbstractStateMachine<TMachineUserData, TStateUserData> {

    public abstract val IsClosing: Boolean
    public abstract val IsClosed: Boolean

    public abstract val Root: AbstractState<TMachineUserData, TStateUserData>?

    public abstract val UserData: TMachineUserData

    public constructor()

    internal fun AsMutable(): AbstractStateMachineMutable<TMachineUserData, TStateUserData> {
        return this as AbstractStateMachineMutable<TMachineUserData, TStateUserData>
    }

    public final override fun toString(): String {
        return if (this.UserData != null) {
            "StateMachine: " + this.UserData.toString()
        } else {
            "StateMachine"
        }
    }

}

public abstract class AbstractStateMachineMutable<TMachineUserData, TStateUserData> : AbstractStateMachine<TMachineUserData, TStateUserData>, AutoCloseable {

    public constructor()

}
