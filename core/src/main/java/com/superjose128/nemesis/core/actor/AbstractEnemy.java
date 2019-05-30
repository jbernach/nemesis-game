package com.superjose128.nemesis.core.actor;

import com.superjose128.nemesis.core.NemesisGame;
import com.superjose128.nemesis.core.collision.CollideableTypes;

public abstract class AbstractEnemy extends Actor {

	public AbstractEnemy(NemesisGame game) {
		super(game);
	}

	@Override
	public CollideableTypes getType() {
		return CollideableTypes.ENEMY;
	}


}
