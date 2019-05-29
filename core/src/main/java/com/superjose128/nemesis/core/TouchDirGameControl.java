package com.superjose128.nemesis.core;

import com.superjose128.nemesis.core.powerup.WeaponSelectionModel;
import playn.scene.Touch;
import pythagoras.f.Point;

public class TouchDirGameControl extends Touch.Listener {
	private Controllable player;
	private WeaponSelectionModel selectionModel;
	
	public static final int MODE_DIR = 0;
	public static final int MODE_FIRE = 1;
	public static final int MODE_SELECT = 2;
	
	private int mode = MODE_DIR;
	
	Point prev = new Point();
	Point move = new Point();
	float thresholdMin = 30;
	float thresholdMax = 70;
	
	
	public TouchDirGameControl(int mode, Controllable player, WeaponSelectionModel selectionModel){
		this.mode = mode;
		this.player = player;
		this.selectionModel = selectionModel;
	}

	@Override
	public void onStart(Touch.Interaction interaction) {
		switch(mode){
			case MODE_DIR:
				prev.x = interaction.x();
				prev.y = interaction.y();
				break;
			case MODE_FIRE:
				player.fire();
				break;
			case MODE_SELECT:
				if(selectionModel != null){
		    		selectionModel.selectCurrent();
		    	}
				break;
		}
	}

	@Override
	public void onMove(Touch.Interaction interaction) {
		switch(mode){
			case MODE_DIR:
				Point current = new Point(interaction.x(),interaction.y());
				current.subtract(prev, move);
				
				if(move.x() > -thresholdMin && move.x () < thresholdMin){
					player.stopHorizontal();
				}else if(move.x() > thresholdMin && move.x() < thresholdMax){
					player.moveRight();
				}else if(move.x() < -thresholdMin && move.x() > -thresholdMax){
					player.moveLeft();
				}
				
				if(move.y() > -thresholdMin && move.y () < thresholdMin){
					player.stopVertical();
				}else if(move.y() > thresholdMin && move.y() < thresholdMax){
					player.moveDown();
				}else if(move.y() < -thresholdMin && move.y() > -thresholdMax){
					player.moveUp();
				}
				
				break;
			case MODE_FIRE:
				player.fire();
				break;
			case MODE_SELECT:
				if(selectionModel != null){
		    		selectionModel.selectCurrent();
		    	}
				break;
		}
	}

	@Override
	public void onEnd(Touch.Interaction interaction) {
		if(mode == MODE_DIR){
			player.stopHorizontal();
			player.stopVertical();
		}
	}

	@Override
	public void onCancel(Touch.Interaction interaction) {
		if(mode == MODE_DIR){
			player.stopHorizontal();
			player.stopVertical();
		}
	}
	
}
