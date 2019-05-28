package com.superjose128.nemesis.core;

import static playn.core.PlayN.*;
import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Font;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Keyboard;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.Surface;
import playn.core.SurfaceImage;
import playn.core.TextFormat;
import playn.core.TextLayout;
import playn.core.Pointer.Event;
import playn.core.util.Callback;
import tripleplay.anim.AnimBuilder;
import tripleplay.anim.Animation.Action;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIAnimScreen;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.Stylesheet;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.util.Colors;


public class MainMenuScreen extends UIAnimScreen {	

	private final NemesisGame game;
	private final ScreenStack stack;
	
	private ImageLayer title;
	private ImageLayer logoLayer;
	
	final private int NUM_LABEL_START_FLASH = 10;
	
	public MainMenuScreen(ScreenStack stack, NemesisGame game){
		this.game = game;
		this.stack = stack;
	}
	
	@Override
	public void wasAdded() {
		super.wasAdded();
		
		Font font = graphics().createFont("Nemesis", playn.core.Font.Style.PLAIN, 32);
		TextFormat textFormat = new TextFormat(font,false);
		SurfaceImage surfaceImg = graphics().createSurface(GameWorld.NATIVE_RES_WIDTH, GameWorld.NATIVE_RES_HEIGHT);
		Surface surface = surfaceImg.surface();
		CanvasImage buffer = graphics().createImage(GameWorld.NATIVE_RES_WIDTH, GameWorld.NATIVE_RES_HEIGHT);
		Canvas canvas = buffer.canvas();
		canvas.setFillColor(Colors.WHITE);
		TextLayout textLayout = graphics().layoutText("Press to play", textFormat);
		canvas.fillText(textLayout,GameWorld.NATIVE_RES_WIDTH/2  - 130, 2*GameWorld.NATIVE_RES_HEIGHT/3f);
		surface.drawImage(buffer, 0, 0);	
			
		logoLayer = graphics().createImageLayer(surfaceImg);
		float scaleW = width()/GameWorld.NATIVE_RES_WIDTH;
		float scaleH = height()/GameWorld.NATIVE_RES_HEIGHT;		
		float scale = Math.min(scaleW, scaleH);
		logoLayer.setScale(scale);	
		
		Image imageTitle = assets().getImage("images/title.png");
    	title = graphics().createImageLayer(imageTitle);
    	title.setTranslation(width()/2, height()/3);
    	
    	imageTitle.addCallback(new Callback<Image>(){
			@Override
			public void onSuccess(Image result) {
				float anchoImagenDeseado = width()/2f;
		        float escalaImg = anchoImagenDeseado/result.width();
		        title.setScale(escalaImg);
		        
		        title.setOrigin(result.width()/2, result.height()/2);
		        layer.add(title);
		        layer.add(logoLayer);
			}

			@Override
			public void onFailure(Throwable cause) {
				PlayN.log().error("No se pudo cargar la imagen del titulo.", cause);
				stack.remove(MainMenuScreen.this);
			}
    		
    	});
        
			
	}

	@Override
    public void wasShown () {
    	super.wasShown();
    	captureKeyboardAndPointer();
    }
	
	@Override
	public void wasHidden() {
		super.wasHidden();
	}

	@Override
	public void wasRemoved() {
		super.wasRemoved();
		
		layer.destroyAll();
	}

	private void startNewGame(){
		GameWorldScreen gameWorlScreen = new GameWorldScreen(this.stack, this.game);
		this.stack.push(gameWorlScreen);
		gameWorlScreen.beginGame();
	}
	
	private void captureKeyboardAndPointer(){
		pointer().setListener(new Pointer.Adapter() {
			@Override
			public void onPointerStart(Event event) {
				flashStart();
			}
		});
		
		keyboard().setListener(new Keyboard.Adapter() {
            @Override public void onKeyDown (Keyboard.Event event) {
            	flashStart();
            }
		});
		
	}
	
	private void flashStart(){
		AnimBuilder anim = iface.animator().repeat(logoLayer);
		Action action = anim.delay(100).then().action(new Runnable() {
            int cycle = 0;
            boolean started = false;
            
            @Override public void run() {
            	if(cycle < NUM_LABEL_START_FLASH){
            		if(cycle++ % 2 == 0){
            			logoLayer.setAlpha(0);
            		}else{
            			logoLayer.setAlpha(1);
            		}
            		
            	}else if(!started){
            		
	        		// START!
	        		started = true;
	        		startNewGame();
            	}else{
            		iface.animator().clear();
            	}
            }
        });
		
	}
	
}
