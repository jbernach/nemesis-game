package com.superjose128.nemesis.core.sprites;

import playn.core.Clock;
import playn.core.Image;
import playn.scene.ImageLayer;
import pythagoras.f.Rectangle;

public class AnimatedSprite {
    private final Image image;
    private final int tilesPerRow;

    private float msPerFrame;
    private float nextFrame;

    private int bank;
    private int rowY;
    private int frame;

    private final int width, height;
    public boolean loop = true;
    public final ImageLayer layer;

    /**
     * Crea un sprite animado. Se pueden tener n bancos, cada uno conteniendo una secuencia animada de igual tamaño en todos los casos.
     *
     * @param image       Imagen que contiene una fila de imágenes por banco de animaciones y una columna por frame de animación.
     * @param tileWid     Ancho de cada frame del sprite.
     * @param tileHei     Alto de cada frame del sprite.
     * @param tilesPerRow Número de frames que componen cada banco.
     * @param msPerFrame  Número de ms que deben transcurrir entre cada frame de animación.
     */
    public AnimatedSprite(Image image, int tileWid, int tileHei, int tilesPerRow, float msPerFrame) {
        this.image = image;
        this.tilesPerRow = tilesPerRow;
        this.frame = 0;
        this.bank = 0;
        this.rowY = 0;
        this.msPerFrame = msPerFrame;
        this.nextFrame = msPerFrame;
        this.width = tileWid;
        this.height = tileHei;
        this.layer = new ImageLayer(image);
        layer.setOrigin(this.width / 2, this.height / 2);
        updateImage();
    }

    /**
     * Sprite paint on layer
     *
     * @param clock Platform clock
     */
    public void paint(Clock clock) {
        if (msPerFrame > 0) {
            nextFrame -= clock.dt;
            int f = this.frame;
            while (nextFrame < 0) {
                nextFrame += msPerFrame;
                if (loop) {
                    f = (f + 1) % tilesPerRow;
                } else {
                    f = f + 1;
                    f = Math.min(f, tilesPerRow - 1);
                }
            }

            setFrame(f);
        }
    }

    private void updateImage() {
        System.out.println("Img:" + this.image.width() + " Bank:" + this.bank + " Frame:" + this.frame);
        this.layer.setRegion(new Rectangle(this.frame * this.width, this.rowY, this.width, this.height));
    }

    public float getMsPerFrame() {
        return this.msPerFrame;
    }

    public void setMsPerFrame(float msPerFrame) {
        this.msPerFrame = msPerFrame;
        this.nextFrame = msPerFrame;
    }

    public void setFrame(int frame) {
        if (frame != this.frame) {
            this.frame = frame;
            updateImage();
        }
    }

    public int getBank() {
        return this.bank;
    }

    /**
     * Selecciona la fila (el banco de sprites a usar)
     *
     * @param bank new  bank
     */
    public void setBank(int bank) {
        if (this.bank != bank) {
            this.bank = bank;
            this.rowY = this.bank * this.height;
            updateImage();
        }
    }
}