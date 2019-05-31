package com.superjose128.nemesis.core.powerup;

import com.superjose128.nemesis.core.NemesisGame;
import com.superjose128.nemesis.core.actor.Player;
import com.superjose128.nemesis.core.actor.weapons.BasicBullet;
import pythagoras.f.Point;
import tripleplay.sound.Clip;

import java.util.concurrent.atomic.AtomicInteger;

public class DoublePowerUp extends PowerUp {
	private Clip fireSound;
	private final static int MAX_BULLETS = 1;
	private AtomicInteger liveBullets = new AtomicInteger(0);
	
	public DoublePowerUp(){
		super();
		this.name = "DOUBLE";
				
		this.maxLevels = 1;
		this.basic = false;
		this.excludes = new String[]{"FIRE","LASER"};
	}

	@Override
	public void onArmed(Player player) {
		super.onArmed(player);
		fireSound = (Clip) player.game().soundsFx.getSound("normalFire");
	}

	@Override
	public void onFire() {
		if(liveBullets.intValue() >= MAX_BULLETS){
			return;
		}
		
		liveBullets.incrementAndGet();
		
		synchronized(fireSound){		
			if(fireSound.isPlaying()) fireSound.stop(); 
			fireSound.play();
		}
				
		// generate bullet
		Point from = new Point();
		from.x = owner.getPos().x + 23f; // 23 is half size of metallion sprite
		from.y = owner.getPos().y;
		BasicBullet bullet = new BasicBullet(this.owner.game(), from);
		bullet.alive.connect(alive -> {
			if (!alive) {
				liveBullets.decrementAndGet();
			}
		});

		this.owner.getWorld().addActor(bullet);
	}

	@Override
	public void onLevelUp() {
		// NOP
	}

}
