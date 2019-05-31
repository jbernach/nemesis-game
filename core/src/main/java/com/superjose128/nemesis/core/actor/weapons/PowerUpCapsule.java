package com.superjose128.nemesis.core.actor.weapons;

import com.superjose128.nemesis.core.NemesisGame;
import com.superjose128.nemesis.core.actor.DestroyWhenDissapearActor;
import com.superjose128.nemesis.core.collision.Collideable;
import com.superjose128.nemesis.core.collision.CollideableTypes;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import playn.core.Image;
import pythagoras.f.Point;

public class PowerUpCapsule extends DestroyWhenDissapearActor {
	private static final PolygonShape shape = new PolygonShape();

	public PowerUpCapsule(NemesisGame game, Point pos){
		super(game);
		this.setPos(pos.x, pos.y);
		this.vel.y = 0f;
		this.vel.x = -200f;
		
		shape.setAsBox(32, 32); // half size of the sprite size
	}

	@Override
	public CollideableTypes getType() {
		return CollideableTypes.POWERUP_CAPSULE;
	}

	@Override
	public Shape getShape() {
		return shape;
	}

	@Override
	public AnimatedSprite initializeSprite() {
		Image imgSprite = this.game.plat.assets().getImage("images/sprites/capsule_red.png");
		return new AnimatedSprite(imgSprite, 64, 64, 2, 150);
	}

	@Override
	public void collisionCallback(Collideable hit) {
		// TODO Auto-generated method stub
		
	}
}