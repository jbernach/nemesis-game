package com.superjose128.nemesis.core.actor;

import com.superjose128.nemesis.core.GameWorld;
import com.superjose128.nemesis.core.collision.CollideableTypes;

public abstract class AbstractEnemy extends Actor {

	public AbstractEnemy(GameWorld world) {
		super(world);
	}

	@Override
	public CollideableTypes getType() {
		return CollideableTypes.ENEMY;
	}


}
