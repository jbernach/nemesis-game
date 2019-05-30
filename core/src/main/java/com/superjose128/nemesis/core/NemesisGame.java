package com.superjose128.nemesis.core;

import com.superjose128.nemesis.core.sound.GameSounds;
import playn.core.Clock;
import playn.core.Image;
import playn.core.Platform;
import playn.scene.ImageLayer;
import playn.scene.SceneGame;
import tripleplay.game.ScreenStack;

public class NemesisGame extends SceneGame {
    public static final int UPDATE_RATE = 33; // 30 fps

    public final ScreenStack screens;
    public final GameSounds soundsFx;

    public NemesisGame(Platform plat) {
        super(plat, UPDATE_RATE); // update our "simulation" 33ms (30 times per second)

        this.soundsFx = new GameSounds(plat, this.update);

        this.screens = new ScreenStack(this, rootLayer);
        this.screens.push(new LogoScreen(this));
    }
}
