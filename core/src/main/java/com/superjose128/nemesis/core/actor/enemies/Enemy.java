package com.superjose128.nemesis.core.actor.enemies;

import com.superjose128.nemesis.core.GameWorld;
import com.superjose128.nemesis.core.actor.DestroyWhenDissapearActor;
import com.superjose128.nemesis.core.actor.Explosion;
import com.superjose128.nemesis.core.collision.CollideableTypes;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import tripleplay.sound.Playable;

public abstract class Enemy extends DestroyWhenDissapearActor {
	protected int score = 100;


	public Enemy(GameWorld world) {
		super(world);
	}

	@Override
	public CollideableTypes getType() {
		return CollideableTypes.ENEMY;
	}
	
	
	@Override
	public void die() {
		if(!this.isAlive()) return;

		super.die();

		if (!this.isOutOfBounds() && this.isVisible()) {
			Explosion explosion = new Explosion(world, this.getPos(),300) {
				@Override
				public void initializeSprite() {
					AnimatedSprite sp = new AnimatedSprite(game.images.get("explode1"), 64, 64, 8, 50);
					sp.loop = false;

					this.sprite = sp;
				}

				@Override
				public Playable getSound() {
					return this.game.soundsFx.get("explode1");
				}
			};

			getWorld().addActor(explosion);
			getWorld().getPlayer1().addScore(this.score);
		}

	}
	
}
