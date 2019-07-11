package com.superjose128.nemesis.core.powerup;

import com.superjose128.nemesis.core.NemesisGame;
import com.superjose128.nemesis.core.actor.Player;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;

public class OptionPowerUp extends PowerUp {
	
	public OptionPowerUp(NemesisGame game) {
		super(game);
		this.name = "OPTION";

		this.maxLevels = 2;
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
