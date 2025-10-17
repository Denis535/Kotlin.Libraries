package com.denis535.state_machine_pro

public interface IStateMachine<TMachineUserData, TStateUserData> {

    public val IsDisposing: Boolean
    public val IsDisposed: Boolean

    public val Root: IState<TMachineUserData, TStateUserData>?

    public val UserData: TMachineUserData

    public fun Dispose()

}