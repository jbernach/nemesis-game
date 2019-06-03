package com.superjose128.nemesis.core.powerup;

import com.superjose128.nemesis.core.actor.Player;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import playn.core.Clock;
import pythagoras.f.Point;

/**
 * A powerup is a characteristic that can be applied to a player (xtra speed, a weapon, a shield, etc.)
 * Can be increased with levels.
 * @author Joselito y Tere
 *
 */
public abstract class PowerUp {
	protected Player owner = null;

	protected String name = "-";
	protected AnimatedSprite sprite; // graphic appearance
	protected Point relativeToActorPos = new Point(0,0); // Position of whe sprite (relative to actor center)
	
	protected int level = 1;
	protected int maxLevels = 1;

	protected boolean enabled = true;

	/* Indicates it's an indispensable powerup and can't be disarmed, but disabled */
	protected boolean basic = false;

	/* No compatible powerups. Wen this powerup is armed, those included in this lists will be disarmed on disabled (if its a basic powerup) */
	protected String[] excludes = new String[0]; 
		
	private boolean spriteOnLayer = false; // indicates if the sprite has been attached to the world's actor layer

	protected PowerUp(){
	}
	
	/**
	 * Fired when the actor is armed with this powerup. 
	 * @param player
	 */
	public void onArmed(Player player){
		this.setOwner(player);
		
		// disable or disarm the excluded powerups
		disarmExcluded(player);
		disableBasicExcluded(player);
	}

	/**
	 * Fired when the actor loses this powerup.
	 */
	public void onDisarmed(){
		if(sprite != null){
			sprite.dispose();
		}
	}
	
	/**
	 * Fired when the actor who belongs the powerup make fire.
	 */
	public abstract void onFire();
	
	/**
	 * Fired after a level increase.
	 */
	public abstract void onLevelUp();
	
	/**
	 * Increase the level of this powerup by 1 unit.
	 */
	public void levelUp(){
		if(this.level < this.maxLevels){
			this.level++;
			onLevelUp();
		}	
	}
	
	/**
	 * Returns the player which this powerup belongs
	 * @return
	 */
	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player guy) {
		this.owner = guy;
	}

	/**
	 *  Powerup name.
	 * @return
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Current level.
	 * @return
	 */
	public int getLevel(){
		return this.level;
	}
	
	
	/**
	 *  Max number of levels this powerup supports.
	 * @return
	 */
	public int getMaxLevels(){
		return this.maxLevels;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if(this.sprite != null && this.sprite.layer != null) {
			sprite.layer.setVisible(enabled);
		}
	}

	public boolean isBasic() {
		return basic;
	}

	
	public String[] getExcludes() {
		return excludes;
	}

		
	public void paint(Clock clock, Point ownerPaintPosition) {
		if (sprite != null && owner != null) {
			if(!spriteOnLayer){
				owner.getWorld().getActorLayer().add(sprite.layer);
				spriteOnLayer = true;
			}
			
			if(this.isEnabled()){
				sprite.paint(clock);
				sprite.layer.setTranslation(ownerPaintPosition.x + relativeToActorPos.x,ownerPaintPosition.y + relativeToActorPos.y);
			}
		}
	}
	
	public void disarmExcluded(Player guy){
		// disable or disarm the excluded powerups
		if(excludes != null && excludes.length > 0){
			for(String name:excludes){
				PowerUp pup = guy.getArmedPowerUp(name);
				if(pup != null){
					if(!pup.isBasic()){						
						guy.disarmPowerUp(name);
					}
				}
			}
		}
	}

	// disable the basic excluded powerups
	public void disableBasicExcluded(Player guy){
		if(excludes != null && excludes.length > 0){
			for(String name:excludes){
				PowerUp pup = guy.getArmedPowerUp(name);
				if(pup != null){
					if(pup.isBasic()){						
						pup.setEnabled(false);
					}
				}
			}
		}
	}
}
