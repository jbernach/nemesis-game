package com.superjose128.nemesis.core.powerup;

import com.superjose128.nemesis.core.actor.Player;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import playn.core.Image;
import pythagoras.f.Point;

public class SpeedPowerUp extends PowerUp {
	public final static String NAME = "SPEED";

	private float initialSpeed = 320f;
	private float speedInc = 60f;
	private float initialBlinkMs = 250f; // Initial interval of flame blink
	private float blinkMsInc = - 20f; // On each level the blame blinks faster
	
	public SpeedPowerUp() {
		super();
		this.name = NAME;

		this.relativeToActorPos = new Point(-92/2-20+5,-1);
		this.maxLevels = 9;
		this.basic = true; 
	}

	@Override
	public void onArmed(Player player) {
		super.onArmed(player);
		player.setSpeed(initialSpeed);

		Image imgSprite = player.game().plat.assets().getImage("images/sprites/flame.png");
		this.sprite = new AnimatedSprite(imgSprite, 20, 12, 2, initialBlinkMs);
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

	public void resetSpeed () {
		this.level = 1;
		onArmed(this.owner);
	}
}
