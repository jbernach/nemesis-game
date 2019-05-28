package com.superjose128.nemesis.core.collision;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Transform;

import com.superjose128.nemesis.core.actor.Actor;

public interface Collideable {
	public CollideableTypes getType();
	public Shape getShape();
	public Transform getTransform();
	public void collisionCallback(Collideable hit);
}
