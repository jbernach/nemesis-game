package com.superjose128.nemesis.core;

import com.superjose128.nemesis.core.sound.GameSounds;

import playn.core.Game;
import playn.core.PlayN;
import playn.core.util.Clock;
import tripleplay.game.ScreenStack;


public class NemesisGame extends Game.Default {
	public static final int UPDATE_RATE = 30; // 33 fps
			
	protected final Clock.Source clock = new Clock.Source(UPDATE_RATE);
	protected final ScreenStack screens = new ScreenStack() {		
		@Override
		protected void handleError(RuntimeException error) {
			PlayN.log().error("Error:", error);
		}
	};
	
	public static final GameSounds soundsFx = new GameSounds();	

	public NemesisGame() {
		super(UPDATE_RATE);
	}

	@Override
	public void init() {						
		screens.push(new LogoScreen(screens,this));
	}

	@Override
	public void update(int delta) {
		clock.update(delta);
		screens.update(delta);
		soundsFx.getSoundBoard().update(delta);
	}

	@Override
	public void paint(float alpha) {
		clock.paint(alpha);
		screens.paint(clock);
	}

	
}
