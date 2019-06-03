package com.superjose128.nemesis.core;

import com.superjose128.nemesis.core.actor.enemies.Chiquis;
import com.superjose128.nemesis.core.actor.enemies.Enemy;
import com.superjose128.nemesis.core.actor.weapons.PowerUpCapsule;
import com.superjose128.nemesis.core.powerup.WeaponSelectionModel;
import playn.core.Key;
import playn.core.Keyboard;
import pythagoras.f.Point;

import java.util.HashMap;

public class KeyboardGameControl extends Keyboard.KeySlot {
    private Controllable player;
    private WeaponSelectionModel selectionModel;
    private GameWorld gameWorld; // for use in tricks
    private HashMap<Key, String> keysDown = new HashMap<Key, String>();

    public KeyboardGameControl(Controllable player, WeaponSelectionModel selectionModel, GameWorld world) {
        this.player = player;
        this.selectionModel = selectionModel;
        this.gameWorld = world;
    }

    public void onKeyDown(Keyboard.KeyEvent event) {
        Key k = event.key;
        keysDown.put(k, "1");
        //log().debug(""+k+" down");

        switch (k) {
            case LEFT:

                player.moveLeft();
                break;
            case RIGHT:
                player.moveRight();
                break;
            case UP:
                player.moveUp();
                break;
            case DOWN:
                player.moveDown();
                break;
            case SPACE:
                player.fire();
                break;

            // Tricks
            case M:
                if (selectionModel != null) {
                    selectionModel.selectCurrent();
                }
                break;
            case U:
                if (selectionModel != null) {
                    selectionModel.increaseWeaponCoins();
                }
                break;
            case C:
                PowerUpCapsule capsule = new PowerUpCapsule(gameWorld, new Point(GameWorld.WORLD_WIDTH / 2 + (float)Math.random() * GameWorld.WORLD_WIDTH / 2, (float)Math.random() * GameWorld.WORLD_HEIGHT));
                gameWorld.addActor(capsule);
                break;
            case E:
                for (int i = 0; i < 20; i++) {
                    Enemy enemy = new Chiquis(this.gameWorld);
                    enemy.setPos(GameWorld.WORLD_WIDTH / 2 + (float)Math.random() * GameWorld.WORLD_WIDTH / 2, (float)Math.random() * GameWorld.WORLD_HEIGHT);
                    enemy.setSpeed(100 * (float)Math.random());
                    enemy.moveLeft();
                    gameWorld.addActor(enemy);
                }
                break;
            default:
                break;
        }
    }

    public void onKeyUp(Keyboard.KeyEvent event) {
        Key k = event.key;
        keysDown.remove(k);

        switch (k) {
            case LEFT:
                if (keysDown.get(Key.RIGHT) == null) {
                    player.stopHorizontal();
                    //log().debug(""+k+" Up: stopH");
                } else {
                    player.moveRight();
                }
                break;
            case RIGHT:
                if (keysDown.get(Key.LEFT) == null) {
                    player.stopHorizontal();
                    //log().debug(""+k+" Up: stopH");
                } else {
                    player.moveLeft();
                }
                break;
            case UP:
                if (keysDown.get(Key.DOWN) == null) {
                    player.stopVertical();
                } else {
                    player.moveDown();
                }
                break;
            case DOWN:
                if (keysDown.get(Key.UP) == null) {
                    player.stopVertical();
                } else {
                    player.moveUp();
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onEmit(Keyboard.KeyEvent event) {
        if (event.down) {
            onKeyDown(event);
        } else {
            onKeyUp(event);
        }

    }

    public Controllable getPlayer() {
        return player;
    }

    public void setPlayer(Controllable player) {
        this.player = player;
    }
}
