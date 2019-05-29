package com.superjose128.nemesis.core;

import com.superjose128.nemesis.core.sound.GameSounds;
import playn.core.Platform;
import playn.scene.SceneGame;
import tripleplay.game.ScreenStack;

public class NemesisGame extends SceneGame {
  public static final int UPDATE_RATE = 33; // 30 fps

  protected ScreenStack screens;

  public static final GameSounds soundsFx = new GameSounds();

  public NemesisGame (Platform plat) {
    super(plat, UPDATE_RATE); // update our "simulation" 33ms (30 times per second)

     this.screens = new ScreenStack(this, rootLayer) {
      @Override
      protected void handleError(RuntimeException error) {
        this._game.plat.log().error("Error:", error);
      }
    };

    // create and add background image layer
    /*Image bgImage = plat.assets().getImage("images/bg.png");
    ImageLayer bgLayer = new ImageLayer(bgImage);
    // scale the background to fill the screen
    bgLayer.setSize(plat.graphics().viewSize);
    rootLayer.add(bgLayer);*/
  }


}
