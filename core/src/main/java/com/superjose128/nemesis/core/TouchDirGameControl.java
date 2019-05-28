package com.superjose128.nemesis.core;

import com.superjose128.nemesis.core.powerup.WeaponSelectionModel;

import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;
import playn.core.PlayN;
import playn.core.Touch;
import pythagoras.f.Point;
import pythagoras.f.Vector;


public class TouchDirGameControl implements Touch.LayerListener{
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
	public void onTouchStart(Touch.Event touch) {
		switch(mode){
			case MODE_DIR:
				prev.x = touch.localX();
				prev.y = touch.localY();
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
	public void onTouchMove(Touch.Event touch) {
		switch(mode){
			case MODE_DIR:
				Point current = new Point(touch.localX(),touch.localY());			
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
	public void onTouchEnd(Touch.Event touch) {
		if(mode == MODE_DIR){
			player.stopHorizontal();
			player.stopVertical();
		}
	}

	@Override
	public void onTouchCancel(Touch.Event touch) {
		if(mode == MODE_DIR){
			player.stopHorizontal();
			player.stopVertical();
		}
	}
	
}
