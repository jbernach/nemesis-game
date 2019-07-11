package com.superjose128.nemesis.core.actor.enemies;

import com.superjose128.nemesis.core.GameWorld;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;

public class Barrel extends Enemy {
    private final PolygonShape shape = new PolygonShape();

    public Barrel(GameWorld world) {
        super(world);

        shape.setAsBox(18, 32); // half size of the sprite size
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    @Override
    public void initializeSprite() {
        this.sprite = new AnimatedSprite(this.game.images.get("barrel"), 64, 64, 4, 100);

    }

}
