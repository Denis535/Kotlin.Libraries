package com.denis535.state_machine_pro

public interface AbstractState<TMachineUserData, TStateUserData> {

    public val IsClosing: Boolean
    public val IsClosed: Boolean

    public val Owner: Any?
    public val Machine: AbstractStateMachine<TMachineUserData, TStateUserData>?

    public val IsRoot: Boolean
    public val Root: AbstractState<TMachineUserData, TStateUserData>

    public val Parent: AbstractState<TMachineUserData, TStateUserData>?
    public val Ancestors: Sequence<AbstractState<TMachineUserData, TStateUserData>>
    public val AncestorsAndSelf: Sequence<AbstractState<TMachineUserData, TStateUserData>>

    public val Activity: Activity

    public val Children: Sequence<AbstractState<TMachineUserData, TStateUserData>>
    public val Descendants: Sequence<AbstractState<TMachineUserData, TStateUserData>>
    public val DescendantsAndSelf: Sequence<AbstractState<TMachineUserData, TStateUserData>>

    public val UserData: TStateUserData

}

internal interface AbstractStateInternal<TMachineUserData, TStateUserData> : AbstractState<TMachineUserData, TStateUserData>, AutoCloseable {

    public fun Attach(machine: AbstractStateMachine<TMachineUserData, TStateUserData>, argument: Any?)
    public fun Attach(parent: AbstractState<TMachineUserData, TStateUserData>, argument: Any?)

    public fun Detach(machine: AbstractStateMachine<TMachineUserData, TStateUserData>, argument: Any?)
    public fun Detach(parent: AbstractState<TMachineUserData, TStateUserData>, argument: Any?)

    public fun Activate(argument: Any?)
    public fun Deactivate(argument: Any?)

}

internal fun <TMachineUserData, TStateUserData> AbstractState<TMachineUserData, TStateUserData>.AsInternal(): AbstractStateInternal<TMachineUserData, TStateUserData> {
    return this as AbstractStateInternal<TMachineUserData, TStateUserData>
}
