package com.superjose128.nemesis.core.actor.enemies;

import com.superjose128.nemesis.core.GameWorld;
import com.superjose128.nemesis.core.actor.DestroyWhenDissapearActor;
import com.superjose128.nemesis.core.actor.Explosion;
import com.superjose128.nemesis.core.collision.CollideableTypes;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import playn.core.Image;
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
					Image imgExplosionSprite = game.plat.assets().getImage("images/sprites/explode1.png");
					AnimatedSprite sp = new AnimatedSprite(imgExplosionSprite, 64, 64, 8, 50);
					sp.loop = false;

					this.sprite = sp;
				}

				@Override
				public Playable getSound() {
					return this.game.soundsFx.getSound("explode1");
				}
			};

			getWorld().addActor(explosion);
			getWorld().getPlayer1().addScore(this.score);
		}

	}
	
}
