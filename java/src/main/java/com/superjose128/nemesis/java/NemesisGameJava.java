package com.superjose128.nemesis.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import com.superjose128.nemesis.core.NemesisGame;

public class NemesisGameJava {

  public static void main(String[] args) {
    JavaPlatform.Config config = new JavaPlatform.Config();
    // use config to customize the Java platform, if needed
    config.width = 1280*2/3;
    config.height = 720*2/3;
    JavaPlatform platform = JavaPlatform.register(config);
    platform.graphics().registerFont("Nemesis", "fonts/avant_pixel.ttf");
    PlayN.run(new NemesisGame());
  }
}
