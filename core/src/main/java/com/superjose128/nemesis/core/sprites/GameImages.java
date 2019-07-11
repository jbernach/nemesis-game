package com.superjose128.nemesis.core.sprites;

import playn.core.Image;
import playn.core.Platform;

import java.util.HashMap;

public class GameImages {
    private final Platform platform;
    public final HashMap<String, Image> images = new HashMap<>();

    public GameImages(Platform platform) {
        this.platform = platform;
        loadAllImages();
    }

    private void loadAllImages(){
        platform.log().info("Loading images...");
        loadImage("metallion", "images/sprites/metallion.png");
        loadImage("explode_viper", "images/sprites/explode_viper.png");
        loadImage("barrel", "images/sprites/barrells.png");
        loadImage("chiqui", "images/sprites/chiquis.png");
        loadImage("explode1", "images/sprites/explode1.png");
        loadImage("bullet", "images/sprites/bullet.png");
        loadImage("missilie", "images/sprites/missilie.png");
        loadImage("capsule_red", "images/sprites/capsule_red.png");
        loadImage("flame","images/sprites/flame.png");
    }

    private void loadImage(String key, String path){
        Image img = this.platform.assets().getImage(path);
        images.put(key, img);
    }

    public Image get(String key){
        return images.get(key);
    }

}
