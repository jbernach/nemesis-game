package com.superjose128.nemesis.core;

import playn.core.Game;
import tripleplay.game.ScreenStack;

public class GameWorldScreen extends ScreenStack.UIScreen{
	private final NemesisGame game;
	private final ScreenStack stack;
	private GameWorld gameWorld = new GameWorld(this);
	    
	//private GroupLayer controlLayer;
	    
	public GameWorldScreen(ScreenStack stack, NemesisGame game){
		super(game.plat);
		this.game = game;
		this.stack = stack;

		this.update.connect(clock -> gameWorld.update(clock.dt));

		this.paint.connect(clock -> gameWorld.paint(clock));
	}

	@Override
	public Game game() {
		return this.game;
	}

	@Override
	public void wasAdded() {
		super.wasAdded();
		setupScreen();
	}

	public void beginGame(){
		gameWorld.loadLevel("1");
	}

	public void hide(){
		this.stack.remove(this);
	}
	/**
	 * Setups the world for start playing it
	 */
	private void setupScreen(){
		// scale the viewport 
		float scaleW = this.size().width()/ GameWorld.NATIVE_RES_WIDTH;
		float scaleH = this.size().height()/ GameWorld.NATIVE_RES_HEIGHT;
		float scale = Math.min(scaleW, scaleH);
		
		layer.setScale(scale);		
		//graphics().ctx().setTextureFilter(GLContext.Filter.NEAREST, GLContext.Filter.NEAREST); // Sin interpolación				
	}
}
