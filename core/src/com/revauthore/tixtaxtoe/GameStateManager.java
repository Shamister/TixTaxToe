package com.revauthore.tixtaxtoe;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.revauthore.tixtaxtoe.states.State;

import java.util.Stack;

/**
 * Created by Revauthore on 10/31/2015.
 */
public class GameStateManager {
    private Stack<State> states;

    public GameStateManager() {
        states = new Stack<State>();
    }

    public void push(State state) {
        states.push(state);
    }

    public void pop() {
        states.peek().dispose();
        states.pop();
    }

    public void set(State state) {
        pop();
        states.push(state);
    }

    public State peek() {
        return states.peek();
    }

    public void update(float dt) {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }
}
