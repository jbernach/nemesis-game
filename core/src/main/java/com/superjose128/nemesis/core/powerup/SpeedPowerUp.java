package com.superjose128.nemesis.core.powerup;

import com.superjose128.nemesis.core.actor.Player;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import playn.core.Image;
import pythagoras.f.Point;

public class SpeedPowerUp extends PowerUp {
	private float initialSpeed = 320f;
	private float speedInc = 60f;
	private float initialBlinkMs = 250f; // Initial interval of flame blink
	private float blinkMsInc = - 20f; // On each level the blame blinks faster
	
	public SpeedPowerUp() {
		super();
		this.name = "SPEED";
		Image imgSprite = assets().getImage("images/sprites/flame.png");
		this.sprite = new AnimatedSprite(imgSprite, 20, 12, 2, initialBlinkMs);
		this.relativeToActorPos = new Point(-92/2-20+5,-1);
		
		this.maxLevels = 9;
		this.basic = true; 
	}

	@Override
	public void onFire() {
		//NOP
		
	}

	@Override
	public void onLevelUp() {
		this.owner.setSpeed(initialSpeed + (this.level - 1)*speedInc);
		this.sprite.setMsPerFrame(initialBlinkMs + (this.level - 1)*blinkMsInc);
	}

	@Override
	public void onArmed(Player guy) {
		// TODO Auto-generated method stub
		super.onArmed(guy);
		
		guy.setSpeed(initialSpeed);
	}

}
