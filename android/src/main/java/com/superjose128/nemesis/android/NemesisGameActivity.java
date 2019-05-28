package com.superjose128.nemesis.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import com.superjose128.nemesis.core.NemesisGame;

public class NemesisGameActivity extends GameActivity {

  @Override
  public void main(){
	  platform().graphics().registerFont("fonts/avant_pixel.ttf", "Nemesis", playn.core.Font.Style.PLAIN);
	  PlayN.run(new NemesisGame());
  }
}
