package com.superjose128.nemesis.core.actor.enemies;

import com.superjose128.nemesis.core.GameWorld;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import playn.core.Image;

public class Chiquis extends Enemy {
    private final PolygonShape shape = new PolygonShape();

    public Chiquis(GameWorld world) {
        super(world);

        shape.setAsBox(32, 32); // half size of the sprite size
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    @Override
    public void initializeSprite() {
        Image imgSprite = game.plat.assets().getImage("images/sprites/chiquis.png");
        AnimatedSprite sp = new AnimatedSprite(imgSprite, 64, 64, 1, 0);

        this.sprite = sp;
    }

}
