package com.revauthore.tixtaxtoe.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.revauthore.tixtaxtoe.Game;

/**
 * Created by Revauthore on 10/31/2015.
 */
public class MenuState extends State implements InputProcessor {
    protected TextureAtlas menuImages;
    protected TextureRegion title;
    protected TextureRegion newGameButton;
    protected TextureRegion exitButton;
    protected float titleLength;
    protected float marginXTitle;
    protected float marginYTitle;
    protected float marginXMenu;
    protected float marginYMenu;
    protected float buttonWidth;
    protected float buttonHeight;
    private Rectangle rects[];

    public MenuState(com.revauthore.tixtaxtoe.GameStateManager gsm) {
        super(gsm);

        cam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
        cam.update();
        menuImages = new TextureAtlas("menu/menu.pack");
        loadTextures();
        init();

    }

    private void init() {
        titleLength = Math.min(Game.WIDTH / 2, Game.HEIGHT / 4);
        marginXTitle = (Game.WIDTH - titleLength) / 2;
        marginYTitle = Game.HEIGHT * 3 / 4 - titleLength;
        buttonWidth = Game.WIDTH * 5 / 6;
        buttonHeight = Game.HEIGHT * 3 / 32;
        marginXMenu = Game.WIDTH / 12;
        marginYMenu = Game.HEIGHT * 3 / 8 - buttonHeight;
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(false);
        rects = new Rectangle[2];
        float buttonMarginY = marginYMenu;
        // new game button bounds
        float marginXNewGameButton = buttonWidth / 20;
        rects[0] = new Rectangle(marginXMenu + marginXNewGameButton, buttonMarginY, buttonWidth - 2 * marginXNewGameButton, buttonHeight);
        buttonMarginY -= buttonHeight;
        // exit button bounds
        float marginXExitButton = buttonWidth * 19 / 60;
        rects[1] = new Rectangle(marginXMenu + marginXExitButton, buttonMarginY, buttonWidth - 2 * marginXExitButton, buttonHeight);
    }

    private void loadTextures() {
        title = menuImages.findRegion("title");
        newGameButton = menuImages.findRegion("newGame");
        exitButton = menuImages.findRegion(("exit"));
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(title, marginXTitle, marginYTitle, titleLength, titleLength);
        float buttonMarginY = marginYMenu;
        sb.draw(newGameButton, marginXMenu, buttonMarginY, buttonWidth, buttonHeight);
        buttonMarginY -= buttonHeight;
        sb.draw(exitButton, marginXMenu, buttonMarginY, buttonWidth, buttonHeight);
        sb.end();
    }

    @Override
    public void dispose() {
        menuImages.dispose();
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
            //System.out.println("NEW GAME BUTTON CLICKED");
            gsm.push(new GameSelectState(gsm));
            return true;
        }
        if (rects[1].contains(pointerX, pointerY)) {
            //System.out.println("EXIT BUTTON CLICKED");
            Gdx.app.exit();
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
