package com.superjose128.nemesis.core.actor.enemies;

import com.superjose128.nemesis.core.NemesisGame;
import com.superjose128.nemesis.core.actor.DestroyWhenDissapearActor;
import com.superjose128.nemesis.core.actor.Explosion;
import com.superjose128.nemesis.core.collision.CollideableTypes;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import playn.core.Image;
import tripleplay.sound.Playable;

public abstract class Enemy extends DestroyWhenDissapearActor {
	protected int score = 100;


	public Enemy(NemesisGame game) {
		super(game);
	}

	@Override
	public CollideableTypes getType() {
		return CollideableTypes.ENEMY;
	}
	
	
	@Override
	public void die() {
		super.die();
		Explosion explosion = new Explosion(game(), this.getPos(),300) {
			@Override
			public AnimatedSprite initializeSprite() {
				Image imgExplosionSprite = game.plat.assets().getImage("images/sprites/explode1.png");
				AnimatedSprite sp = new AnimatedSprite(imgExplosionSprite, 64, 64, 8, 50);
				sp.loop = false;

				return sp;
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
