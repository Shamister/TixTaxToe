package com.revauthore.tixtaxtoe.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.revauthore.tixtaxtoe.Game;
import com.revauthore.tixtaxtoe.GameManager;
import com.revauthore.tixtaxtoe.GameStateManager;


/**
 * Created by Revauthore on 11/2/2015.
 */
public class GameSelectState extends MenuState implements InputProcessor {
    TextureAtlas gameSelection;
    TextureRegion dialogTitle;
    TextureRegion computerButton;
    TextureRegion playerButton;
    ShapeRenderer sr;
    private float dialogWidth;
    private float dialogHeight;
    private float marginXDialog;
    private float marginYDialog;
    private float dialogTitleWidth;
    private float dialogTitleHeight;
    private float marginXDialogTitle;
    private float marginYDialogTitle;
    private float dialogButtonWidth;
    private float dialogButtonHeight;
    private float marginXDialogButton;
    private Rectangle rects[];

    protected GameSelectState(GameStateManager gsm) {
        super(gsm);
        sr = new ShapeRenderer();
        gameSelection = new TextureAtlas("gameSelection/gameSelection.pack");
        loadTextures();
        init();
    }

    private void loadTextures() {
        dialogTitle = gameSelection.findRegion("whoMovesFirst");
        computerButton = gameSelection.findRegion("computer");
        playerButton = gameSelection.findRegion("player");
    }

    private void init() {
        dialogWidth = Game.WIDTH * 3 / 4;
        dialogHeight = Game.HEIGHT / 5;
        marginXDialog = (Game.WIDTH - dialogWidth) / 2;
        marginYDialog = (Game.HEIGHT - dialogHeight) / 2;
        dialogTitleWidth = dialogWidth * 4 / 5;
        dialogTitleHeight = dialogHeight / 7;
        marginXDialogTitle = marginXDialog + (dialogWidth - dialogTitleWidth) / 2;
        marginYDialogTitle = Game.HEIGHT - marginYDialog - dialogTitleHeight * 2;
        dialogButtonWidth = dialogWidth * 3 / 5;
        dialogButtonHeight = dialogTitleHeight;
        marginXDialogButton = marginXDialog + (dialogWidth - dialogButtonWidth) / 2;
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        rects = new Rectangle[2];
        float yPosItem = marginYDialogTitle - 2 * dialogTitleHeight;
        // computer button bounds
        float marginXCompButton = dialogButtonWidth / 8;
        rects[0] = new Rectangle(marginXDialogButton + marginXCompButton, yPosItem, dialogButtonWidth - 2 * marginXCompButton, dialogButtonHeight);
        yPosItem -= 2 * dialogTitleHeight;
        // player button bounds
        float marginXPlayerButton = dialogButtonWidth / 4;
        rects[1] = new Rectangle(marginXDialogButton + marginXPlayerButton, yPosItem, dialogButtonWidth - 2 * marginXPlayerButton, dialogButtonHeight);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setProjectionMatrix(cam.combined);
        //================ BACKGROUND ================
        sr.setColor(Game.SHADE_COLOR[0], Game.SHADE_COLOR[1], Game.SHADE_COLOR[2], Game.SHADE_COLOR[3]);
        sr.rect(0, 0, Game.WIDTH, Game.HEIGHT);
        //================ DIALOG ================
        sr.setColor(Game.BG_COLOR[0], Game.BG_COLOR[1], Game.BG_COLOR[2], Game.BG_COLOR[3]);
        sr.rect(marginXDialog, marginYDialog, dialogWidth, dialogHeight);
        sr.end();
        //================ DIALOG BORDER =================
        Gdx.gl.glLineWidth(Game.DIALOG_BORDER_WIDTH);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setProjectionMatrix(cam.combined);
        sr.setColor(Game.DIALOG_BORDER_COLOR[0], Game.DIALOG_BORDER_COLOR[1], Game.DIALOG_BORDER_COLOR[2], Game.DIALOG_BORDER_COLOR[3]);
        sr.rect(marginXDialog, marginYDialog, dialogWidth, dialogHeight);
        sr.end();
        //================ DIALOG MENU =================
        float yPosItem = marginYDialogTitle;
        sb.begin();
        sb.draw(dialogTitle, marginXDialogTitle, yPosItem, dialogTitleWidth, dialogTitleHeight);
        yPosItem -= 2 * dialogTitleHeight;
        sb.draw(computerButton, marginXDialogButton, yPosItem, dialogButtonWidth, dialogButtonHeight);
        yPosItem -= 2 * dialogTitleHeight;
        sb.draw(playerButton, marginXDialogButton, yPosItem, dialogButtonWidth, dialogButtonHeight);
        sb.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void dispose() {
        super.dispose();
        gameSelection.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            gsm.pop();
            Gdx.input.setInputProcessor((InputProcessor) gsm.peek());
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT) return false;
        float pointerX = screenX * cam.viewportWidth / Gdx.graphics.getWidth();
        float pointerY = Game.HEIGHT - screenY * cam.viewportHeight / Gdx.graphics.getHeight();
        if (rects[0].contains(pointerX, pointerY)) {
            //System.out.println("COMP BUTTON CLICKED");
            gsm.set(new GameState(gsm, GameManager.Turn.COMPUTER));
            return true;
        }
        if (rects[1].contains(pointerX, pointerY)) {
            //System.out.println("PLAYER BUTTON CLICKED");
            gsm.set(new GameState(gsm, GameManager.Turn.PLAYER));
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
