package com.superjose128.nemesis.core.actor.weapons;

import com.superjose128.nemesis.core.NemesisGame;
import com.superjose128.nemesis.core.actor.Actor;
import com.superjose128.nemesis.core.actor.DestroyWhenDissapearActor;
import com.superjose128.nemesis.core.collision.Collideable;
import com.superjose128.nemesis.core.collision.CollideableTypes;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import playn.core.Image;
import pythagoras.f.Point;

public class Missile extends DestroyWhenDissapearActor {
	private final PolygonShape shape = new PolygonShape();
	
	public Missile(NemesisGame game, Point pos){
		super(game);
		this.setPos(pos.x, pos.y);
		this.vel.y = 300f;
		this.vel.x = 50f;
		
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
	public AnimatedSprite initializeSprite() {
		Image imgSprite = game.plat.assets().getImage("images/sprites/missilie.png");
		return new AnimatedSprite(imgSprite, 40, 32, 1, 0);
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
