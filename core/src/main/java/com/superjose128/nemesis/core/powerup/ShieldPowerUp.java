package com.superjose128.nemesis.core.powerup;

import com.superjose128.nemesis.core.NemesisGame;
import com.superjose128.nemesis.core.actor.Player;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;

public class ShieldPowerUp extends PowerUp {
	
	public ShieldPowerUp(NemesisGame game) {
		super(game);
		this.name = "SHIELD";
		
		this.maxLevels = 1;
		this.basic = false;

		this.sprite = new AnimatedSprite(game.images.get("flame"), 10, 6, 2, 500);
	}

	@Override
	public void onArmed(Player player) {
		super.onArmed(player);
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
