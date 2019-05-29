package com.superjose128.nemesis.core.sound;

import tripleplay.sound.Clip;
import tripleplay.sound.Playable;
import tripleplay.sound.SoundBoard;

import java.util.HashMap;

public class GameSounds {
	private final SoundBoard sb;
	public final HashMap<String, Playable> sounds = new HashMap<String, Playable>();
	
	public GameSounds(){
		this.sb = new SoundBoard();
		this.sb.volume.update(0.5f); // Medio volumen
	}
	
	/**
	 * Preload of the most important sounds of the game, to be accesed throug {@link getSound}
	 */
	public void loadAllSounds(){
		loadClipSound("normalFire","sounds/normalFire");
		loadClipSound("pause","sounds/pause");
		loadClipSound("powerArm","sounds/powerArm");
		loadClipSound("powerCoin","sounds/powerCoin");
		loadClipSound("laser","sounds/laser");
		loadClipSound("explodeViper","sounds/explodeViper");
		loadClipSound("explode1","sounds/explode1");	}
	
	public void loadClipSound(String key, String path){
		Clip clip = sb.getClip(path);
		sounds.put(key, clip);
		clip.preload();
	}
	
	public Playable getSound(String key){
		return sounds.get(key);
	}
	
	public SoundBoard getSoundBoard(){
		return this.sb;
	}
	
}
