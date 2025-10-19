package com.denis535.state_machine_pro

public class ChildrenableState<TMachineUserData, TStateUserData> : AbstractState<TMachineUserData, TStateUserData> {

    private var Lifecycle = ELifecycle.Alive

    public override val IsClosing: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closing
        }
    public override val IsClosed: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closed
        }

    public override var Owner: Any? = null
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

    public override val Machine: AbstractStateMachine<TMachineUserData, TStateUserData>?
        get() {
            assert(!this.IsClosed)
            return (this.Owner as? AbstractStateMachine<TMachineUserData, TStateUserData>) ?: (this.Owner as? AbstractState<TMachineUserData, TStateUserData>)?.Machine
        }

    public override val IsRoot: Boolean
        get() {
            assert(!this.IsClosed)
            return this.Parent == null
        }
    public override val Root: AbstractState<TMachineUserData, TStateUserData>
        get() {
            assert(!this.IsClosed)
            return this.Parent?.Root ?: this
        }

    public override val Parent: AbstractState<TMachineUserData, TStateUserData>?
        get() {
            assert(!this.IsClosed)
            return this.Owner as? AbstractState<TMachineUserData, TStateUserData>
        }
    public override val Ancestors: Sequence<AbstractState<TMachineUserData, TStateUserData>>
        get() {
            assert(!this.IsClosed)
            return sequence {
                if (this@ChildrenableState.Parent != null) {
                    this.yield(this@ChildrenableState.Parent!!)
                    this.yieldAll(this@ChildrenableState.Parent!!.Ancestors)
                }
            }
        }
    public override val AncestorsAndSelf: Sequence<AbstractState<TMachineUserData, TStateUserData>>
        get() {
            assert(!this.IsClosed)
            return sequence {
                this.yield(this@ChildrenableState)
                this.yieldAll(this@ChildrenableState.Ancestors)
            }
        }

    public override var Activity: EActivity = EActivity.Inactive
        get() {
            assert(!this.IsClosed)
            return field
        }
        private set(value) {
            assert(!this.IsClosed)
            assert(this.Owner != null)
            assert(field != value)
            field = value
        }

    public val Children: List<AbstractState<TMachineUserData, TStateUserData>>
        get() {
            assert(!this.IsClosed)
            return this.ChildrenMutable
        }
    private val ChildrenMutable: MutableList<AbstractState<TMachineUserData, TStateUserData>> = mutableListOf<AbstractState<TMachineUserData, TStateUserData>>()
        get() {
            assert(!this.IsClosed)
            return field
        }

    public var SortDelegate: Proc1<MutableList<AbstractState<TMachineUserData, TStateUserData>>>? = null
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

    public override val UserData: TStateUserData
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

    public var OnAttachCallback: Proc1<Any?>? = null
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
    public var OnDetachCallback: Proc1<Any?>? = null
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

    public var OnActivateCallback: Proc1<Any?>? = null
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
    public var OnDeactivateCallback: Proc1<Any?>? = null
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

    public constructor(userData: TStateUserData) {
        this.UserData = userData
    }

    public override fun close() {
        assert(!this.IsClosed)
        this.Owner?.let { owner ->
            when (owner) {
                is AbstractStateMachine<*, *> -> assert(owner.IsClosing)
                is AbstractState<*, *> -> assert(owner.IsClosing)
            }
        }
        this.Lifecycle = ELifecycle.Closing
        this.OnCloseCallback?.invoke()
        assert(this.Children.all { it.IsClosed })
        this.Lifecycle = ELifecycle.Closed
    }

    internal override fun Attach(machine: AbstractStateMachine<TMachineUserData, TStateUserData>, argument: Any?) {
        assert(!this.IsClosed)
        assert(this.Owner == null)
        this.Owner = machine
        this.OnAttachCallback?.invoke(argument)
        if (true) {
            this.Activate(argument)
        }
    }

    internal override fun Attach(parent: AbstractState<TMachineUserData, TStateUserData>, argument: Any?) {
        assert(!this.IsClosed)
        assert(this.Owner == null)
        this.Owner = parent
        this.OnAttachCallback?.invoke(argument)
        if (this.Parent!!.Activity == EActivity.Active) {
            this.Activate(argument)
        }
    }

    internal override fun Detach(machine: AbstractStateMachine<TMachineUserData, TStateUserData>, argument: Any?) {
        assert(!this.IsClosed)
        assert(this.Owner == machine)
        if (true) {
            this.Deactivate(argument)
        }
        this.OnDetachCallback?.invoke(argument)
        this.Owner = null
    }

    internal override fun Detach(parent: AbstractState<TMachineUserData, TStateUserData>, argument: Any?) {
        assert(!this.IsClosed)
        assert(this.Owner == parent)
        if (this.Activity == EActivity.Active) {
            this.Deactivate(argument)
        }
        this.OnDetachCallback?.invoke(argument)
        this.Owner = null
    }

    internal override fun Activate(argument: Any?) {
        this.Activity = EActivity.Activating
        this.OnActivateCallback?.invoke(argument)
        for (child in this.Children.toList()) {
            child.Activate(argument)
        }
        this.Activity = EActivity.Active
    }

    internal override fun Deactivate(argument: Any?) {
        this.Activity = EActivity.Deactivating
        for (child in this.Children.reversed()) {
            child.Deactivate(argument)
        }
        this.OnDeactivateCallback?.invoke(argument)
        this.Activity = EActivity.Inactive
    }

    public fun AddChild(child: AbstractState<TMachineUserData, TStateUserData>, argument: Any?) {
        require(!child.IsClosed)
        require(child.Owner == null)
        assert(!this.IsClosed)
        assert(!this.Children.contains(child))
        this.ChildrenMutable.add(child)
        this.SortDelegate?.invoke(this.ChildrenMutable)
        child.Attach(this, argument)
    }

    public fun AddChildren(children: Array<AbstractState<TMachineUserData, TStateUserData>>, argument: Any?) {
        assert(!this.IsClosed)
        for (child in children) {
            this.AddChild(child, argument)
        }
    }

    public fun RemoveChild(child: AbstractState<TMachineUserData, TStateUserData>, argument: Any?, callback: Proc2<AbstractState<TMachineUserData, TStateUserData>, Any?>? = null) {
        require(!child.IsClosed)
        require(child.Owner == this)
        assert(!this.IsClosed)
        assert(this.Children.contains(child))
        child.Detach(this, argument)
        this.ChildrenMutable.remove(child)
        if (callback != null) {
            callback.invoke(child, argument)
        } else {
            child.close()
        }
    }

    public fun RemoveChildren(predicate: Predicate1<AbstractState<TMachineUserData, TStateUserData>>, argument: Any?, callback: Proc2<AbstractState<TMachineUserData, TStateUserData>, Any?>? = null): Int {
        assert(!this.IsClosed)
        var count = 0
        for (child in this.Children.reversed().filter(predicate)) {
            this.RemoveChild(child, argument, callback)
            count++
        }
        return count
    }

}
