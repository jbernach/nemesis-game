package com.superjose128.nemesis.core.powerup;

import com.superjose128.nemesis.core.actor.weapons.Missilie;
import pythagoras.f.Point;

public class MissiliePowerUp extends PowerUp {
	private int MAX_BULLETS = 1;
	private static int liveBullets = 0;
	
	public MissiliePowerUp(){
		super();
		this.name = "MISSILIE";
				
		this.maxLevels = 2;
	}
	
	@Override
	public void onFire() {
		if(getLiveBullets() >= MAX_BULLETS){
			return;
		}
					
		incLiveBullets(1);	
						
		// generate bullet
		Point from = new Point();
		from.x = owner.getPos().x;
		from.y = owner.getPos().y + 26f;
		
		Missilie bullet = new Missilie(from){
			@Override
			public void onDeath(){
				incLiveBullets(-1);
			}
		};
		
		bullet.addToWorld(owner.getWorld());
	}

	public static synchronized int getLiveBullets() {
		return liveBullets;
	}

	public static synchronized void incLiveBullets(int inc) {
		MissiliePowerUp.liveBullets = getLiveBullets() + inc;
	}
	@Override
	public void onLevelUp() {
		MAX_BULLETS++;
	}

}
