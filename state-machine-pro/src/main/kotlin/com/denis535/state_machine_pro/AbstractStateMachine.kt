package com.denis535.state_machine_pro

public interface AbstractStateMachine<TMachineUserData, TStateUserData> {

    public val IsClosing: Boolean
    public val IsClosed: Boolean

    public val Root: AbstractState<TMachineUserData, TStateUserData>?

    public val UserData: TMachineUserData

}

internal interface AbstractStateMachineInternal<TMachineUserData, TStateUserData> : AbstractStateMachine<TMachineUserData, TStateUserData>, AutoCloseable

internal fun <TMachineUserData, TStateUserData> AbstractStateMachine<TMachineUserData, TStateUserData>.AsInternal(): AbstractStateMachineInternal<TMachineUserData, TStateUserData> {
    return this as AbstractStateMachineInternal<TMachineUserData, TStateUserData>
}
