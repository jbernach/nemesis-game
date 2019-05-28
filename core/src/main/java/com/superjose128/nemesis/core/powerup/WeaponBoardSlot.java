package com.superjose128.nemesis.core.powerup;

import static playn.core.PlayN.graphics;
import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Surface;
import playn.core.util.Clock;
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
	
	public void paint(Clock clock) {
		if(board == null) return;
		
		float margin = board.getChooserHMargin();
		float w = (board.getChooserWidth() - margin )/board.getCols();
		float h = board.getChooserHeight()/board.getRows();
		
		float x = col*w + margin;
		float y = row*h;
		
		Surface surface = board.surfaceImg.surface();
		CanvasImage buffer = graphics().createImage(w, h);
		Canvas canvas = buffer.canvas();
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
							
		surface.drawImage(buffer, x, y);			
	}

	public PowerUp getPowerup() {
		return powerup;
	}		
}
