package com.superjose128.nemesis.core.powerup;

import com.superjose128.nemesis.core.actor.Player;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import playn.core.Image;

import static playn.core.PlayN.assets;

public class OptionPowerUp extends PowerUp {
	
	public OptionPowerUp() {
		super();
		this.name = "OPTION";
		Image imgSprite = assets().getImage("images/sprites/flame.png");
		this.sprite = new AnimatedSprite(imgSprite, 10, 6, 2, 500);
		
		this.maxLevels = 2;
		this.basic = false;
	}

	@Override
	public void onFire() {
		//NOP
		
	}

	@Override
	public void onLevelUp() {
		// TODO
	}

	@Override
	public void onArmed(Player guy) {
		// TODO Auto-generated method stub
		super.onArmed(guy);
		
		
	}

}
