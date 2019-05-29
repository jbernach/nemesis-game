package com.superjose128.nemesis.android;

import playn.android.GameActivity;

import com.superjose128.nemesis.core.NemesisGame;

public class NemesisGameActivity extends GameActivity {

  @Override public void main () {
    new NemesisGame(platform());
  }
}
