package com.superjose128.nemesis.core.actor;

import com.superjose128.nemesis.core.Controllable;
import com.superjose128.nemesis.core.GameWorld;
import com.superjose128.nemesis.core.collision.Collideable;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import playn.core.GroupLayer;
import playn.core.util.Clock;
import pythagoras.f.MathUtil;
import pythagoras.f.Point;
import pythagoras.f.Vector;

import static playn.core.PlayN.*;

/**
 * An actor inside the game (player, enemy, bullet, etc).
 * Belongs to a world, has a animated image representation, a position, velocity and acceleration.
 * 
 * @author Joselito y Tere
 *
 */
public abstract class Actor implements Controllable, Collideable {
	protected boolean alive = true;
	protected AnimatedSprite sprite; // Actor animated character on screen
	protected Point oldPos = new Point(0f,0f); // Old position
	protected Point pos = new Point(0f,0f); // Current position (px world)
	protected Vector vel = new Vector(0f,0f); // Velocity (px world / sec)
	protected Vector acel = new Vector(0f,0f); // Acceleration (px worl / sec*sec)
	protected GameWorld world = null; // Nivel en el que se encuentra el actor
	
	protected float speed = 0; // speed magnitude (world px/sec) for control
	
	protected final Transform t = new Transform();
	
	public Actor(){
		sprite = initializeSprite();
	}
	
	public abstract AnimatedSprite initializeSprite();
	
	public void addToWorld(GameWorld world) {
		this.world = world;
		
		if (sprite != null) {
			world.getActorLayer().add(sprite.layer);
		}
		
		sprite.layer.setTranslation(pos.x,pos.y);
		this.world.addActor(this);
	}
	
	protected void removeLayerFromWorld(){
		if(world != null){
			if(sprite != null){
				GroupLayer parent = world.getActorLayer();
				try{
				parent.remove(sprite.layer);
				}catch(Exception ex){
					// Buuuhh
				}
			}
		
		}
		
		
	}

	public void destroy(){
		this.setAlive(false);
		this.removeLayerFromWorld();
	}
	
	public void die(){
		destroy();
		this.onDeath();
	}
	
	public void onDeath(){
		
	}
	
	public void update(int delta) {
		// used to interpolate on paint
		oldPos.x = pos.x;
		oldPos.y = pos.y;
		float t = delta/1000f;
		
		vel.x = vel.x + acel.x * t;
		vel.y = vel.y + acel.y * t;
		
		pos.x = pos.x + vel.x * t;
		pos.y = pos.y + vel.y * t;
		
	}

	public void paint(Clock clock) {
		float alpha = clock.alpha();
		
		if (sprite != null) {
			sprite.paint(clock);
			
			sprite.layer.setTranslation(MathUtil.lerp(oldPos.x, pos.x, alpha), MathUtil.lerp(oldPos.y, pos.y, alpha));
		}
	}

	public Vector getAcel() {
		return acel;
	}

	public Point getPos() {
		return pos;
	}
	
	public void setPos(float x, float y){
		pos.x = x;
		pos.y = y;
		oldPos.x = x;
		oldPos.y = y;
	}

	public Vector getVel() {
		return vel;
	}

	public GameWorld getWorld() {
		return world;
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}	
	
	// Controllable
	public void moveUp(){
		Vector v = resetVertical();
		v.y = -speed;
		v.setLength(speed);
	}
	
	public void moveDown(){
		Vector v = resetVertical();
		v.y = speed;
		v.setLength(speed);
	}
	
	public void moveLeft(){
		Vector v = resetHorizontal();
		v.x = -speed;
		v.setLength(speed);
	}
	
	public void moveRight(){
		Vector v = resetHorizontal();
		v.x = speed;
		v.setLength(speed);
	}

	public void stopHorizontal(){
		resetHorizontal();
	}
	
	public void stopVertical(){
		resetVertical();
	}
	
	public void fire(){
		// NOP
	}
	
	@Override
	public Transform getTransform() {
		t.set(new Vec2(this.pos.x,this.pos.y), 0);
		
		return t;
	}
	
	private Vector resetHorizontal(){
		Vector v = getVel(); 
		v.x = 0f;
		if(v.length() > 0){
			v.normalizeLocal();
			v.setLength(speed);
		}
		
		return v;
	}
	
	private Vector resetVertical(){
		Vector v = getVel();
		v.y = 0f;
		if(v.length() > 0){
			v.normalizeLocal();
			v.setLength(speed);
		}
		
		return v;
	}

	public boolean isAlive() {
		return alive;
	}

	protected void setAlive(boolean alive) {
		this.alive = alive;
	}
		
	@Override
	public void collisionCallback(Collideable hit) {
		// TODO Auto-generated method stub
		
	}
}
