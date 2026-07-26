package com.lighsync.gs2dgame.states.gui;

import com.lighsync.gs2d.platform.InputManager;
import com.lighsync.gs2d.platform.ResourceManager;
import com.lighsync.gs2d.renderer.RenderSystem;
import com.lighsync.gs2d.states.StateManager;
import com.lighsync.gs2dgame.Components;
import com.lighsync.gs2dgame.GS2DGame;
import com.lighsync.gs2dgame.states.base.GuiScreenBase;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class MainMenuScreen extends GuiScreenBase {
    private final BufferedImage background = ResourceManager.loadImage("/assets/textures/gui/game_background.png");
    private final BufferedImage title = ResourceManager.loadImage("/assets/textures/gui/game_title.png");

    public MainMenuScreen(StateManager stateManager) {
        super(stateManager, List.of(Components.START_GAME, Components.OPTIONS, Components.QUIT_GAME), "GS2D Game", new Color(20, 20, 30));
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void update(float deltaTime, InputManager inputManager) {
        super.update(deltaTime, inputManager);
    }

    @Override
    public void applyChooseControls(InputManager inputManager) {
        super.applyChooseControls(inputManager);
    }

    @Override
    public void executeSelection() {
        switch (currentSelection) {
            case 0 -> stateManager.changeState("gameplay");
            case 1 -> System.out.println("Options opened");
            case 2 -> GS2DGame.quit();
        }
    }

    @Override
    public void render(RenderSystem renderSystem) {
        int centerX = GS2DGame.width / 2 - 75;
        int centerY = GS2DGame.height / 2 - 255;

        renderSystem.drawTexture(background, 0, 0, GS2DGame.width, GS2DGame.height);
        renderSystem.drawTexture(title, centerX, centerY);

        applyControls(renderSystem);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}