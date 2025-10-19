package com.denis535.state_machine_pro

import com.denis535.state_machine_pro.Lifecycle as ELifecycle
import com.denis535.state_machine_pro.Activity as EActivity

public class State<TMachineUserData, TStateUserData> : AbstractStateMutable<TMachineUserData, TStateUserData> {

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
                if (this@State.Parent != null) {
                    this.yield(this@State.Parent!!)
                    this.yieldAll(this@State.Parent!!.Ancestors)
                }
            }
        }
    public override val AncestorsAndSelf: Sequence<AbstractState<TMachineUserData, TStateUserData>>
        get() {
            assert(!this.IsClosed)
            return sequence {
                this.yield(this@State)
                this.yieldAll(this@State.Ancestors)
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

    public override val Children: Sequence<AbstractState<TMachineUserData, TStateUserData>>
        get() {
            assert(!this.IsClosed)
            return sequence { }
        }
    public override val Descendants: Sequence<AbstractState<TMachineUserData, TStateUserData>>
        get() {
            assert(!this.IsClosed)
            return sequence {}
        }
    public override val DescendantsAndSelf: Sequence<AbstractState<TMachineUserData, TStateUserData>>
        get() {
            assert(!this.IsClosed)
            return sequence {
                this.yield(this@State)
            }
        }

    public override val UserData: TStateUserData
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

    public var OnAttachCallback: ((Any?) -> Unit)? = null
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
    public var OnDetachCallback: ((Any?) -> Unit)? = null
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

    public var OnActivateCallback: ((Any?) -> Unit)? = null
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
    public var OnDeactivateCallback: ((Any?) -> Unit)? = null
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
        this.Lifecycle = ELifecycle.Closing
        this.OnCloseCallback?.invoke()
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
        this.Activity = EActivity.Active
    }

    internal override fun Deactivate(argument: Any?) {
        this.Activity = EActivity.Deactivating
        this.OnDeactivateCallback?.invoke(argument)
        this.Activity = EActivity.Inactive
    }

}
