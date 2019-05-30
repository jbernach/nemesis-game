package com.superjose128.nemesis.core.actor;

import com.superjose128.nemesis.core.GameWorld;
import com.superjose128.nemesis.core.NemesisGame;
import com.superjose128.nemesis.core.collision.Collideable;
import com.superjose128.nemesis.core.collision.CollideableTypes;
import com.superjose128.nemesis.core.powerup.PowerUp;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import org.jbox2d.collision.shapes.Shape;
import playn.core.Clock;
import pythagoras.f.Point;

import java.util.HashMap;

/**
 * Player.
 * @author A132493
 *
 */
public abstract class Player extends Actor {
	private int lives = 3;
	private int score = 0;
	
	public boolean invulnerable = false; // siiii
	
	protected HashMap<String,PowerUp> powerUps = new HashMap<String,PowerUp>();

	public Player(NemesisGame game) {
		super(game);
	}

	public boolean hasPowerUp(String name){
		return (powerUps.get(name) != null);
	}
	
	public PowerUp armPowerUp(PowerUp powerUp){
		PowerUp pup = powerUps.get(powerUp.getName());
		
		if(pup != null){
			pup.levelUp();
		}else{
			pup = powerUp;
			powerUps.put(powerUp.getName(), powerUp);
			powerUp.onArmed(this);
		}
		
		return pup;
	}
	
	public PowerUp disarmPowerUp(String name){
		PowerUp powerUp = powerUps.get(name);
		
		if(powerUp != null){
			powerUps.remove(name);
			powerUp.onDisarmed();
			
			// a non basic powerup, when dissarmed, enables again the basic powerups it excluded when was armed
			String[] excludes = powerUp.getExcludes();
			if(excludes != null && excludes.length > 0){
				for(String excluded:excludes){
					PowerUp pup = getArmedPowerUp(excluded);
					if(pup != null){
						if(pup.isBasic()){
							pup.setEnabled(true);
						}
					}
				}
			}
		}
		
		return powerUp;
	}
	
	public PowerUp getArmedPowerUp(String name){
		return powerUps.get(name);
	}
	
	public void paint(Clock clock) {
		super.paint(clock);
		
		// PowerUp painting
		for(PowerUp poup:this.powerUps.values()){
			poup.paint(clock, new Point(this.sprite.layer.tx(),this.sprite.layer.ty()));
		}
	}
	
	@Override
	public void fire(){
		for(PowerUp poup:this.powerUps.values()){
			if(poup.isEnabled()){
				poup.onFire();
			}
			
		}
	}

	@Override
	public void die(){
		//remove Powerups
		PowerUp powerUpsArray[] = this.powerUps.values().toArray(new PowerUp[0]);
		for(PowerUp poup:powerUpsArray){
			disarmPowerUp(poup.getName());
		}

		super.die();
	}

	@Override
	public void collisionCallback(Collideable hit) {
		switch(hit.getType()){
			case ENEMY:
				if(invulnerable) return;
				// AY AY AY!!
				die();
				// enemy die
				((Actor)hit).die();
				break;
			case ENEMY_WEAPON:
				if(invulnerable) return;
				die();
				// OUUUUCH!
				break;
			case WALL:
				if(invulnerable) return;
				die();
				// CRASH!
				break;
			case POWERUP_CAPSULE:
				world.getWeaponSel1().increaseWeaponCoins();
				((Actor)hit).die();
				break;
			default: break;
		}
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void addScore(int score) {
		this.score += score;
	}

	@Override
	public AnimatedSprite initializeSprite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		if(pos.x < 0) pos.x = 0;
		if(pos.x > GameWorld.WORLD_WIDTH) pos.x = GameWorld.WORLD_WIDTH;
		if(pos.y < 0) pos.y = 0;
		if(pos.y > GameWorld.WORLD_HEIGHT) pos.y = GameWorld.WORLD_HEIGHT;
	}

	
}
