package com.superjose128.nemesis.core.powerup;

import playn.core.Canvas;
import playn.core.Surface;
import tripleplay.util.Colors;

public class WeaponBoardSlot {
	protected WeaponBoard board;
	private PowerUp powerup;
	private boolean current = false;
	private boolean disabled = false;
	protected int row = 0;
	protected int col = 0;
	
	public WeaponBoardSlot(PowerUp pup){
		this.powerup = pup;
	}
	
	public String getName() {
		if(powerup != null){
			return powerup.getName();
		}else{
			return "";
		}
	}

	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}
	
	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	public void paint(Surface surface) {
		if(board == null) return;
		
		float margin = board.getChooserHMargin();
		float w = (board.getChooserWidth() - margin )/board.getCols();
		float h = board.getChooserHeight()/board.getRows();
		
		float x = col*w + margin;
		float y = row*h;

		Canvas canvas = board.plat().graphics().createCanvas(w, h);
		/*canvas.setFillColor(Colors.BLACK);
		canvas.fillRect(0, 0, w, h);*/
		
		if(this.isDisabled()){
			canvas.setStrokeWidth(2);
			canvas.setStrokeColor(Colors.RED);
			canvas.strokeRect(5, 5, w - 10, h - 10);
		}else{
			if(this.isCurrent()){
				canvas.setFillColor(Colors.YELLOW);
				canvas.fillRect(5, 5, w-10, h-10);
				canvas.setStrokeColor(Colors.WHITE);
				canvas.setStrokeWidth(2);
				canvas.drawLine(5, 5, w-5, 5);
				canvas.drawLine(5, h-5, w-5, h-5);
			}else{
				canvas.setFillColor(Colors.BLUE);
				canvas.fillRect(5, 5, w-10, h-10);
				canvas.setStrokeColor(Colors.CYAN);
				canvas.setStrokeWidth(2);
				canvas.drawLine(5, 5, w-5, 5);
				canvas.drawLine(5, h-5, w-5, h-5);	
			}
		}
							
		surface.draw(canvas.snapshot().tile(), x, y);
	}

	public PowerUp getPowerup() {
		return powerup;
	}		
}
