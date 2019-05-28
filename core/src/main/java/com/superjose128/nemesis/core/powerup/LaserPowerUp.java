package com.superjose128.nemesis.core.powerup;

import pythagoras.f.Point;
import tripleplay.sound.Clip;

import com.superjose128.nemesis.core.NemesisGame;
import com.superjose128.nemesis.core.actor.weapons.BasicBullet;

public class LaserPowerUp extends PowerUp {
	final static private Clip fireSound = (Clip)NemesisGame.soundsFx.getSound("normalFire");
	private final static int MAX_BULLETS =1;
	private static int liveBullets = 0;
	
	public LaserPowerUp(){
		super();
		this.name = "LASER";
				
		this.maxLevels = 2;
		this.basic = false;
		this.excludes = new String[]{"FIRE","DOUBLE"};
	}
	
	@Override
	public void onFire() {
		if(liveBullets >= MAX_BULLETS){
			return;
		}
		
		liveBullets++;
		
		synchronized(fireSound){		
			if(fireSound.isPlaying()) fireSound.stop(); 
			fireSound.play();
		}
				
		// generate bullet
		Point from = new Point();
		from.x = owner.getPos().x + 23f; // 23 is half size of metallion sprite
		from.y = owner.getPos().y;
		BasicBullet bullet = new BasicBullet(from){
			@Override
			public void removeLayerFromWorld(){
				super.removeLayerFromWorld();
				liveBullets--;
			}
		};
		
		bullet.addToWorld(owner.getWorld());
	}

	@Override
	public void onLevelUp() {
		// NOP
	}

}
