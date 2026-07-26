package com.lighsync.gs2dgame.entity;

import com.lighsync.gs2d.platform.InputManager;
import com.lighsync.gs2d.platform.ResourceManager;
import com.lighsync.gs2d.renderer.RenderSystem;
import com.lighsync.gs2d.renderer.SpriteAnimation;
import com.lighsync.gs2d.world.Tilemap;
import com.lighsync.gs2d.world.entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Coin extends Entity {
    private SpriteAnimation animSpin;

    public Coin(float x, float y) {
        super(x, y, 32, 32);
        BufferedImage sheet = ResourceManager.loadImage("/assets/textures/entity/coin_sheet.png");
        BufferedImage[] frames = ResourceManager.splitSpriteSheet(sheet, 1, 4, 16, 16);
        animSpin = new SpriteAnimation(frames, 0.15F);
    }

    @Override
    public void update(float deltaTime, InputManager input, Tilemap tilemap) {
        animSpin.update(deltaTime);
    }

    @Override
    public void render(RenderSystem renderer) {
        renderer.drawSpriteWorld(animSpin.getCurrentFrame(), (int) x, (int) y, width, height, false);
    }
}