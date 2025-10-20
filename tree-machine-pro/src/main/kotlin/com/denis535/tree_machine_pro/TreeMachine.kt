package com.denis535.tree_machine_pro

public class TreeMachine<TMachineUserData, TNodeUserData> : AbstractTreeMachine<TMachineUserData, TNodeUserData> {

    private var Lifecycle = ELifecycle.Alive

    public override val IsClosing: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closing
        }
    public override val IsClosed: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closed
        }

    public override var Root: AbstractNode<TMachineUserData, TNodeUserData>? = null
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
        assert(!this.IsClosed)
        this.Lifecycle = ELifecycle.Closing
        this.OnCloseCallback?.invoke()
        assert(this.Root == null || this.Root!!.IsClosed)
        this.Lifecycle = ELifecycle.Closed
    }

    public fun SetRoot(
        root: AbstractNode<TMachineUserData, TNodeUserData>?, argument: Any?, callback: Proc2<AbstractNode<TMachineUserData, TNodeUserData>, Any?>? = null
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
        root: AbstractNode<TMachineUserData, TNodeUserData>, argument: Any?
    ) {
        assert(!this.IsClosed)
        assert(this.Root == null)
        this.Root = root
        this.Root!!.Attach(this, argument)
    }

    private fun RemoveRoot(
        root: AbstractNode<TMachineUserData, TNodeUserData>, argument: Any?, callback: Proc2<AbstractNode<TMachineUserData, TNodeUserData>, Any?>? = null
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
