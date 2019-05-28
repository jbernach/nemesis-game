package com.superjose128.nemesis.core.actor;

import com.superjose128.nemesis.core.collision.CollideableTypes;

public abstract class AbstractEnemy extends Actor {

	@Override
	public CollideableTypes getType() {
		return CollideableTypes.ENEMY;
	}


}
