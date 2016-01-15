package com.revauthore.tixtaxtoe.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Revauthore on 10/31/2015.
 */
public abstract class State{
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected com.revauthore.tixtaxtoe.GameStateManager gsm;

    protected State(com.revauthore.tixtaxtoe.GameStateManager gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

    public abstract void update(float dt);

    public abstract void render(SpriteBatch sb);

    public abstract void dispose();
}
