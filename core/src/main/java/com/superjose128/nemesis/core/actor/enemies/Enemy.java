package com.superjose128.nemesis.core.actor.enemies;

import static playn.core.PlayN.assets;
import playn.core.Image;
import tripleplay.sound.Playable;

import com.superjose128.nemesis.core.NemesisGame;
import com.superjose128.nemesis.core.actor.DestroyWhenDissapearActor;
import com.superjose128.nemesis.core.actor.Explosion;
import com.superjose128.nemesis.core.collision.Collideable;
import com.superjose128.nemesis.core.collision.CollideableTypes;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;

public abstract class Enemy extends DestroyWhenDissapearActor {
	protected int score = 100;
	
	private static final Image imgExplosionSprite = assets().getImage("images/sprites/explode1.png");
	@Override
	public CollideableTypes getType() {
		return CollideableTypes.ENEMY;
	}
	
	
	@Override
	public void die() {
		super.die();
		Explosion explosion = new Explosion(this.getPos(),300) {
			@Override
			public AnimatedSprite initializeSprite() {
				
				AnimatedSprite sp = new AnimatedSprite(imgExplosionSprite, 64, 64, 8, 50);
				sp.loop = false;

				return sp;
			}
			
			@Override
			public Playable getSound() {
				return NemesisGame.soundsFx.getSound("explode1");
			}
				
		};
		explosion.addToWorld(getWorld());
		
		getWorld().getPlayer1().addScore(this.score);
		
	}
	
}
