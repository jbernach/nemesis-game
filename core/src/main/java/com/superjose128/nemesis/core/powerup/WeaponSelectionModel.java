package com.superjose128.nemesis.core.powerup;

import java.util.Vector;

import playn.core.PlayN;
import tripleplay.sound.Clip;

import com.superjose128.nemesis.core.NemesisGame;
import com.superjose128.nemesis.core.actor.Player;

public class WeaponSelectionModel {
	private Player player;
	private WeaponBoard weaponBoard;
	private int weaponCoins = 0; // Number of capsules collected (to get weapons)
	private Vector<PowerUp> availablePowerUps = new Vector<PowerUp>();
	
	final static private Clip powerCoinUpSound = (Clip)NemesisGame.soundsFx.getSound("powerCoin");
	final static private Clip powerArmSound = (Clip)NemesisGame.soundsFx.getSound("powerArm");
	
	
	public WeaponSelectionModel(Player player, WeaponBoard weaponBoard){
		this.player = player;
		this.weaponBoard = weaponBoard;
	}
	
	public void addSelectablePowerUp(PowerUp powerUp){
		availablePowerUps.addElement(powerUp);
		WeaponBoardSlot slot = new WeaponBoardSlot(powerUp);
		this.weaponBoard.addSlot(slot);
	}
	
	private void onWeaponCoinChange(){
		if(weaponCoins > availablePowerUps.size()){
			weaponCoins = 1;
		}
		
		weaponBoard.selectSlot(weaponCoins - 1);
	}
	
	public void setWeaponCoins(int coins) {
		this.weaponCoins = coins;
		this.onWeaponCoinChange();
	}

	public void resetWeaponCoins() {
		this.weaponCoins = 0;
		this.onWeaponCoinChange();
	}
	
	public void increaseWeaponCoins(){
		this.weaponCoins++;
		synchronized(powerCoinUpSound){		
			if(powerCoinUpSound.isPlaying()) powerCoinUpSound.stop(); 
			powerCoinUpSound.play();
		}
		
		this.onWeaponCoinChange();
	}
	
	public void selectCurrent(){
		PowerUp pup = weaponBoard.getReadyPowerUp();
		
		if(pup != null){
			pup = player.armPowerUp(pup);
			
			synchronized(powerArmSound){		
				if(powerArmSound.isPlaying()) powerArmSound.stop(); 
				powerArmSound.play();
			}
			
			weaponBoard.onWeaponArm(pup);
			
			resetWeaponCoins();
		}
		
	}
	
	public void update(int delta) {
		weaponBoard.setLives(player.getLives());
		weaponBoard.setScore(player.getScore());
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
