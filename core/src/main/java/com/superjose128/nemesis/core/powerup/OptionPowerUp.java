package com.superjose128.nemesis.core.powerup;

import com.superjose128.nemesis.core.actor.Player;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import playn.core.Image;

public class OptionPowerUp extends PowerUp {
	
	public OptionPowerUp() {
		super();
		this.name = "OPTION";

		this.maxLevels = 2;
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
