package com.lighsync.gs2dgame.states.gui;

import com.lighsync.gs2d.platform.InputManager;
import com.lighsync.gs2d.platform.SoundManager;
import com.lighsync.gs2d.renderer.Camera;
import com.lighsync.gs2d.renderer.RenderSystem;
import com.lighsync.gs2d.states.StateManager;
import com.lighsync.gs2dgame.Components;
import com.lighsync.gs2dgame.GS2DGame;
import com.lighsync.gs2dgame.states.base.GuiScreenBase;

import java.awt.*;
import java.util.ArrayList;

public class HorrorScreen extends GuiScreenBase {
    protected boolean scared = false;
    protected int ticks;

    public HorrorScreen(StateManager stateManager) {
        super(stateManager, new ArrayList<>(), "", new Color(0, 0, 0));
    }

    @Override
    public void init() {
        options.clear();
        options.add(Components.YES);
        options.add(Components.NO);
        currentSelection = 0;
        ticks = 0;

        SoundManager.loadSound("horror", "/assets/sounds/horror.wav");
        SoundManager.playSound("horror");

        super.init();
    }

    @Override
    public void update(float deltaTime, InputManager inputManager) {
        if (options.isEmpty()) return;
        if (ticks >= 1000 && scared) {
            stateManager.changeState("titlemenu");
        }
        super.update(deltaTime, inputManager);
    }


    @Override
    public void executeSelection() {
        String selectedOption = options.get(currentSelection);

        switch (selectedOption.toUpperCase()) {
            case "YES", "ДА" -> {
                scared = true;
                options.remove(Components.YES);
                options.remove(Components.NO);
                options.add(Components.OK);
                currentSelection = 0;
            }
            case "NO", "НЕТ" -> {
                options.remove(Components.NO);
                currentSelection = 0;
                SoundManager.playSound("menu_select");
            }
        }
    }

    @Override
    public void render(RenderSystem renderSystem) {
        int centerX = GS2DGame.width / 2;
        int centerY = GS2DGame.height / 2;

        renderSystem.clear(new Color(0, 0, 0));
        renderSystem.drawText(Components.HORROR_TITLE, centerX - 325, centerY - 200, GS2DGame.buildGameFont(Font.BOLD, 42), Color.RED);
        renderSystem.drawText(Components.HORROR_SUBTITLE, centerX - 125, centerY - 150, GS2DGame.buildGameFont(Font.BOLD | Font.ITALIC, 32), Color.RED);

        applyControls(renderSystem);
    }

    @Override
    public void applyChooseControls(InputManager inputManager) {
        super.applyChooseControls(inputManager);
    }

    @Override
    protected void applyControls(RenderSystem renderSystem) {
        int centerX = GS2DGame.width / 2;
        int centerY = GS2DGame.height / 2;

        int x = centerX - 10;

        Font baseFont = GS2DGame.buildGameFont(Font.PLAIN, 25);
        for (int i = 0; i < options.size(); i++) {
            if (i == currentSelection) {
                renderSystem.drawText("> " + options.get(i), x - 40, centerY + i * 50, baseFont, Color.ORANGE);
            } else {
                renderSystem.drawText(options.get(i), x - 40, centerY + i * 50, baseFont, Color.RED);
            }
        }
        renderSystem.drawText(Components.PRESS_ENTER, centerX - 390, GS2DGame.height - 10, GS2DGame.buildGameFont(Font.ITALIC, 10), Color.RED);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public Camera getCamera() {
        return super.getCamera();
    }
}