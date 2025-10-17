package com.denis535.state_machine_pro

public interface AbstractStateMachine<TMachineUserData, TStateUserData> {

    public val IsDisposing: Boolean
    public val IsDisposed: Boolean

    public val Root: AbstractState<TMachineUserData, TStateUserData>?

    public val UserData: TMachineUserData

}

internal interface AbstractStateMachineMutable<TMachineUserData, TStateUserData> : AbstractStateMachine<TMachineUserData, TStateUserData> {

    public fun Dispose()

}
