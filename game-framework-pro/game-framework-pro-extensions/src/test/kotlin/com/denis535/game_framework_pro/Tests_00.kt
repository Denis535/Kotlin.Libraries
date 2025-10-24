package com.denis535.game_framework_pro

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

public class Tests_00 {

    @Test
    fun Test_00() {
        Program().use {
        }
    }

}

internal class Program : AbstractProgram2<Theme, Screen, Router, Application> {

    public constructor() {
        this.Application = Application(this)
        this.Router = Router(this)
        this.Screen = Screen(this)
        this.Theme = Theme(this)
    }

    protected override fun OnClose() {
        this.Theme!!.close()
        this.Screen!!.close()
        this.Router!!.close()
        this.Application!!.close()
        super.OnClose()
    }

}
// UI
internal class Theme : AbstractTheme2<Router, Application> {

    public constructor(provider: DependencyProvider) : super(provider) {
        this.Machine.SetRoot(MainPlayList().State, null, null)
        this.Machine.SetRoot(GamePlayList().State, null, null)
    }

    protected override fun OnClose() {
        this.Machine.SetRoot(null, null, null)
        super.OnClose()
    }

}

internal class MainPlayList : AbstractPlayList {

    public constructor()

    protected override fun OnClose() {
    }

    protected override fun OnActivate(argument: Any?) {
    }

    protected override fun OnDeactivate(argument: Any?) {
    }

}

internal class GamePlayList : AbstractPlayList {

    public constructor()

    protected override fun OnClose() {
    }

    protected override fun OnActivate(argument: Any?) {
    }

    protected override fun OnDeactivate(argument: Any?) {
    }

}
// UI
internal class Screen : AbstractScreen2<Router, Application> {

    public constructor(provider: DependencyProvider) : super(provider) {
        this.Machine.SetRoot(RootWidget(provider).Node, null, null)
    }

    protected override fun OnClose() {
        this.Machine.SetRoot(null, null, null)
        super.OnClose()
    }

}

internal class RootWidget : AbstractWidget2 {

    public constructor(provider: DependencyProvider) : super(provider) {
        this.NodeMutable.AddChild(MainWidget(provider).Node, null)
        this.NodeMutable.AddChild(GameWidget(provider).Node, null)
    }

    protected override fun OnClose() {
        this.NodeMutable.RemoveChildren({ true }, null, null)
    }

    protected override fun OnActivate(argument: Any?) {
    }

    protected override fun OnDeactivate(argument: Any?) {
    }

}

internal class MainWidget : AbstractViewableWidget2 {
    internal class View {
        public constructor()
    }

    public constructor(provider: DependencyProvider) : super(provider) {
        this.View = View()
    }

    protected override fun OnClose() {
        this.NodeMutable.RemoveChildren({ true }, null, null)
    }

    protected override fun OnActivate(argument: Any?) {
    }

    protected override fun OnDeactivate(argument: Any?) {
    }

}

internal class GameWidget : AbstractViewableWidget2 {
    internal class View {
        public constructor()
    }

    public constructor(provider: DependencyProvider) : super(provider) {
        this.View = View()
    }

    protected override fun OnClose() {
        this.NodeMutable.RemoveChildren({ true }, null, null)
    }

    protected override fun OnActivate(argument: Any?) {
    }

    protected override fun OnDeactivate(argument: Any?) {
    }

}
// UI
internal class Router : AbstractRouter2<Theme, Screen, Application> {

    public constructor(provider: DependencyProvider) : super(provider)

    protected override fun OnClose() {
        super.OnClose()
    }

}
// App
internal class Application : AbstractApplication2 {

    public val Game: Game

    public constructor(provider: DependencyProvider) : super(provider) {
        this.Game = Game(this.Provider)
    }

    protected override fun OnClose() {
        this.Game.close()
        super.OnClose()
    }

}
// Domain
internal class Game : AbstractGame2 {

    public constructor(provider: DependencyProvider) : super(provider)

    protected override fun OnClose() {
        super.OnClose()
    }

}
