package com.denis535.state_machine_pro

import com.denis535.state_machine_pro.Lifecycle as ELifecycle

public class StateMachine<TMachineUserData, TStateUserData> : AbstractStateMachineInternal<TMachineUserData, TStateUserData> {

    private var Lifecycle = ELifecycle.Alive

    public override val IsClosing: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closing
        }
    public override val IsClosed: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closed
        }

    public override var Root: AbstractState<TMachineUserData, TStateUserData>? = null
        get() {
            assert(!this.IsClosed) { "StateMachine $this must be non-closed" }
            return field
        }
        private set(value) {
            assert(!this.IsClosed) { "StateMachine $this must be non-closed" }
            if (field != null) {
                require(value == null) { "Argument 'value' ($value) must be null" }
            } else {
                require(value != null) { "Argument 'value' must be non-null" }
            }
            field = value
        }

    public override val UserData: TMachineUserData
        get() {
            assert(!this.IsClosed) { "StateMachine $this must be non-closed" }
            return field
        }

    public var OnCloseCallback: (() -> Unit)? = null
        get() {
            assert(!this.IsClosed) { "StateMachine $this must be non-closed" }
            return field
        }
        set(value) {
            assert(!this.IsClosed) { "StateMachine $this must be non-closed" }
            if (field != null) {
                require(value == null) { "Argument 'value' ($value) must be null" }
            } else {
                require(value != null) { "Argument 'value' must be non-null" }
            }
            field = value
        }

    public constructor(userData: TMachineUserData) {
        this.UserData = userData
    }

    public override fun close() {
        assert(!this.IsClosed) { "StateMachine $this must be non-closed" }
        this.Lifecycle = ELifecycle.Closing
        this.OnCloseCallback?.invoke()
        this.Root?.let {
            assert(it.IsClosed) { "StateMachine $this must have no $it root" }
        }
        this.Lifecycle = ELifecycle.Closed
    }

    public fun SetRoot(
        root: AbstractState<TMachineUserData, TStateUserData>?, argument: Any?, callback: ((AbstractState<TMachineUserData, TStateUserData>, Any?) -> Unit)? = null
    ) {
        assert(!this.IsClosed) { "StateMachine $this must be non-closed" }
        this.Root?.let {
            this.RemoveRoot(it, argument, callback)
        }
        root?.let {
            this.AddRoot(it, argument)
        }
    }

    private fun AddRoot(
        root: AbstractState<TMachineUserData, TStateUserData>, argument: Any?
    ) {
        require(!root.IsClosed) { "Argument 'root' ($root) must be non-closed" }
        require(root.Owner == null) { "Argument 'root' ($root) must have no ${root.Owner} owner" }
        assert(!this.IsClosed) { "StateMachine $this must be non-closed" }
        assert(this.Root == null) { "StateMachine $this must have no ${this.Root} root" }
        root.AsInternal().let {
            this.Root = it
            it.Attach(this, argument)
        }
    }

    private fun RemoveRoot(
        root: AbstractState<TMachineUserData, TStateUserData>, argument: Any?, callback: ((AbstractState<TMachineUserData, TStateUserData>, Any?) -> Unit)? = null
    ) {
        require(!root.IsClosed) { "Argument 'root' ($root) must be non-closed" }
        require(root.Owner == this) { "Argument 'root' ($root) must have $this owner" }
        assert(!this.IsClosed) { "StateMachine $this must be non-closed" }
        assert(this.Root == root) { "StateMachine $this must have $root root" }
        root.AsInternal().let {
            it.Detach(this, argument)
            this.Root = null
            if (callback != null) {
                callback.invoke(it, argument)
            } else {
                it.close()
            }
        }
    }

    public override fun toString(): String {
        return if (this.UserData != null) {
            "StateMachine: " + this.UserData.toString()
        } else {
            "StateMachine"
        }
    }

}
