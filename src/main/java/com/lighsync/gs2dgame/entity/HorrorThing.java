package com.lighsync.gs2dgame.entity;

import com.lighsync.gs2d.platform.InputManager;
import com.lighsync.gs2d.platform.ResourceManager;
import com.lighsync.gs2d.renderer.Camera;
import com.lighsync.gs2d.renderer.RenderSystem;
import com.lighsync.gs2d.renderer.SpriteAnimation;
import com.lighsync.gs2d.world.Tilemap;
import com.lighsync.gs2d.world.entity.Entity;
import com.lighsync.gs2dgame.GS2DGame;
import com.lighsync.gs2dgame.states.gui.DialogWidget;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HorrorThing extends Entity {
    private SpriteAnimation animation;
    private final Camera camera;
    private boolean mouseReleased = true;
    protected DialogWidget widget = new DialogWidget("glackus", "It`s a test dialog message!", GS2DGame.buildGameFont(Font.PLAIN, 14), Color.WHITE, true);
    protected boolean allow = false;

    public HorrorThing(float x, float y, Camera camera) {
        super(x, y, 48, 48);
        this.camera = camera;
        BufferedImage sheet = ResourceManager.loadImage("/assets/textures/entity/horror_thing.png");
        BufferedImage[] frames = ResourceManager.splitSpriteSheet(sheet, 1, 1, 48, 48);
        animation = new SpriteAnimation(frames, 0.15F);
    }

    @Override
    public void update(float deltaTime, InputManager inputManager, Tilemap tilemap) {
        animation.update(deltaTime);

        int screenX = (int) this.x - (int) camera.getX();
        int screenY = (int) this.y - (int) camera.getY();

        Rectangle screenBounds = new Rectangle(screenX, screenY, width, height);

        int mouseX = inputManager.getMouseX();
        int mouseY = inputManager.getMouseY();

        if (inputManager.isMousePressed(1)) {
            if (mouseReleased && screenBounds.contains(mouseX, mouseY)) {
                mouseReleased = false;
                // GS2DGame.requestChangeState("horror");
                allow = !allow;
            }
        } else {
            mouseReleased = true;
        }
        if (allow) {
            widget.update(deltaTime);
        }
    }

    @Override
    public void render(RenderSystem renderSystem) {
        renderSystem.drawSpriteWorld(animation.getCurrentFrame(), (int) x, (int) y, width, height, false);

        if (allow) {
            widget.render(renderSystem);
        }
    }
}