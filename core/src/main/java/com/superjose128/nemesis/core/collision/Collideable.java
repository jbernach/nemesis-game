package com.superjose128.nemesis.core.collision;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Transform;

public interface Collideable {
	CollideableTypes getType();
	Shape getShape();
	Transform getTransform();
	void collisionCallback(Collideable hit);
}
