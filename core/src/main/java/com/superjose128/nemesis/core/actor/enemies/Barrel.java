package com.superjose128.nemesis.core.actor.enemies;

import static playn.core.PlayN.assets;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;

import playn.core.Image;

import com.superjose128.nemesis.core.collision.Collideable;
import com.superjose128.nemesis.core.collision.CollideableTypes;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;

public class Barrel extends Enemy {
private final PolygonShape shape = new PolygonShape();
	private static Image imgSprite = assets().getImage("images/sprites/barrells.png");
	public Barrel(){
		super();
		
		 
		shape.setAsBox(18, 32); // half size of the sprite size
	}

	@Override
	public Shape getShape() {
		return shape;
	}

	@Override
	public AnimatedSprite initializeSprite() {
		
		AnimatedSprite sp = new AnimatedSprite(imgSprite, 64, 64, 4, 100);
		
		return sp;
	}

}
