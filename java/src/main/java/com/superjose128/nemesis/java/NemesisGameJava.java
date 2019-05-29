package com.superjose128.nemesis.java;

import com.superjose128.nemesis.core.NemesisGame;
import playn.java.LWJGLPlatform;

public class NemesisGameJava {

  public static void main (String[] args) {
    LWJGLPlatform.Config config = new LWJGLPlatform.Config();
    // use config to customize the Java platform, if needed
    config.width = 1280*2/3;
    config.height = 720*2/3;
    LWJGLPlatform plat = new LWJGLPlatform(config);
    try {
      plat.graphics().registerFont("Nemesis", plat.assets().getFont("fonts/avant_pixel.ttf"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    new NemesisGame(plat);
    plat.start();
  }
}
