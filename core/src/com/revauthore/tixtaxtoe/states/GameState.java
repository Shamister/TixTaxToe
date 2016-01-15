package com.revauthore.tixtaxtoe.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.revauthore.tixtaxtoe.Game;
import com.revauthore.tixtaxtoe.GameManager;
import com.revauthore.tixtaxtoe.GameStateManager;

/**
 * Created by Revauthore on 11/3/2015.
 */
public class GameState extends State implements InputProcessor {
    protected GameManager gm;
    protected ShapeRenderer sr;
    private float boardLength;
    private float marginXBoard;
    private float marginYBoard;
    private float cellLength;
    private float xVLine;
    private float startYVLine;
    private float endYVLine;
    private float startXHLine;
    private float endXHLine;
    private float yHLine;
    private float marginXSymbol;
    private float marginYSymbol;
    private float symbolLength;
    private float statusWidth;
    private float statusHeight;
    private float marginXStatus;
    private float marginYStatus;
    private float gameOverElapsed;
    private TextureAtlas game;
    private TextureRegion draw;
    private TextureRegion youWin;
    private TextureRegion youLose;
    private TextureRegion symbolX;
    private TextureRegion symbolO;
    private Rectangle[][] rects;

    protected GameState(GameStateManager gsm, GameManager.Turn turn) {
        super(gsm);
        cam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
        cam.update();
        game = new TextureAtlas("game/game.pack");
        loadTextures();
        init(turn);
    }

    private void loadTextures() {
        draw = game.findRegion("draw");
        youWin = game.findRegion("youWin");
        youLose = game.findRegion("youLose");
        symbolX = game.findRegion("x");
        symbolO = game.findRegion("o");
    }

    private void init(GameManager.Turn turn) {
        sr = new ShapeRenderer();
        gm = new GameManager(turn);
        boardLength = Game.WIDTH * 3 / 4;
        marginXBoard = (Game.WIDTH - boardLength) / 2;
        marginYBoard = (Game.HEIGHT - boardLength) / 2;
        cellLength = boardLength / Game.BOARD_SIZE;
        xVLine = marginXBoard + cellLength;
        startYVLine = marginYBoard - Game.OUTER_LINE_LENGTH;
        endYVLine = marginYBoard + boardLength + Game.OUTER_LINE_LENGTH;
        startXHLine = marginXBoard - Game.OUTER_LINE_LENGTH;
        endXHLine = marginXBoard + boardLength + Game.OUTER_LINE_LENGTH;
        yHLine = marginYBoard + cellLength;
        marginXSymbol = marginXBoard + Game.PADDING_SYMBOL;
        marginYSymbol = marginYBoard + boardLength - cellLength + Game.PADDING_SYMBOL;
        symbolLength = boardLength / Game.BOARD_SIZE - 2 * Game.PADDING_SYMBOL;
        statusWidth = boardLength;
        statusHeight = Game.HEIGHT / 20;
        marginXStatus = (Game.WIDTH - statusWidth) / 2;
        marginYStatus = marginYBoard + boardLength + statusHeight;
        rects = new Rectangle[Game.BOARD_SIZE][Game.BOARD_SIZE];
        // cell bounds
        for (int i = 0; i < Game.BOARD_SIZE; i++) {
            float marginX = marginXSymbol + i * cellLength;
            for (int j = 0; j < Game.BOARD_SIZE; j++) {
                float marginY = marginYSymbol - j * cellLength;
                rects[i][j] = new Rectangle(marginX, marginY, symbolLength, symbolLength);
            }
        }
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void update(float dt) {
        if (!gm.isGameOver()) {
            gm.update(dt);
        } else {
            gameOverElapsed += dt;
            if (gameOverElapsed >= Game.GAME_OVER_DELAY) {
                gsm.set(new GameOverState(gsm, gm.getFirstTurn(), gm));
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        //====================== BOARD =======================
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setProjectionMatrix(cam.combined);
        sr.setColor(Game.BOARD_COLOR[0], Game.BOARD_COLOR[1], Game.BOARD_COLOR[2], Game.BOARD_COLOR[3]);
        sr.rect(marginXBoard, marginYBoard, boardLength, boardLength);
        sr.end();
        //====================== BOARD LINE ========================
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setProjectionMatrix(cam.combined);
        sr.setColor(Game.BOARD_LINE_COLOR[0], Game.BOARD_LINE_COLOR[1], Game.BOARD_LINE_COLOR[2], Game.BOARD_LINE_COLOR[3]);
        //====================== HORIZONTAL LINES ======================
        for (int i = 0; i < Game.BOARD_SIZE - 1; i++) {
            float marginY = yHLine + i * cellLength;
            sr.rectLine(startXHLine, marginY, endXHLine, marginY, Game.BOARD_LINE_WIDTH);
        }
        //====================== VERTICAL LINES ======================
        for (int i = 0; i < Game.BOARD_SIZE - 1; i++) {
            float marginX = xVLine + i * cellLength;
            sr.rectLine(marginX, startYVLine, marginX, endYVLine, Game.BOARD_LINE_WIDTH);
        }
        sr.end();
        //====================== SYMBOLS ======================
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        char[][] board = gm.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 'X') {
                    sb.draw(symbolX, rects[i][j].x, rects[i][j].y, symbolLength, symbolLength);
                } else if (board[i][j] == 'O') {
                    sb.draw(symbolO, rects[i][j].x, rects[i][j].y, symbolLength, symbolLength);
                }
            }
        }
        //===================== STATUS =====================
        if (gm.isGameOver()) {
            if (gm.getStatus() == 1) {
                sb.draw(youWin, marginXStatus, marginYStatus, statusWidth, statusHeight);
            } else if (gm.getStatus() == 2) {
                sb.draw(youLose, marginXStatus, marginYStatus, statusWidth, statusHeight);
            } else if (gm.getStatus() == 3) {
                sb.draw(draw, marginXStatus, marginYStatus, statusWidth, statusHeight);
            }
        }
        sb.end();
    }

    @Override
    public void dispose() {
        game.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK && !gm.isGameOver()) {
            gsm.push(new GameQuitState(gsm, gm.getFirstTurn(), gm));
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
        //System.out.println("Clicked : (" + pointerX + "," + pointerY + ")");
        if (!gm.isGameOver()) {
            for (int i = 0; i < rects.length; i++) {
                for (int j = 0; j < rects.length; j++) {
                    //System.out.println("    rects[" + i + "][" + j + "] = " + rects[i][j].getMinX() + "," + rects[i][j].getMinY() + "," + rects[i][j].getMaxX() + "," + rects[i][j].getMaxY());
                    if (rects[i][j].contains(pointerX, pointerY)) {
                        //System.out.println("    this rect contains the position");
                        gm.setMove(i, j);
                        return true;
                    }
                }
            }
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
