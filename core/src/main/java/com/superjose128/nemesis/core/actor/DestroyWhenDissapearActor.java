package com.superjose128.nemesis.core.actor;

import com.superjose128.nemesis.core.GameWorld;
import com.superjose128.nemesis.core.NemesisGame;

/**
 *  Actor than automatically detaches from the world when its
 *  position is beyond world limits.
 * @author Joselito y Tere
 *
 */
public abstract class DestroyWhenDissapearActor extends Actor {
	public DestroyWhenDissapearActor(NemesisGame game) {
		super(game);
	}

	public void update(int delta) {
		super.update(delta);
		
		if(this.getPos().x < 0 || this.getPos().x > GameWorld.WORLD_WIDTH || this.getPos().y < 0 || this.getPos().y > GameWorld.WORLD_HEIGHT){
			die();
		}
	}
}
