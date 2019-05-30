package com.superjose128.nemesis.core.actor;

import com.superjose128.nemesis.core.NemesisGame;
import com.superjose128.nemesis.core.collision.CollideableTypes;
import org.jbox2d.collision.shapes.Shape;
import playn.core.Clock;
import pythagoras.f.Point;
import tripleplay.sound.Playable;

public abstract class Explosion extends DieOnTimeActor {
	private boolean soundPlayed = false;

	public Explosion(NemesisGame game, Point pos, long lifeTime){
		super(game);
		this.setPos(pos.x, pos.y);
		this.vel.y = 0f;
		this.vel.x = 0f;
		this.setLifeTime(lifeTime);
	}
	
	public abstract Playable getSound();
	
	@Override
	public CollideableTypes getType() {
		return CollideableTypes.GHOST;
	}

	@Override
	public Shape getShape() {
		return null;
	}

	@Override
	public void paint(Clock clock) {
		super.paint(clock);
		if(!soundPlayed){
			soundPlayed = true;
			Playable sound = getSound();
			sound.play();			
		}
	}
}
