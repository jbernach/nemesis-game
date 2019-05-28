package com.superjose128.nemesis.core.actor;

import static playn.core.PlayN.*;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;

import playn.core.Image;
import tripleplay.sound.Playable;

import com.superjose128.nemesis.core.GameWorld;
import com.superjose128.nemesis.core.NemesisGame;
import com.superjose128.nemesis.core.powerup.*;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import com.superjose128.nemesis.core.collision.Collideable;
import com.superjose128.nemesis.core.collision.CollideableTypes;


public class PlayerMetallion extends Player {
	final static int ROW_NORMAL = 0;
	final static int ROW_UP = 1;
	final static int ROW_DOWN = 2;
	
	private static final Image imgSprite = assets().getImage("images/sprites/metallion.png");
	private static final Image imgExplosionSprite = assets().getImage("images/sprites/explode_viper.png");
	private static final PolygonShape shape = new PolygonShape();
	
	public PlayerMetallion(){
		super();
		
		this.setPos(GameWorld.WORLD_WIDTH/3f, GameWorld.WORLD_HEIGHT/2f);
		
		PowerUp pup = new SpeedPowerUp();
		powerUps.put(pup.getName(), pup);
		pup.onArmed(this);
		pup = new BasicFirePowerUp();
		powerUps.put(pup.getName(), pup);
		pup.onArmed(this);
		 
		shape.setAsBox(46, 16); 
	}

	@Override
	public void moveUp() {
		this.sprite.setRow(PlayerMetallion.ROW_UP);
		super.moveUp();
	}

	@Override
	public void moveDown() {
		this.sprite.setRow(PlayerMetallion.ROW_DOWN);
		super.moveDown();
	}

	@Override
	public void stopVertical() {
		this.sprite.setRow(0);
		super.stopVertical();
		
	}

	@Override
	public Shape getShape() {
		return shape;
	}

	@Override
	public CollideableTypes getType() {
		return CollideableTypes.PLAYER;
	}

	@Override
	public AnimatedSprite initializeSprite() {
		
		AnimatedSprite sp = new AnimatedSprite(imgSprite, 92, 64, 2, 600);
		sp.loop = false;
		
		return sp;
	}

	@Override
	public void die() {
		destroy();
		Explosion explosion = new Explosion(this.getPos(),2000) {
			
			@Override
			public AnimatedSprite initializeSprite() {
				AnimatedSprite sp = new AnimatedSprite(imgExplosionSprite, 128, 56, 5, 400);
				sp.loop = false;

				return sp;
			}
			
			@Override
			public Playable getSound() {
				return NemesisGame.soundsFx.getSound("explodeViper");
			}
			
			@Override
			public void onDeath(){
				PlayerMetallion.this.onDeath();
			}
		};
		explosion.addToWorld(getWorld());
	}

	
}