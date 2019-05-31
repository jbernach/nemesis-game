package com.superjose128.nemesis.core.actor;

import com.superjose128.nemesis.core.GameWorld;
import com.superjose128.nemesis.core.NemesisGame;
import playn.core.Game;

/**
 *  Actor than automatically detaches from the world when its
 *  position is beyond world limits.
 * @author Joselito y Tere
 *
 */
public abstract class DieOnTimeActor extends Actor {
	private long lifeTime = 1000; // ms
	private long life = lifeTime;

	public DieOnTimeActor(GameWorld world) {
		super(world);
	}

	public void update(int delta) {
		super.update(delta);
		
		life = life - delta;
		
		if(life <= 0){
			die();
		}
		
	}

	public long getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(long lifeTime) {
		this.lifeTime = lifeTime;
		this.life = lifeTime;
	}
}
