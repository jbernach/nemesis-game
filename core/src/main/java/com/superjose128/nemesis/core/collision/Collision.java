package com.superjose128.nemesis.core.collision;

/**
 * A collision between two collideable objects.
 * @author Joselito y Tere
 *
 */
public class Collision {
	public Collideable collideableA;
	public Collideable collideableB;
	
	public Collision(Collideable a, Collideable b){
		this.collideableA = a;
		this.collideableB = b;
	}
}
