package com.superjose128.nemesis.core.actor;

import com.superjose128.nemesis.core.Controllable;
import com.superjose128.nemesis.core.GameWorld;
import com.superjose128.nemesis.core.NemesisGame;
import com.superjose128.nemesis.core.collision.Collideable;
import com.superjose128.nemesis.core.sprites.AnimatedSprite;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import playn.core.Clock;
import playn.scene.Layer;
import pythagoras.f.MathUtil;
import pythagoras.f.Point;
import pythagoras.f.Vector;
import react.Signal;

/**
 * An actor inside the game (player, enemy, bullet, etc).
 * Belongs to a world, has a animated image representation, a position, velocity and acceleration.
 *
 * @author Joselito y Tere
 */
public abstract class Actor implements Controllable, Collideable {
    protected final NemesisGame game;
    public Signal<Boolean> alive = new Signal<>();
    private boolean isAlive;
    private boolean visible = true;

    protected AnimatedSprite sprite; // Actor animated character on screen
    protected Point oldPos = new Point(0f, 0f); // Old position
    protected Point pos = new Point(0f, 0f); // Current position (px world)
    protected Vector vel = new Vector(0f, 0f); // Velocity (px world / sec)
    protected Vector acel = new Vector(0f, 0f); // Acceleration (px worl / sec*sec)
    protected GameWorld world; // Nivel en el que se encuentra el actor

    protected float speed = 0; // speed magnitude (world px/sec) for control

    protected final Transform t = new Transform();

    public Actor(GameWorld world) {
        this.game = world.game();
        this.world = world;
        this.isAlive = true;
        initializeSprite();
        this.alive.emit(true);
    }

    public abstract void initializeSprite();

    public Layer getLayer() {
        if (this.sprite == null) return null;

        return sprite.layer;
    }

    public void die() {
        if(!this.isAlive) return;

        this.isAlive = false;
        alive.emit(false);

        if (this.sprite != null && !this.isAlive ) {
           this.sprite.dispose();
           this.sprite = null;
        }
    }

    public void update(int delta) {
        if(!this.isAlive) return;

        // used to interpolate on paint
        oldPos.x = pos.x;
        oldPos.y = pos.y;
        float t = delta / 1000f;

        vel.x = vel.x + acel.x * t;
        vel.y = vel.y + acel.y * t;

        pos.x = pos.x + vel.x * t;
        pos.y = pos.y + vel.y * t;

    }

    public void paint(Clock clock) {
        if(!this.isAlive) return;

        float alpha = clock.alpha;

        if (sprite != null) {
            sprite.layer.setTranslation(MathUtil.lerp(oldPos.x, pos.x, alpha), MathUtil.lerp(oldPos.y, pos.y, alpha));
            sprite.paint(clock);
        }
    }

    public Vector getAcel() {
        return acel;
    }

    public Point getPos() {
        return pos;
    }

    public void setPos(float x, float y) {
        pos.x = x;
        pos.y = y;
        oldPos.x = x;
        oldPos.y = y;
    }

    public Vector getVel() {
        return vel;
    }

    public GameWorld getWorld() {
        return world;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    // Controllable
    public void moveUp() {
        Vector v = resetVertical();
        v.y = -speed;
        v.setLength(speed);
    }

    public void moveDown() {
        Vector v = resetVertical();
        v.y = speed;
        v.setLength(speed);
    }

    public void moveLeft() {
        Vector v = resetHorizontal();
        v.x = -speed;
        v.setLength(speed);
    }

    public void moveRight() {
        Vector v = resetHorizontal();
        v.x = speed;
        v.setLength(speed);
    }

    public void stopHorizontal() {
        resetHorizontal();
    }

    public void stopVertical() {
        resetVertical();
    }

    public void fire() {
        // NOP
    }

    @Override
    public Transform getTransform() {
        t.set(new Vec2(this.pos.x, this.pos.y), 0);

        return t;
    }

    private Vector resetHorizontal() {
        Vector v = getVel();
        v.x = 0f;
        if (v.length() > 0) {
            v.normalizeLocal();
            v.setLength(speed);
        }

        return v;
    }

    private Vector resetVertical() {
        Vector v = getVel();
        v.y = 0f;
        if (v.length() > 0) {
            v.normalizeLocal();
            v.setLength(speed);
        }

        return v;
    }

    @Override
    public void collisionCallback(Collideable hit) {
        // NOP
    }

    public NemesisGame game() {
        return this.game;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        if (this.getLayer() != null) {
           this.getLayer().setVisible(visible);
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        this.isAlive = alive;
        this.alive.emit(alive);
    }
}
