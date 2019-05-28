package com.superjose128.nemesis.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import com.superjose128.nemesis.core.NemesisGame;

public class NemesisGameHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform.Config config = new HtmlPlatform.Config();    
    // use config to customize the HTML platform, if needed
    HtmlPlatform platform = HtmlPlatform.register(config);
    platform.assets().setPathPrefix("nemesis/");
    PlayN.run(new NemesisGame());
  }
}
