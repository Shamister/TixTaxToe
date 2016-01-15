package com.revauthore.tixtaxtoe.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.revauthore.tixtaxtoe.Game;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Game.WIDTH / 2;
        config.height = Game.HEIGHT / 2;
        config.title = Game.TITLE;
        config.resizable = false;
        new LwjglApplication(new Game(), config);
    }
}
