package com.superjose128.nemesis.core.actor.weapons;

import com.superjose128.nemesis.core.GameWorld;
import com.superjose128.nemesis.core.actor.Actor;
import com.superjose128.nemesis.core.actor.DestroyWhenDissapearActor;
import com.superjose128.nemesis.core.collision.Collideable;
import com.superjose128.nemesis.core.collision.CollideableTypes;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import playn.core.Image;
import pythagoras.f.Point;

public class BasicBullet extends DestroyWhenDissapearActor {
	private final PolygonShape shape = new PolygonShape();
	
	public BasicBullet(GameWorld world, Point pos){
		super(world);
		this.setPos(pos.x, pos.y);
		this.vel.y = 0f;
		this.vel.x = 1000f;
		
		shape.setAsBox(14, 4); // half size of the sprite size
	}

	
	@Override
	public CollideableTypes getType() {
		return CollideableTypes.PLAYER_WEAPON;
	}

	@Override
	public Shape getShape() {
		return shape;
	}


	@Override
	public void initializeSprite() {
		Image imgSprite = game.plat.assets().getImage("images/sprites/bullet.png");
		this.sprite = new AnimatedSprite(imgSprite, 24, 8, 1, 0);
	}
	
	@Override
	public void collisionCallback(Collideable hit) {
		switch(hit.getType()){
			case ENEMY:
				die();
				((Actor)hit).die();
				// OUUUUCH!
				break;
			default: break;
		}
	}
}
