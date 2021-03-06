package com.superjose128.nemesis.core.powerup;

import com.superjose128.nemesis.core.NemesisGame;
import com.superjose128.nemesis.core.actor.Player;
import com.superjose128.nemesis.core.actor.weapons.BasicBullet;
import pythagoras.f.Point;
import tripleplay.sound.Clip;

import java.util.concurrent.atomic.AtomicInteger;

public class BasicFirePowerUp extends PowerUp {
	private final Clip fireSound;
	private final static int MAX_BULLETS = 3;
	private AtomicInteger liveBullets = new AtomicInteger(0);
	
	public BasicFirePowerUp(NemesisGame game){
		super(game);
		this.name = "FIRE";
		this.basic = true;
		fireSound = (Clip) game.soundsFx.get("normalFire");
	}

	@Override
	public void onArmed(Player player) {
		super.onArmed(player);

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
		from.x = owner.getPos().x + 46f; // 46 is half size of metallion sprite
		from.y = owner.getPos().y;
		BasicBullet bullet = new BasicBullet(this.owner.getWorld(), from);
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
