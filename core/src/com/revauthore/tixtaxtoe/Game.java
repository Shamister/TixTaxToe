package com.revauthore.tixtaxtoe;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.revauthore.tixtaxtoe.states.MenuState;

public class Game extends ApplicationAdapter {
    public static final int WIDTH = 720;
    public static final int HEIGHT = 1280;
    public static final String TITLE = "Tix Tax Toe";
    public static final float[] BG_COLOR = {0.203125f, 0.28515625f, 0.3671875f, 1};
    public static final float[] SHADE_COLOR = {0, 0, 0, 0.5f};
    public static final float[] DIALOG_BORDER_COLOR = {1, 1, 1, 1};
    public static final float[] BOARD_COLOR = {0.4375f, 0.57421875f, 0.7109375f, 1};
    public static final int DIALOG_BORDER_WIDTH = 3;
    public static final int OUTER_LINE_LENGTH = 15;
    public static final int BOARD_LINE_WIDTH = 6;
    public static final float[] BOARD_LINE_COLOR = {1, 1, 1, 1};
    public static final int PADDING_SYMBOL = 5;
    public static final int BOARD_SIZE = 3;
    public static final float COMP_MOVE_DELAY = 0.5f;
    public static final float GAME_OVER_DELAY = 0.5f;
    public static final String AD_UNIT_ID = "ca-app-pub-4211886139454274/8265969748";
    private GameStateManager gsm;
    private SpriteBatch batch;

    public Game() {
        gsm = new GameStateManager();
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        gsm.push(new MenuState(gsm));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(BG_COLOR[0], BG_COLOR[1], BG_COLOR[2], BG_COLOR[3]);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }
}
