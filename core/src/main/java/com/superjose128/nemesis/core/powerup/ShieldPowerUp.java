package com.superjose128.nemesis.core.powerup;

import com.superjose128.nemesis.core.actor.Player;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import playn.core.Image;

public class ShieldPowerUp extends PowerUp {
	
	public ShieldPowerUp() {
		super();
		this.name = "SHIELD";
		
		this.maxLevels = 1;
		this.basic = false;
	}

	@Override
	public void onArmed(Player player) {
		super.onArmed(player);
		Image imgSprite = player.game().plat.assets().getImage("images/sprites/flame.png");
		this.sprite = new AnimatedSprite(imgSprite, 10, 6, 2, 500);
	}

	@Override
	public void onFire() {
		//NOP
	}

	@Override
	public void onLevelUp() {
		// NOP
	}

}
