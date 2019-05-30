package com.superjose128.nemesis.core.collision;

import org.jbox2d.dynamics.World;
import org.jbox2d.pooling.IWorldPool;
import org.jbox2d.pooling.normal.DefaultWorldPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Processes collition detection grouping collideable objects by type.
 *
 * @author Joselito y Tere
 */
public class CollisionManager {
    private LinkedList<Collision> collisions = new LinkedList<>();

    private IWorldPool pool = new DefaultWorldPool(World.WORLD_POOL_SIZE, World.WORLD_POOL_CONTAINER_SIZE);
    private org.jbox2d.collision.Collision collisionTester = new org.jbox2d.collision.Collision(pool);

    private HashMap<CollideableTypes, ArrayList<Collideable>> itemsByType = new HashMap<CollideableTypes, ArrayList<Collideable>>();
    CollideableTypes[][] collideablePairs = new CollideableTypes[][]{
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

    public void addCollideable(Collideable item) {
        if (CollideableTypes.GHOST == item.getType()) return;

        ArrayList<Collideable> v = itemsByType.get(item.getType());
        if (v == null) {
            v = new ArrayList<Collideable>();
            itemsByType.put(item.getType(), v);
        }

        v.add(item);
    }

    public void removeCollideable(Collideable item) {
        if (CollideableTypes.GHOST == item.getType()) return;

        ArrayList<Collideable> v = itemsByType.get(item.getType());
        if (v != null) {
            v.remove(item);
        }

    }

    public void calculateCollisions() {
        for (CollideableTypes[] pair : collideablePairs) {
            calculateCollisions(pair[0], pair[1]);
        }
    }
    /**
     * Calculate collisions between collideable objects of type A vs collideable objects of type B
     * and puts the resulting Collision objects in the Arraylist.
     *
     * @param typeA
     * @param typeB
     */
    private void calculateCollisions(CollideableTypes typeA, CollideableTypes typeB) {
        ArrayList<Collideable> vA = itemsByType.get(typeA);
        ArrayList<Collideable> vB = itemsByType.get(typeB);

        if (vA == null || vB == null) return;

        if (vA.size() == 0 || vB.size() == 0) return;

        for (Collideable a : vA) {
            for (Collideable b : vB) {
                boolean overLap = collisionTester.testOverlap(a.getShape(), 0, b.getShape(), 0, a.getTransform(), b.getTransform());
                if (overLap) {
                    collisions.add(new Collision(a, b));
                }
            }
        }
    }

    /**
     * Process all collisions on the list invoking the A object callback of the collision
     * and removing it from the list.
     */
    public void processCollisions() {
        while (!collisions.isEmpty()) {
            Collision c = collisions.removeFirst();
            c.collideableA.collisionCallback(c.collideableB);
        }
    }
}
