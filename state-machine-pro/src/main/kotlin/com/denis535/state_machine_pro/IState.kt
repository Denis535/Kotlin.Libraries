package com.denis535.state_machine_pro

public interface IState<TMachineUserData, TStateUserData> {

    public val IsDisposing: Boolean
    public val IsDisposed: Boolean

    public val Owner: Any?
    public val Machine: IStateMachine<TMachineUserData, TStateUserData>?

    public val IsRoot: Boolean
    public val Root: IState<TMachineUserData, TStateUserData>

    public val Parent: IState<TMachineUserData, TStateUserData>?
    public val Ancestors: Sequence<IState<TMachineUserData, TStateUserData>>
    public val AncestorsAndSelf: Sequence<IState<TMachineUserData, TStateUserData>>

    public val Activity: Activity

    public val Children: Sequence<IState<TMachineUserData, TStateUserData>>
    public val Descendants: Sequence<IState<TMachineUserData, TStateUserData>>
    public val DescendantsAndSelf: Sequence<IState<TMachineUserData, TStateUserData>>

    public val UserData: TStateUserData

    public fun Dispose()

    public fun Attach(machine: IStateMachine<TMachineUserData, TStateUserData>, argument: Any?)
    public fun Attach(parent: IState<TMachineUserData, TStateUserData>, argument: Any?)

    public fun Detach(machine: IStateMachine<TMachineUserData, TStateUserData>, argument: Any?)
    public fun Detach(parent: IState<TMachineUserData, TStateUserData>, argument: Any?)

    public fun Activate(argument: Any?)

    public fun Deactivate(argument: Any?)

}