package com.superjose128.nemesis.core;

import static playn.core.PlayN.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.superjose128.nemesis.core.actor.*;
import com.superjose128.nemesis.core.actor.enemies.*;
import com.superjose128.nemesis.core.collision.CollideableTypes;
import com.superjose128.nemesis.core.collision.Collision;
import com.superjose128.nemesis.core.collision.CollisionManager;
import com.superjose128.nemesis.core.powerup.*;

import playn.core.Connection;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.Surface;
import playn.core.SurfaceImage;
import playn.core.Touch;
import playn.core.Mouse;
import playn.core.util.Clock;
import pythagoras.f.Point;
import react.Slot;

/**
 * Represents a level inside the game.
 * The world works with a fixed size coordinate system that is scaled by the GameWorldScreen.
 * @author Joselito y Tere
 *
 */
public class GameWorld {	
	public final static int NATIVE_RES_WIDTH = 1280;
	public final static int NATIVE_RES_HEIGHT = 720;
	
	private final static int SCORE_AREA_HEIGHT = 80; // Height of the area reserved for lives, display score, powerups...
	public final static int WORLD_WIDTH = NATIVE_RES_WIDTH;
	public final static int WORLD_HEIGHT = NATIVE_RES_HEIGHT - SCORE_AREA_HEIGHT;
	
	private final GameWorldScreen gameScreen;
	
	private KeyboardGameControl keyControl;
	private final TouchDirGameControl[] touchControls = new TouchDirGameControl[3];
	private final Connection[] touchConnections= new Connection[3];

	private final GroupLayer controlPadLayer = graphics().createGroupLayer(NATIVE_RES_WIDTH,NATIVE_RES_WIDTH);
	private final Layer[] controlDirLayers = new Layer[3];

	private final GroupLayer backgroundLayer = graphics().createGroupLayer(NATIVE_RES_WIDTH,NATIVE_RES_WIDTH);
	private final GroupLayer scoreLayer = graphics().createGroupLayer(WORLD_WIDTH,SCORE_AREA_HEIGHT);
	private final GroupLayer actorLayer = graphics().createGroupLayer(WORLD_WIDTH,WORLD_HEIGHT);
	
	private WeaponBoard weaponBoard1;
	private Player player1 = null;
	private WeaponSelectionModel weaponSel1;
	private String currentLevel = "1";
	
	private final ArrayList<Actor> actors = new ArrayList<Actor>();
	
	private CollisionManager collisionManager = new CollisionManager();
	private LinkedList<Collision> collisions = new LinkedList<Collision>();
	
	boolean useScreenPad = false;
	
	public GameWorld(GameWorldScreen gameScreen){
		this.gameScreen = gameScreen;
		useScreenPad = touch().hasTouch();
		useScreenPad = true;
		
		this.gameScreen.layer.add(this.backgroundLayer);
		this.gameScreen.layer.add(this.actorLayer);
		scoreLayer.setTy(WORLD_HEIGHT); // To display the score layer under the actor layer, in the bottom of the screen
		
		this.gameScreen.layer.add(this.scoreLayer); // on top of control
		
		
		createScreenPad();
				
		init();
	}
		
	public void update(int delta) {
		Actor[] actorsArray = actors.toArray(new Actor[0]);
		
		for(Actor actor:actorsArray){	
			actor.update(delta);
		}
				
		removeDeadActors();
		
		CollideableTypes[][] collideablePairs;
		collideablePairs = new CollideableTypes[][]{
				{CollideableTypes.PLAYER_SHIELD, CollideableTypes.ENEMY_WEAPON},
				{CollideableTypes.PLAYER_SHIELD, CollideableTypes.ENEMY},
				{CollideableTypes.PLAYER_WEAPON, CollideableTypes.ENEMY},
				{CollideableTypes.PLAYER_WEAPON, CollideableTypes.WALL},
				{CollideableTypes.PLAYER, CollideableTypes.POWERUP_CAPSULE},
				{CollideableTypes.PLAYER, CollideableTypes.ENEMY_WEAPON},
				{CollideableTypes.PLAYER, CollideableTypes.ENEMY},
				{CollideableTypes.PLAYER, CollideableTypes.WALL},
				{CollideableTypes.ENEMY_WEAPON, CollideableTypes.WALL},
				{CollideableTypes.ENEMY, CollideableTypes.WALL}
				};
	
		for(CollideableTypes[] pair:collideablePairs){
			collisionManager.calculateCollisions(pair[0], pair[1], collisions);
		}
		
		collisionManager.processCollisions(collisions);
		
		removeDeadActors();
		
		this.weaponSel1.update(delta); // update score, lives, etc
		//log().debug("actors: " + actors.size());
	}

	public void paint(Clock clock) {
		weaponBoard1.paint(clock);
		
		for(Iterator<Actor> it = actors.iterator();it.hasNext();){
			it.next().paint(clock);
		}
	}
	
	private void init(){
		initPlayer();
		initWeaponBoard();
		initIO();	
	}
	
	private void initIO(){		
		pointer().setListener(null);
		keyboard().setListener(null);
		
		keyControl = new KeyboardGameControl(this.getPlayer1(), this.getWeaponSel1(),this);
			
		keyboard().setListener(keyControl);
		
		if(useScreenPad){
			for(Connection conn:touchConnections){
				if(conn != null){
					conn.disconnect();
				}
			}
			
			for(int i = 0;i < touchControls.length;i++){
				touchControls[i] = new TouchDirGameControl(i,this.getPlayer1(),this.getWeaponSel1());
				touchConnections[i] = controlDirLayers[i].addListener((Touch.LayerListener)touchControls[i]);
			}
		}
			
	}
	
