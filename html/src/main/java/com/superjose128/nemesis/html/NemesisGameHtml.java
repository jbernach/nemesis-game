package com.superjose128.nemesis.html;

import com.google.gwt.core.client.EntryPoint;
import playn.html.HtmlPlatform;
import com.superjose128.nemesis.core.NemesisGame;

public class NemesisGameHtml implements EntryPoint {

  @Override public void onModuleLoad () {
    HtmlPlatform.Config config = new HtmlPlatform.Config();
    // use config to customize the HTML platform, if needed
    HtmlPlatform plat = new HtmlPlatform(config);
    plat.assets().setPathPrefix("nemesis/");
    new NemesisGame(plat);
    plat.start();
  }
}
