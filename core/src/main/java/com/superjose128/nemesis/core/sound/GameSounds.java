package com.superjose128.nemesis.core.sound;

import playn.core.Clock;
import playn.core.Platform;
import react.Signal;
import tripleplay.sound.Clip;
import tripleplay.sound.Playable;
import tripleplay.sound.SoundBoard;

import java.util.HashMap;

public class GameSounds {
	private final Platform platform;
	private final SoundBoard sb;
	public final HashMap<String, Playable> sounds = new HashMap<>();
	
	public GameSounds(Platform platform, Signal<Clock> paintclock){
		this.platform = platform;
		this.sb = new SoundBoard(platform, paintclock);
		this.sb.volume.update(0.5f); // Medio volumen
		loadAllSounds();
	}
	
	/**
	 * Preload of the most important sounds of the game
	 */
	private void loadAllSounds(){
		platform.log().info("Loading sounds...");
		loadClipSound("normalFire","sounds/normalFire");
		loadClipSound("pause","sounds/pause");
		loadClipSound("powerArm","sounds/powerArm");
		loadClipSound("powerCoin","sounds/powerCoin");
		loadClipSound("laser","sounds/laser");
		loadClipSound("explodeViper","sounds/explodeViper");
		loadClipSound("explode1","sounds/explode1");	}
	
	private void loadClipSound(String key, String path){
		Clip clip = sb.getClip(path);
		sounds.put(key, clip);
		clip.preload();
	}
	
	public Playable get(String key){
		return sounds.get(key);
	}
	
	public SoundBoard getSoundBoard(){
		return this.sb;
	}
	
}
