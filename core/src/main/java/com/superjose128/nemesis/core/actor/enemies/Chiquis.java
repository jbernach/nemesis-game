package com.superjose128.nemesis.core.actor.enemies;

import static playn.core.PlayN.assets;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;

import playn.core.Image;

import com.superjose128.nemesis.core.collision.Collideable;
import com.superjose128.nemesis.core.collision.CollideableTypes;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;

public class Chiquis extends Enemy {
private final PolygonShape shape = new PolygonShape();
	private static Image imgSprite = assets().getImage("images/sprites/chiquis.png");
	public Chiquis(){
		super();
		
		 
		shape.setAsBox(32, 32); // half size of the sprite size
	}

	@Override
	public Shape getShape() {
		return shape;
	}

	@Override
	public AnimatedSprite initializeSprite() {
		
		AnimatedSprite sp = new AnimatedSprite(imgSprite, 64, 64, 1, 0);
		
		return sp;
	}

}
