package com.denis535.state_machine_pro

import com.denis535.state_machine_pro.Lifecycle as ELifecycle

public class StateMachine<TMachineUserData, TStateUserData> : AbstractStateMachineMutable<TMachineUserData, TStateUserData> {

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
            assert(!this.IsClosed)
            return field
        }
        private set(value) {
            assert(!this.IsClosed)
            if (value != null) {
                assert(field == null)
            } else {
                assert(field != null)
            }
            field = value
        }

    public override val UserData: TMachineUserData
        get() {
            assert(!this.IsClosed)
            return field
        }

    public var OnCloseCallback: (() -> Unit)? = null
        get() {
            assert(!this.IsClosed)
            return field
        }
        set(value) {
            assert(!this.IsClosed)
            if (value != null) {
                assert(field == null)
            } else {
                assert(field != null)
            }
            field = value
        }

    public constructor(userData: TMachineUserData) {
        this.UserData = userData
    }

    public override fun close() {
        assert(!this.IsClosed)
        this.Lifecycle = ELifecycle.Closing
        this.OnCloseCallback?.invoke()
        this.Root?.let {
            assert(it.IsClosed)
        }
        this.Lifecycle = ELifecycle.Closed
    }

    public fun SetRoot(
        root: AbstractState<TMachineUserData, TStateUserData>?, argument: Any?, callback: ((AbstractState<TMachineUserData, TStateUserData>, Any?) -> Unit)? = null
    ) {
        assert(!this.IsClosed)
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
        require(!root.IsClosed)
        require(root.Owner == null)
        assert(!this.IsClosed)
        assert(this.Root == null)
        root.AsMutable().let {
            this.Root = it
            it.Attach(this, argument)
        }
    }

    private fun RemoveRoot(
        root: AbstractState<TMachineUserData, TStateUserData>, argument: Any?, callback: ((AbstractState<TMachineUserData, TStateUserData>, Any?) -> Unit)? = null
    ) {
        require(!root.IsClosed)
        require(root.Owner == this)
        assert(!this.IsClosed)
        assert(this.Root == root)
        root.AsMutable().let {
            it.Detach(this, argument)
            this.Root = null
            if (callback != null) {
                callback.invoke(it, argument)
            } else {
                it.close()
            }
        }
    }

}
