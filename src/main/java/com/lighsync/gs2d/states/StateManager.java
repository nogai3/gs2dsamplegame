package com.lighsync.gs2d.states;

import com.lighsync.gs2d.platform.InputManager;
import com.lighsync.gs2d.renderer.RenderSystem;

import java.util.HashMap;
import java.util.Map;

public class StateManager {
    private final Map<String, GameState> states = new HashMap<>();
    private GameState currentState = null;

    public void registerState(String name, GameState state) {
        states.put(name, state);
    }

    public void changeState(String name) {
        if (!states.containsKey(name)) return;

        if (currentState != null) {
            currentState.dispose();
        }
        currentState = states.get(name);
        currentState.init();
    }

    public void update(float deltaTime, InputManager inputManager) {
        if (currentState != null) currentState.update(deltaTime, inputManager);
    }

    public void render(RenderSystem renderSystem) {
        if (currentState != null) currentState.render(renderSystem);
    }

    public GameState getCurrentState() {
        return this.currentState;
    }
}