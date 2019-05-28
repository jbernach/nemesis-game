package com.superjose128.nemesis.core;

import static playn.core.PlayN.*;
import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.PlayN;
import playn.core.util.Callback;
import react.Signal;
import react.Slot;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIAnimScreen;
import tripleplay.sound.Clip;


public class LogoScreen extends UIAnimScreen {	
	public static final int DURATION = 2500; // 2,5 sec
	
	private final NemesisGame game;
	private final ScreenStack stack;
	
	private final Signal<Boolean> _completeSignal = new Signal<Boolean>();
	private ImageLayer _logo;
	
	public LogoScreen(ScreenStack stack, NemesisGame game){
		this.stack = stack;
		this.game = game;
		this._completeSignal.connect(new Slot<Boolean>(){
			public void onEmit(Boolean event) {
				LogoScreen.this.stack.push(new MainMenuScreen(LogoScreen.this.stack, LogoScreen.this.game));
			}
		});
	}
	
	@Override
	public void wasAdded() {
		super.wasAdded();
		
		Image imageLogo = assets().getImage("images/konami_logo.png");
		_logo = graphics().createImageLayer(imageLogo);
		
		// Preload of game sounds
		NemesisGame.soundsFx.loadAllSounds();
		
		imageLogo.addCallback(new Callback<Image>(){
			@Override
			public void onSuccess(Image result) {
				float anchoImagenDeseado = width()/4f;
		        float escalaImg = anchoImagenDeseado/result.width();
		                
		        _logo.setScale(escalaImg);
		        _logo.setOrigin(result.width()/2, result.height()/2);
		        _logo.setTranslation(width()/2, -_logo.height()/2);
		       
		        layer.add(_logo);
		        
		        anim.tweenY(_logo).to(height()/3f).in(DURATION).easeInOut().then().emit(_completeSignal, true);
			}

			@Override
			public void onFailure(Throwable cause) {
				PlayN.log().error("No se pudo cargar el logo.", cause);
				_completeSignal.emit(true); // Continue to next screen
			}
			
		});
	}

	@Override
    public void wasShown () {
    	super.wasShown();
  
    }
    
	@Override
	public void wasRemoved() {
		super.wasRemoved();
		
		layer.destroyAll();		
	}
	
	
}