	private void createScreenPad(){
		if(!useScreenPad) return;
		this.gameScreen.layer.add(this.controlPadLayer);
		
		Image imgPad = assets().getImage("images/dir_pad.png");
		
		controlDirLayers[0] = graphics().createImageLayer(imgPad);
		controlDirLayers[0].setTranslation(0,0);
		controlDirLayers[0].setAlpha(0.1f);
		controlPadLayer.add(controlDirLayers[0]);
		
		Image imgButton = assets().getImage("images/button_pad.png");
		// fire
		controlDirLayers[1] = graphics().createImageLayer(imgButton);
		controlDirLayers[1].setOrigin(90, 90);
		controlDirLayers[1].setTranslation(920,620);
		controlDirLayers[1].setAlpha(0.1f);
		controlPadLayer.add(controlDirLayers[1]);
		
		controlDirLayers[2] = graphics().createImageLayer(imgButton);
		controlDirLayers[2].setOrigin(90, 90);
		controlDirLayers[2].setTranslation(1130,620);
		controlDirLayers[2].setAlpha(0.1f);
		controlPadLayer.add(controlDirLayers[2]);
		
	}
	
	private void initWeaponBoard(){
		weaponBoard1 = new WeaponBoard(WORLD_WIDTH, SCORE_AREA_HEIGHT, 10);
		weaponSel1 = new WeaponSelectionModel(player1, weaponBoard1);
		
		weaponSel1.addSelectablePowerUp(new SpeedPowerUp());
		weaponSel1.addSelectablePowerUp(new MissiliePowerUp());
		weaponSel1.addSelectablePowerUp(new DoublePowerUp());
		weaponSel1.addSelectablePowerUp(new LaserPowerUp());
		weaponSel1.addSelectablePowerUp(new OptionPowerUp());
		weaponSel1.addSelectablePowerUp(new ShieldPowerUp());
		
		scoreLayer.add(graphics().createImageLayer(weaponBoard1.surfaceImg));
	}
	
	private void initPlayer(){
		Player deadPlayer = player1;
		
		player1 = new PlayerMetallion();
		if(deadPlayer != null){
			player1.setLives(deadPlayer.getLives() - 1);
			player1.setScore(deadPlayer.getScore());
		}
		
		player1.alive.connect(new Slot<Boolean>(){
			@Override
			public void onEmit(Boolean event) {
				if(!event.booleanValue()){
					if(player1.getLives() == 0){
						GameWorld.this.gameOver();
					}else{
						initPlayer();
						initIO();
						weaponSel1.setPlayer(player1);
						loadLevel(currentLevel);
					}
				}
			}
		});		
	}
	
	public void loadLevel(String level){
		currentLevel = level;
		clearLevel();
		player1.addToWorld(this);
		// TODO: load level
		SurfaceImage baseBackground = graphics().createSurface(NATIVE_RES_WIDTH, NATIVE_RES_HEIGHT);
		Surface surfaceB = baseBackground.surface();
		//surfaceB.setFillColor(0xFF2B76FF);
		surfaceB.setFillColor(0xFF000000);
		surfaceB.fillRect(0, 0, NATIVE_RES_WIDTH, NATIVE_RES_HEIGHT);
		backgroundLayer.add(graphics().createImageLayer(baseBackground));
		
		createTestStuff();
    	
	}
	
	private void createTestStuff(){
		for(int i = 0;i < 20;i++){
	    	Enemy enemy = new Barrel();
	    	enemy.setPos(GameWorld.WORLD_WIDTH/2 + PlayN.random() * GameWorld.WORLD_WIDTH/2, PlayN.random() * GameWorld.WORLD_HEIGHT);
	    	enemy.setSpeed(100*PlayN.random());
	    	enemy.moveLeft();
	    	enemy.addToWorld(this);
    	}
    	
    	for(int i = 0;i < 10;i++){
    		PowerUpCapsule capsule = new PowerUpCapsule(new Point( GameWorld.WORLD_WIDTH/2 + PlayN.random() * GameWorld.WORLD_WIDTH/2, PlayN.random() * GameWorld.WORLD_HEIGHT)); 
    		capsule.addToWorld(this);
    	}
	}
	
	public void destroy(){
		
	}
	
	public void gameOver(){
		
		gameScreen.hide();
	}
	
	private void clearLevel(){
		backgroundLayer.destroyAll();
		actorLayer.destroyAll();
		actors.clear();
		collisions.clear();
		collisionManager = new CollisionManager();
		weaponSel1.setWeaponCoins(0);
		
	}

	private void removeDeadActors(){
		Actor[] actorsArray = actors.toArray(new Actor[0]);
		
		for(Actor actor:actorsArray){
			if(!actor.isAlive()){
				actors.remove(actor);
				collisionManager.removeCollideable(actor);
			}
		}
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player) {
		this.player1 = player;
	}
	
	public GroupLayer getActorLayer(){
		return actorLayer; 
	}
	
	public WeaponSelectionModel getWeaponSel1() {
		return weaponSel1;
	}

	/**
	 * Add an actor from the world. Not use directly, use Actor.addToWorld.
	*/
	public void addActor(Actor actor){
		actors.add(actor);
		collisionManager.addCollideable(actor);
	}
	
}