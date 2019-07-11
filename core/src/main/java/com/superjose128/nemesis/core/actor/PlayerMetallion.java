package com.superjose128.nemesis.core.actor;

import com.superjose128.nemesis.core.GameWorld;
import com.superjose128.nemesis.core.collision.CollideableTypes;
import com.superjose128.nemesis.core.powerup.*;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import tripleplay.sound.Playable;


public class PlayerMetallion extends Player {
    final static int ROW_NORMAL = 0;
    final static int ROW_UP = 1;
    final static int ROW_DOWN = 2;

    private static final PolygonShape shape = new PolygonShape();

    public PlayerMetallion(GameWorld world) {
        super(world);

        this.setPos(GameWorld.WORLD_WIDTH / 3f, GameWorld.WORLD_HEIGHT / 2f);

        initializePowerUps();

        shape.setAsBox(46, 16);
    }

    @Override
    public void initializePowerUps() {
        PowerUp pup = new SpeedPowerUp(game);
        powerUps.put(pup.getName(), pup);
        pup.onArmed(this);
        pup = new BasicFirePowerUp(game);
        powerUps.put(pup.getName(), pup);
        pup.onArmed(this);
    }

    @Override
    public void moveUp() {
        if (this.sprite.getBank() == 0) {
            this.sprite.setFrame(0);
        }

        this.sprite.setBank(PlayerMetallion.ROW_UP);
        super.moveUp();
    }

    @Override
    public void moveDown() {
        if (this.sprite.getBank() == 0) {
            this.sprite.setFrame(0);
        }

        this.sprite.setBank(PlayerMetallion.ROW_DOWN);
        super.moveDown();
    }

    @Override
    public void stopVertical() {
        this.sprite.setBank(0);
        this.sprite.setFrame(0);
        super.stopVertical();
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    @Override
    public CollideableTypes getType() {
        return CollideableTypes.PLAYER;
    }

    @Override
    public void initializeSprite() {
        AnimatedSprite sp = new AnimatedSprite(game.images.get("metallion"), 92, 64, 2, 600);
        sp.loop = false;

        this.sprite = sp;
    }

    @Override
    public void die() {
        this.setVisible(false);
        //remove Powerups
        PowerUp[] powerUpsArray = this.powerUps.values().toArray(new PowerUp[0]);
        for(PowerUp poup:powerUpsArray){
            this.disarmPowerUp(poup.getName());
        }

        Explosion explosion = new Explosion(this.world, this.getPos(), 2000) {
            @Override
            public void initializeSprite() {
                AnimatedSprite sp = new AnimatedSprite(game.images.get("explode_viper"), 128, 56, 5, 400);
                sp.loop = false;

                this.sprite = sp;
            }

            @Override
            public Playable getSound() {
                return this.game.soundsFx.get("explodeViper");
            }
        };

        getWorld().addActor(explosion);
        explosion.alive.connect(alive -> {
           if(!alive) {
               super.die();
           }
        });
    }
}
