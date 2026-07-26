package com.lighsync.gs2dgame;

import com.lighsync.gs2d.GS2D;
import com.lighsync.gs2d.platform.ResourceManager;
import com.lighsync.gs2d.platform.SoundManager;
import com.lighsync.gs2d.renderer.locale.Language;
import com.lighsync.gs2d.states.StateManager;
import com.lighsync.gs2dgame.states.base.GameplayBase;
import com.lighsync.gs2dgame.states.gui.HorrorScreen;
import com.lighsync.gs2dgame.states.gui.MainMenuScreen;

import java.awt.*;
import java.util.Locale;

public class GS2DGame {
    public static int width = 800;
    public static int height = 600;

    private static final GS2D engine = new GS2D("GS2D Game", width, height);
    private static final StateManager stateManager = engine.getStateManager();
    private static Font font;

    public static void main(String[] args) {
        font = ResourceManager.loadFont("/assets/fonts/p2p.ttf", 14F);
        Language.loadLanguage("en_us");
        SoundManager.loadSound("menu_select", "/assets/sounds/select.wav");
        stateManager.registerState("titlemenu", new MainMenuScreen(stateManager));
        stateManager.registerState("horror", new HorrorScreen(stateManager));
        stateManager.registerState("gameplay", new GameplayBase());

        stateManager.changeState("titlemenu");
        engine.start();

        Locale locale = Locale.getDefault();
        System.out.println(locale.getLanguage() + locale.getCountry());
    }

    public static void requestChangeState(String stateName) {
        stateManager.changeState(stateName);
    }

    public static void quit() {
        engine.quit();
    }

    public static Font buildGameFont(int style, int size) {
        if (font != null) {
            return font.deriveFont(style, size);
        }
        return new Font("Arial", style, size);
    }
}