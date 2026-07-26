package com.lighsync.gs2dgame.states.base;

import com.lighsync.gs2d.platform.InputManager;
import com.lighsync.gs2d.platform.SoundManager;
import com.lighsync.gs2d.renderer.Camera;
import com.lighsync.gs2d.renderer.RenderSystem;
import com.lighsync.gs2d.states.GameState;
import com.lighsync.gs2d.states.StateManager;
import com.lighsync.gs2dgame.Components;
import com.lighsync.gs2dgame.GS2DGame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GuiScreenBase implements GameState {
    protected List<String> options = new ArrayList<>();
    protected String title;
    protected int currentSelection = 0;
    protected boolean keyReleased = true;
    protected Color backgroundColor;
    protected final StateManager stateManager;

    public GuiScreenBase(StateManager stateManager, List<String> options, String title, Color backgroundColor) {
        this.stateManager = stateManager;
        this.title = title;
        this.options = options;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void init() {}

    @Override
    public void update(float deltaTime, InputManager inputManager) {
        applyChooseControls(inputManager);
    }

    public void applyChooseControls(InputManager inputManager) {
        if (inputManager.isKeyPressed(KeyEvent.VK_W) || inputManager.isKeyPressed(KeyEvent.VK_UP)) {
            if (keyReleased) {
                currentSelection--;
                if (currentSelection < 0) currentSelection = options.size() - 1;
                SoundManager.playSound("menu_select");
                keyReleased = false;
            }
        } else if (inputManager.isKeyPressed(KeyEvent.VK_S) || inputManager.isKeyPressed(KeyEvent.VK_DOWN)) {
            if (keyReleased) {
                currentSelection++;
                if (currentSelection >= options.size()) currentSelection = 0;
                SoundManager.playSound("menu_select");
                keyReleased = false;
            }
        } else if (inputManager.isKeyPressed(KeyEvent.VK_ENTER)) {
            if (keyReleased) {
                executeSelection();
                keyReleased = false;
            }
        } else {
            keyReleased = true;
        }
    }

    public void executeSelection() {}

    @Override
    public void render(RenderSystem renderSystem) {
        int centerX = GS2DGame.width / 2;
        int centerY = GS2DGame.height / 2;

        renderSystem.clear(backgroundColor);
        Font titleFont = GS2DGame.buildGameFont(Font.BOLD, 42);
        renderSystem.drawText(title, centerX - (title.length() + titleFont.getSize()) - 250, centerY - titleFont.getSize(), titleFont, Color.CYAN);
        applyControls(renderSystem);
    }

    protected void applyControls(RenderSystem renderSystem) {
        int centerX = GS2DGame.width / 2;
        int centerY = GS2DGame.height / 2;

        int x = centerX + 125 - options.get(0).length()*3;
        int y = centerY + 150;

        Font baseFont = GS2DGame.buildGameFont(Font.PLAIN, 25);
        for (int i = 0; i < options.size(); i++) {
            if (i == currentSelection) {
                renderSystem.drawText("> " + options.get(i), x - 40, y + i * 50, baseFont, Color.YELLOW);
            } else {
                renderSystem.drawText(options.get(i), x - 40, y + i * 50, baseFont, Color.WHITE);
            }
        }
        renderSystem.drawText(Components.PRESS_ENTER, centerX - 390, GS2DGame.height - 10, GS2DGame.buildGameFont(Font.ITALIC, 10), Color.WHITE);
        renderSystem.drawText(Components.COPYRIGHT, centerX + 30, GS2DGame.height - 10, GS2DGame.buildGameFont(Font.ITALIC, 10), Color.WHITE);
    }

    @Override
    public void dispose() {}

    @Override
    public Camera getCamera() {
        return null;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
    public List<String> getOptions() {
        return this.options;
    }
    public void setBackgroundColor(Color newColor) {
        this.backgroundColor = newColor;
    }
    public Color getBackgroundColor() {
        return this.backgroundColor;
    }
}