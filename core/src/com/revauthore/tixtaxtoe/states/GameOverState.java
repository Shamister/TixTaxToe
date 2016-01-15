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
 * Created by Revauthore on 11/4/2015.
 */
public class GameOverState extends GameState {

    private float dialogWidth;
    private float dialogHeight;
    private float marginXDialog;
    private float marginYDialog;
    private float dialogTitleWidth;
    private float dialogTitleHeight;
    private float marginXDialogTitle;
    private float marginYDialogTitle;
    private float buttonLength;
    private float marginXDialogButton;
    private float marginYDialogButton;
    private float buttonDistanceX;
    private TextureAtlas gameOver;
    private TextureRegion playAgain;
    private TextureRegion yesButton;
    private TextureRegion noButton;
    private Rectangle rects[];

    public GameOverState(GameStateManager gsm, GameManager.Turn turn, GameManager gm) {
        super(gsm, turn);
        this.gm = gm;
        gameOver = new TextureAtlas("gameOver/gameOver.pack");
        loadTextures();
        init();
    }

    private void loadTextures() {
        playAgain = gameOver.findRegion("playAgain");
        yesButton = gameOver.findRegion("yes");
        noButton = gameOver.findRegion("no");
    }

    private void init() {
        dialogWidth = Game.WIDTH * 3 / 4;
        dialogHeight = Game.HEIGHT / 4;
        marginXDialog = (Game.WIDTH - dialogWidth) / 2;
        marginYDialog = (Game.HEIGHT - dialogHeight) / 2;
        dialogTitleWidth = dialogWidth * 3 / 4;
        dialogTitleHeight = dialogHeight / 5;
        marginXDialogTitle = marginXDialog + (dialogWidth - dialogTitleWidth) / 2;
        marginYDialogTitle = marginYDialog + dialogHeight - 2 * dialogTitleHeight;
        buttonLength = Math.min(dialogWidth / 6, dialogHeight / 5);
        buttonDistanceX = (dialogWidth - 2 * buttonLength) / 3;
        marginXDialogButton = marginXDialog + buttonDistanceX;
        marginYDialogButton = marginYDialog + buttonLength;
        rects = new Rectangle[2];
        float buttonMarginX = marginXDialogButton;
        rects[0] = new Rectangle(buttonMarginX, marginYDialogButton, buttonLength, buttonLength);
        buttonMarginX += buttonDistanceX + buttonLength;
        rects[1] = new Rectangle(buttonMarginX, marginYDialogButton, buttonLength, buttonLength);
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

        sb.begin();
        //================ DIALOG TITLE ==================
        sb.draw(playAgain, marginXDialogTitle, marginYDialogTitle, dialogTitleWidth, dialogTitleHeight);
        //================ BUTTONS ================
        float buttonMarginX = marginXDialogButton;
        sb.draw(yesButton, buttonMarginX, marginYDialogButton, buttonLength, buttonLength);
        buttonMarginX += buttonDistanceX + buttonLength;
        sb.draw(noButton, buttonMarginX, marginYDialogButton, buttonLength, buttonLength);
        sb.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        gameOver.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
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
            // create a new game
            gsm.set(new GameState(gsm, this.gm.getFirstTurn()));
            return true;
        }
        if (rects[1].contains(pointerX, pointerY)) {
            // back to main menu
            gsm.pop();
            Gdx.input.setInputProcessor((InputProcessor) gsm.peek());
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
