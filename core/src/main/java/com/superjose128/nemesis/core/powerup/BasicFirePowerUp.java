package com.superjose128.nemesis.core.powerup;

import playn.core.PlayN;
import pythagoras.f.Point;
import tripleplay.sound.Clip;

import com.superjose128.nemesis.core.NemesisGame;
import com.superjose128.nemesis.core.actor.weapons.BasicBullet;

public class BasicFirePowerUp extends PowerUp {
	final static private Clip fireSound = (Clip)NemesisGame.soundsFx.getSound("normalFire");
	private final static int MAX_BULLETS = 3;
	private static int liveBullets = 0;
	
	public BasicFirePowerUp(){
		super();
		this.name = "FIRE";
				
		this.maxLevels = 1;
		this.basic = true; 
	}
	
	@Override
	public void onFire() {
		
		if(getLiveBullets() >= MAX_BULLETS){
			return;
		}
					
		incLiveBullets(1);	
		
		
		synchronized(fireSound){		
			if(fireSound.isPlaying()) fireSound.stop(); 
			fireSound.play();
		}
				
		// generate bullet
		Point from = new Point();
		from.x = owner.getPos().x + 46f; // 46 is half size of metallion sprite
		from.y = owner.getPos().y;
		BasicBullet bullet = new BasicBullet(from){
			@Override
			public void onDeath(){
				incLiveBullets(-1);
			}
		};
		
		bullet.addToWorld(owner.getWorld());
	}

	@Override
	public void onLevelUp() {
		// NOP
	}

	public static synchronized int getLiveBullets() {
		return liveBullets;
	}

	public static synchronized void incLiveBullets(int inc) {
		BasicFirePowerUp.liveBullets = getLiveBullets() + inc;
	}

	
}
