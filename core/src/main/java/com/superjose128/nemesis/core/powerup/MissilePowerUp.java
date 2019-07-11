package com.superjose128.nemesis.core.powerup;

import com.superjose128.nemesis.core.NemesisGame;
import com.superjose128.nemesis.core.actor.weapons.Missile;
import pythagoras.f.Point;

import java.util.concurrent.atomic.AtomicInteger;


public class MissilePowerUp extends PowerUp {
	private int maxBullets = 1;
	private AtomicInteger liveBullets = new AtomicInteger(0);
	
	public MissilePowerUp(NemesisGame game){
		super(game);
		this.name = "MISSILE";
				
		this.maxLevels = 2;
	}
	
	@Override
	public void onFire() {
		if(liveBullets.intValue() >= maxBullets){
			return;
		}
					
		liveBullets.incrementAndGet();
						
		// generate bullet
		Point from = new Point();
		from.x = owner.getPos().x;
		from.y = owner.getPos().y + 26f;

		Missile missile = new Missile(this.owner.getWorld(), from);
		missile.alive.connect(alive -> {
			if (!alive) {
				liveBullets.decrementAndGet();
			}
		});

		this.owner.getWorld().addActor(missile);
	}

	@Override
	public void onLevelUp() {
		maxBullets++;
	}

}
