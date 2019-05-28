package com.superjose128.nemesis.core;

import java.util.HashMap;

import com.superjose128.nemesis.core.actor.enemies.*;
import com.superjose128.nemesis.core.powerup.PowerUpCapsule;
import com.superjose128.nemesis.core.powerup.WeaponSelectionModel;

import playn.core.Key;
import playn.core.Keyboard;
import playn.core.PlayN;
import pythagoras.f.Point;

public class KeyboardGameControl extends Keyboard.Adapter {
	private Controllable player;
	private WeaponSelectionModel selectionModel;
	private GameWorld gameWorld = null; // for use in tricks
	private HashMap<Key, String> keysDown = new HashMap<Key, String>();

	public KeyboardGameControl(Controllable player, WeaponSelectionModel selectionModel, GameWorld world){
		this.player = player;
		this.selectionModel = selectionModel;
		this.gameWorld = world;
	}
	
	@Override
	public void onKeyDown(Keyboard.Event event) {
		Key k = event.key();
		keysDown.put(k, "1");
		//log().debug(""+k+" down");
		
			switch (k) {
		    case LEFT:
		    	
		    	player.moveLeft();
		    	break;
		    case RIGHT:
		    	player.moveRight();
		    	break;
		    case UP:
		    	player.moveUp();
		    	break;
		    case DOWN:
		    	player.moveDown();
		    	break;
		    case SPACE: 
		    	player.fire();
		    	break;
		    	
		    	// Tricks
		    case M: 
		    	if(selectionModel != null){
		    		selectionModel.selectCurrent();
		    	}
		    	break;
		    case U:
		    	if(selectionModel != null){
		    		selectionModel.increaseWeaponCoins();
		    	}
		    	break;
		    case C:
		    	PowerUpCapsule capsule = new PowerUpCapsule(new Point( GameWorld.WORLD_WIDTH/2 + PlayN.random() * GameWorld.WORLD_WIDTH/2, PlayN.random() * GameWorld.WORLD_HEIGHT)); 
		    	capsule.addToWorld(gameWorld);
		    	break;
		    case E:
		    	for(int i = 0;i < 20;i++){
			    	Enemy enemy = new Chiquis();
			    	enemy.setPos(GameWorld.WORLD_WIDTH/2 + PlayN.random() * GameWorld.WORLD_WIDTH/2, PlayN.random() * GameWorld.WORLD_HEIGHT);
			    	enemy.setSpeed(100*PlayN.random());
			    	enemy.moveLeft();
			    	enemy.addToWorld(gameWorld);
		    	}
		    	break;
		    default:
		    	break;
			}	
	}

	@Override
	public void onKeyUp(Keyboard.Event event) {
		Key k = event.key();
		keysDown.remove(k);
		
			switch (k) {
		        case LEFT:
		        	if(keysDown.get(Key.RIGHT) == null){
		        		player.stopHorizontal();
		        		//log().debug(""+k+" Up: stopH");
		        	}else{
		        		player.moveRight();
		        	}
		        	break;
		        case RIGHT:
		        	if(keysDown.get(Key.LEFT) == null){
		        		player.stopHorizontal();
		        		//log().debug(""+k+" Up: stopH");
		        	}else{
		        		player.moveLeft();
		        	}
		        	break;
		        case UP:
		        	if(keysDown.get(Key.DOWN) == null){
		        		player.stopVertical();
		        	}else{
		        		player.moveDown();
		        	}
		        	break;
		        case DOWN:
		        	if(keysDown.get(Key.UP) == null){
		        		player.stopVertical();
		        	}else{
		        		player.moveUp();
		        	}
		        	break;
		        default: break;
	        }
		
	}

}
