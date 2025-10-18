package com.denis535.state_machine_pro

public abstract class AbstractState<TMachineUserData, TStateUserData> {

    public abstract val IsClosing: Boolean
    public abstract val IsClosed: Boolean

    public abstract val Owner: Any?
    public abstract val Machine: AbstractStateMachine<TMachineUserData, TStateUserData>?

    public abstract val IsRoot: Boolean
    public abstract val Root: AbstractState<TMachineUserData, TStateUserData>

    public abstract val Parent: AbstractState<TMachineUserData, TStateUserData>?
    public abstract val Ancestors: Sequence<AbstractState<TMachineUserData, TStateUserData>>
    public abstract val AncestorsAndSelf: Sequence<AbstractState<TMachineUserData, TStateUserData>>

    public abstract val Activity: Activity

    public abstract val Children: Sequence<AbstractState<TMachineUserData, TStateUserData>>
    public abstract val Descendants: Sequence<AbstractState<TMachineUserData, TStateUserData>>
    public abstract val DescendantsAndSelf: Sequence<AbstractState<TMachineUserData, TStateUserData>>

    public abstract val UserData: TStateUserData

    public constructor()

    internal fun AsMutable(): AbstractStateMutable<TMachineUserData, TStateUserData> {
        return this as AbstractStateMutable<TMachineUserData, TStateUserData>
    }

    public final override fun toString(): String {
        return if (this.UserData != null) {
            "State: " + this.UserData.toString()
        } else {
            "State"
        }
    }

}

public abstract class AbstractStateMutable<TMachineUserData, TStateUserData> : AbstractState<TMachineUserData, TStateUserData>, AutoCloseable {

    internal abstract fun Attach(machine: AbstractStateMachine<TMachineUserData, TStateUserData>, argument: Any?)
    internal abstract fun Attach(parent: AbstractState<TMachineUserData, TStateUserData>, argument: Any?)

    internal abstract fun Detach(machine: AbstractStateMachine<TMachineUserData, TStateUserData>, argument: Any?)
    internal abstract fun Detach(parent: AbstractState<TMachineUserData, TStateUserData>, argument: Any?)

    internal abstract fun Activate(argument: Any?)
    internal abstract fun Deactivate(argument: Any?)

    public constructor()

}
