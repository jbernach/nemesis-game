package com.superjose128.nemesis.core;

import com.superjose128.nemesis.core.sound.GameSounds;
import com.superjose128.nemesis.core.sprites.GameImages;
import playn.core.Platform;
import playn.scene.SceneGame;
import tripleplay.game.ScreenStack;

public class NemesisGame extends SceneGame {
    private static final int UPDATE_RATE = 33; // 30 fps

    public final ScreenStack screens;
    public final GameSounds soundsFx;
    public final GameImages images;

    public NemesisGame(Platform plat) {
        super(plat, UPDATE_RATE); // update our "simulation" 33ms (30 times per second)

        this.soundsFx = new GameSounds(plat, this.update);
        this.images = new GameImages(plat);

        this.screens = new ScreenStack(this, rootLayer);
        this.screens.push(new LogoScreen(this));
    }
}
