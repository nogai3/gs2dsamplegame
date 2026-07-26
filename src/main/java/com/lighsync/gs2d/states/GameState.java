package com.lighsync.gs2d.states;

import com.lighsync.gs2d.platform.InputManager;
import com.lighsync.gs2d.renderer.Camera;
import com.lighsync.gs2d.renderer.RenderSystem;

public interface GameState {
    void init();
    void update(float deltaTime, InputManager inputManager);
    void render(RenderSystem renderSystem);
    void dispose();
    Camera getCamera();
}