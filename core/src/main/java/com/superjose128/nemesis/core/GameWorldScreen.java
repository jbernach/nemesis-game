package com.superjose128.nemesis.core;

import static playn.core.PlayN.*;
import playn.core.util.Clock;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIAnimScreen;

public class GameWorldScreen extends UIAnimScreen{
	private final NemesisGame game;
	private final ScreenStack stack;
	private GameWorld gameWorld = new GameWorld(this);
	    
	//private GroupLayer controlLayer;
	    
	public GameWorldScreen(ScreenStack stack, NemesisGame game){
		this.game = game;
		this.stack = stack;		
	}
	
	@Override
	public void wasAdded() {
		super.wasAdded();
		setupScreen();
	}

	@Override
    public void wasShown () {
    	super.wasShown();
    }
    
	public void beginGame(){
		gameWorld.loadLevel("1");
	}
	
	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub
		super.update(delta);
		gameWorld.update(delta);	}

	@Override
	public void paint(Clock clock) {
		// TODO Auto-generated method stub
		super.paint(clock);
		gameWorld.paint(clock);
	}
	
	@Override
	public void wasHidden() {
		super.wasHidden();		
	}
	
	@Override
	public void wasRemoved() {
		super.wasRemoved();
		
		layer.destroyAll();
	}
	
	public void hide(){
		this.stack.remove(this);
	}
	/**
	 * Setups the world for start playing it
	 * @param level
	 */
	private void setupScreen(){
		// scale the viewport 
		float scaleW = width()/GameWorld.NATIVE_RES_WIDTH;
		float scaleH = height()/GameWorld.NATIVE_RES_HEIGHT;		
		float scale = Math.min(scaleW, scaleH);
		
		layer.setScale(scale);		
		//graphics().ctx().setTextureFilter(GLContext.Filter.NEAREST, GLContext.Filter.NEAREST); // Sin interpolación				
	}
		

	
}
