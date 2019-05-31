package com.superjose128.nemesis.core.actor.enemies;

import com.superjose128.nemesis.core.NemesisGame;
import com.superjose128.nemesis.core.collision.Collideable;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import playn.core.Image;

public class Barrel extends Enemy {
    private final PolygonShape shape = new PolygonShape();

    public Barrel(NemesisGame game) {
        super(game);

        shape.setAsBox(18, 32); // half size of the sprite size
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    @Override
    public AnimatedSprite initializeSprite() {
        Image imgSprite = this.game.plat.assets().getImage("images/sprites/barrells.png");
        AnimatedSprite sp = new AnimatedSprite(imgSprite, 64, 64, 4, 100);

        return sp;
    }

}
