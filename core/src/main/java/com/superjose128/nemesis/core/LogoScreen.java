package com.superjose128.nemesis.core;

import playn.core.Game;
import playn.core.Image;
import playn.scene.ImageLayer;
import react.Signal;
import react.Slot;
import tripleplay.game.ScreenStack;

public class LogoScreen extends ScreenStack.UIScreen {
    public static final int DURATION = 2500; // 2,5 sec

    private final NemesisGame game;

    private final Signal<Boolean> _completeSignal = new Signal<Boolean>();
    private ImageLayer _logo;

    public LogoScreen(NemesisGame game) {
        super(game.plat);
        this.game = game;
        this._completeSignal.connect(new Slot<Boolean>() {
            public void onEmit(Boolean event) {
                game.screens.push(new MainMenuScreen(game));
            }
        });
    }

    @Override
    public Game game() {
        return this.game;
    }

    @Override
    public void wasAdded() {
        super.wasAdded();

        Image imageLogo = game.plat.assets().getImage("images/konami_logo.png");
        _logo = new ImageLayer(imageLogo);

        // Preload of game sounds
        this.game.soundsFx.loadAllSounds();

        imageLogo.state.onComplete(result -> {
            float anchoImagenDeseado = this.size().width() / 4f;
            float escalaImg = anchoImagenDeseado / result.get().width();

            _logo.setScale(escalaImg);
            _logo.setOrigin(result.get().width() / 2, result.get().height() / 2);
            _logo.setTranslation(this.size().width() / 2, -_logo.height() / 2);

            layer.add(_logo);

            this.iface.anim.tweenY(_logo).to(this.size().height() / 3f).in(DURATION).easeInOut().then().emit(_completeSignal, true);
        });

        imageLogo.state.onFailure(error -> {
            game.plat.log().error("No se pudo cargar el logo.", error);
            _completeSignal.emit(true); // Continue to next screen
        });
    }
}
