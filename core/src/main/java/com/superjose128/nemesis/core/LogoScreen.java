package com.superjose128.nemesis.core;

import playn.core.Game;
import playn.core.Image;
import playn.scene.ImageLayer;
import tripleplay.anim.Animator;
import tripleplay.game.ScreenStack;

public class LogoScreen extends ScreenStack.UIScreen {
    public static final int DURATION = 2500; // 2,5 sec

    private final NemesisGame game;

    private ImageLayer logo;
    private Image imageLogo;

    public LogoScreen(NemesisGame game) {
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

        imageLogo = game.plat.assets().getImage("images/konami_logo.png");
        logo = new ImageLayer(imageLogo);

        // Preload of game sounds
        this.game.soundsFx.loadAllSounds();

        imageLogo.state.onComplete(image -> {
            float anchoImagenDeseado = this.size().width() / 4f;
            float escalaImg = anchoImagenDeseado / image.get().width();

            logo.setScale(escalaImg);
            logo.setOrigin(image.get().width() / 2, image.get().height() / 2);
            logo.setTranslation(this.size().width() / 2, -logo.height() / 2);
        });
    }

    @Override
    public void wasShown() {
        super.wasShown();

        imageLogo.state.onComplete(image -> {
            layer.add(logo);

            Animator animator = new Animator();
            this.paint.connect(animator.onPaint);
            animator.tweenY(logo).to(this.size().height() / 3f).in(DURATION).easeInOut()
                    .then().delay(1000).then().action(() -> {
                this.game.screens.push(new MainMenuScreen(this.game));
            });
        });

        imageLogo.state.onFailure(error -> {
            game.plat.log().error("No se pudo cargar el logo.", error);
            this.game.screens.push(new MainMenuScreen(this.game));
        });
    }
}
