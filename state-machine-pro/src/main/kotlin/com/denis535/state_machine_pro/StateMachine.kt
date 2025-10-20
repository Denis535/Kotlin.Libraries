package com.denis535.state_machine_pro

public class StateMachine<TMachineUserData, TStateUserData> : AbstractStateMachine<TMachineUserData, TStateUserData> {

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

    public var OnCloseCallback: Proc? = null
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
        assert(!this.IsClosing)
        assert(!this.IsClosed)
        this.Lifecycle = ELifecycle.Closing
        this.OnCloseCallback?.invoke()
        assert(this.Root == null || this.Root!!.IsClosed)
        this.Lifecycle = ELifecycle.Closed
    }

    public fun SetRoot(
        root: AbstractState<TMachineUserData, TStateUserData>?, argument: Any?, callback: Proc2<AbstractState<TMachineUserData, TStateUserData>, Any?>? = null
    ) {
        assert(!this.IsClosed)
        if (this.Root != null) {
            this.RemoveRoot(this.Root!!, argument, callback)
        }
        if (root != null) {
            this.AddRoot(root, argument)
        }
    }

    private fun AddRoot(
        root: AbstractState<TMachineUserData, TStateUserData>, argument: Any?
    ) {
        assert(!this.IsClosed)
        assert(this.Root == null)
        this.Root = root
        this.Root!!.Attach(this, argument)
    }

    private fun RemoveRoot(
        root: AbstractState<TMachineUserData, TStateUserData>, argument: Any?, callback: Proc2<AbstractState<TMachineUserData, TStateUserData>, Any?>? = null
    ) {
        assert(!this.IsClosed)
        assert(this.Root == root)
        this.Root!!.Detach(this, argument)
        this.Root = null
        if (callback != null) {
            callback.invoke(root, argument)
        } else {
            root.close()
        }
    }

}
