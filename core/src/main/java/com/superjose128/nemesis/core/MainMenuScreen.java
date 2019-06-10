package com.superjose128.nemesis.core;

import playn.core.Canvas;
import playn.core.Font;
import playn.core.Game;
import playn.core.Image;
import playn.core.TextFormat;
import playn.core.TextLayout;
import playn.scene.ImageLayer;
import playn.scene.Pointer;
import tripleplay.anim.AnimBuilder;
import tripleplay.anim.Animator;
import tripleplay.game.ScreenStack;
import tripleplay.util.Colors;

public class MainMenuScreen extends ScreenStack.UIScreen {

    private final NemesisGame game;

    private ImageLayer title;
    private ImageLayer logoLayer;

    final private int NUM_LABEL_START_FLASH = 10;
    private boolean flashing;

    public MainMenuScreen(NemesisGame game) {
        super(game.plat);
        this.game = game;
    }

    @Override
    public Game game() {
        return this.game;
    }

    @Override
    public void wasAdded() {
        super.wasAdded();

        Font font = new Font("Nemesis", Font.Style.PLAIN, 32);
        TextFormat textFormat = new TextFormat(font, false);
        Canvas canvas = this.game.plat.graphics().createCanvas(GameWorld.NATIVE_RES_WIDTH, GameWorld.NATIVE_RES_HEIGHT);
        canvas.setFillColor(Colors.WHITE);
        TextLayout textLayout = this.game.plat.graphics().layoutText("Press to play", textFormat);
        canvas.fillText(textLayout, GameWorld.NATIVE_RES_WIDTH / 2 - 130, 2 * GameWorld.NATIVE_RES_HEIGHT / 3f);

        logoLayer = new ImageLayer(canvas.snapshot());

        float scaleW = this.size().width() / GameWorld.NATIVE_RES_WIDTH;
        float scaleH = this.size().height() / GameWorld.NATIVE_RES_HEIGHT;
        float scale = Math.min(scaleW, scaleH);
        logoLayer.setScale(scale);

        Image imageTitle = this.game.plat.assets().getImage("images/title.png");
        title = new ImageLayer(imageTitle);
        title.setTranslation(this.size().width() / 2, this.size().height() / 3);

        imageTitle.state.onComplete(event -> {
            float anchoImagenDeseado = size().width() / 2f;
            float escalaImg = anchoImagenDeseado / event.get().width();
            title.setScale(escalaImg);

            title.setOrigin(event.get().width() / 2, event.get().height() / 2);
            layer.add(title);
            layer.add(logoLayer);
        });

        imageTitle.state.onFailure(event -> {
            game.plat.log().error("No se pudo cargar la imagen del titulo.", event);
            game.screens.remove(this);
        });
    }

    @Override
    public void wasShown() {
        super.wasShown();
        captureKeyboardAndPointer();
        layer.add(logoLayer);
    }

    private void startNewGame() {
        layer.remove(logoLayer);
        this.game.plat.input().keyboardEvents.clearConnections();
        GameWorldScreen gameWorlScreen = new GameWorldScreen(this.game);
        game.screens.push(gameWorlScreen);
        gameWorlScreen.beginGame();
    }

    private void captureKeyboardAndPointer() {
        this.layer.events().clearConnections();
        this.layer.events().connect(new Pointer.Listener() {
            @Override
            public void onStart(Pointer.Interaction interaction) {
                flashStart();
            }
        });

        this.game.plat.input().keyboardEvents.clearConnections();
        this.game.plat.input().keyboardEvents.connect(event -> {
            flashStart();
        });
    }

    private synchronized void flashStart() {
        if (flashing) return;

        this.flashing = true;

        Animator animator = new Animator();
        this.paint.connect(animator.onPaint);

        Runnable flash = new Runnable() {
            int cycle = 0;

            boolean started = false;

            @Override
            public void run() {
                if (cycle < NUM_LABEL_START_FLASH) {
                    if (cycle++ % 2 == 0) {
                        logoLayer.setAlpha(0);
                    } else {
                        logoLayer.setAlpha(1);
                    }
                } else if (!started) {
                    started = true;
                    MainMenuScreen.this.flashing = false;
                    startNewGame();
                }
            }
        };

        animator.repeat(logoLayer).delay(100).then().action(flash);
    }

}
