package com.denis535.tree_machine_pro

public class Node<TMachineUserData, TNodeUserData> : AbstractNode<TMachineUserData, TNodeUserData> {

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

    public override val Machine: AbstractTreeMachine<TMachineUserData, TNodeUserData>?
        get() {
            assert(!this.IsClosed)
            return when (val owner = this.Owner) {
                is AbstractTreeMachine<*, *> -> owner as AbstractTreeMachine<TMachineUserData, TNodeUserData>
                is AbstractNode<*, *> -> owner.Machine as AbstractTreeMachine<TMachineUserData, TNodeUserData>
                else -> null
            }
        }

    public override val IsRoot: Boolean
        get() {
            assert(!this.IsClosed)
            return this.Parent == null
        }
    public override val Root: AbstractNode<TMachineUserData, TNodeUserData>
        get() {
            assert(!this.IsClosed)
            return this.Parent?.Root ?: this
        }

    public override val Parent: AbstractNode<TMachineUserData, TNodeUserData>?
        get() {
            assert(!this.IsClosed)
            return this.Owner as? AbstractNode<TMachineUserData, TNodeUserData>
        }
    public override val Ancestors: Sequence<AbstractNode<TMachineUserData, TNodeUserData>>
        get() {
            assert(!this.IsClosed)
            return sequence {
                if (this@Node.Parent != null) {
                    this.yield(this@Node.Parent!!)
                    this.yieldAll(this@Node.Parent!!.Ancestors)
                }
            }
        }
    public override val AncestorsAndSelf: Sequence<AbstractNode<TMachineUserData, TNodeUserData>>
        get() {
            assert(!this.IsClosed)
            return sequence {
                this.yield(this@Node)
                this.yieldAll(this@Node.Ancestors)
            }
        }

    public override var Activity: EActivity = EActivity.Inactive
        get() {
            assert(!this.IsClosed)
            return field
        }
        private set(value) {
            assert(!this.IsClosed)
            assert(field != value)
            field = value
        }

    public override val Children: List<AbstractNode<TMachineUserData, TNodeUserData>>
        get() {
            assert(!this.IsClosed)
            return this.ChildrenMutable
        }
    private val ChildrenMutable: MutableList<AbstractNode<TMachineUserData, TNodeUserData>> = mutableListOf<AbstractNode<TMachineUserData, TNodeUserData>>()
        get() {
            assert(!this.IsClosed)
            return field
        }
    public override val Descendants: Sequence<AbstractNode<TMachineUserData, TNodeUserData>>
        get() {
            assert(!this.IsClosed)
            return sequence {
                for (child in this@Node.Children) {
                    this.yield(child)
                    this.yieldAll(child.Descendants)
                }
            }
        }
    public override val DescendantsAndSelf: Sequence<AbstractNode<TMachineUserData, TNodeUserData>>
        get() {
            assert(!this.IsClosed)
            return sequence {
                this.yield(this@Node)
                this.yieldAll(this@Node.Descendants)
            }
        }

    public var SortDelegate: Proc1<MutableList<AbstractNode<TMachineUserData, TNodeUserData>>>? = null
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

    public override val UserData: TNodeUserData
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

    public constructor(userData: TNodeUserData) {
        this.UserData = userData
    }

    public override fun close() {
        assert(!this.IsClosed)
        when (val owner = this.Owner) {
            is AbstractTreeMachine<*, *> -> assert(owner.IsClosing)
            is AbstractNode<*, *> -> assert(owner.IsClosing)
        }
        this.Lifecycle = ELifecycle.Closing
        this.OnCloseCallback?.invoke()
        assert(this.Children.all { it.IsClosed })
        this.Lifecycle = ELifecycle.Closed
    }

    internal override fun Attach(machine: AbstractTreeMachine<TMachineUserData, TNodeUserData>, argument: Any?) {
        assert(!this.IsClosed)
        assert(this.Owner == null)
        this.Owner = machine
        this.OnAttachCallback?.invoke(argument)
        if (true) {
            this.Activate(argument)
        }
    }

    internal override fun Attach(parent: AbstractNode<TMachineUserData, TNodeUserData>, argument: Any?) {
        assert(!this.IsClosed)
        assert(this.Owner == null)
        this.Owner = parent
        this.OnAttachCallback?.invoke(argument)
        if (this.Parent!!.Activity == EActivity.Active) {
            this.Activate(argument)
        }
    }

    internal override fun Detach(machine: AbstractTreeMachine<TMachineUserData, TNodeUserData>, argument: Any?) {
        assert(!this.IsClosed)
        assert(this.Owner == machine)
        if (true) {
            this.Deactivate(argument)
        }
        this.OnDetachCallback?.invoke(argument)
        this.Owner = null
    }

    internal override fun Detach(parent: AbstractNode<TMachineUserData, TNodeUserData>, argument: Any?) {
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

    public fun AddChild(child: AbstractNode<TMachineUserData, TNodeUserData>, argument: Any?) {
        assert(!this.IsClosed)
        assert(!this.Children.contains(child))
        this.ChildrenMutable.add(child)
        this.SortDelegate?.invoke(this.ChildrenMutable)
        child.Attach(this, argument)
    }

    public fun AddChildren(children: Array<AbstractNode<TMachineUserData, TNodeUserData>>, argument: Any?) {
        assert(!this.IsClosed)
        for (child in children) {
            this.AddChild(child, argument)
        }
    }

    public fun RemoveChild(child: AbstractNode<TMachineUserData, TNodeUserData>, argument: Any?, callback: Proc2<AbstractNode<TMachineUserData, TNodeUserData>, Any?>? = null) {
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

    public fun RemoveChildren(predicate: Predicate1<AbstractNode<TMachineUserData, TNodeUserData>>, argument: Any?, callback: Proc2<AbstractNode<TMachineUserData, TNodeUserData>, Any?>? = null): Int {
        assert(!this.IsClosed)
        var count = 0
        for (child in this.Children.reversed().filter(predicate)) {
            this.RemoveChild(child, argument, callback)
            count++
        }
        return count
    }

    public fun RemoveSelf(argument: Any?, callback: Proc2<AbstractNode<TMachineUserData, TNodeUserData>, Any?>? = null) {
        assert(!this.IsClosed)
        assert(this.Owner != null)
        when (val owner = this.Owner) {
            is TreeMachine<*, *> -> (owner as TreeMachine<TMachineUserData, TNodeUserData>).SetRoot(null, argument, callback)
            is Node<*, *> -> (owner as Node<TMachineUserData, TNodeUserData>).RemoveChild(this, argument, callback)
        }
    }

}
