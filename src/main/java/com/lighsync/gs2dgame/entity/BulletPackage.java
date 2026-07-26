package com.lighsync.gs2dgame.entity;

import com.lighsync.gs2d.platform.InputManager;
import com.lighsync.gs2d.platform.ResourceManager;
import com.lighsync.gs2d.renderer.RenderSystem;
import com.lighsync.gs2d.renderer.SpriteAnimation;
import com.lighsync.gs2d.world.Tilemap;
import com.lighsync.gs2d.world.entity.Entity;

import java.awt.image.BufferedImage;

public class BulletPackage extends Entity {
    private SpriteAnimation animSpin;

    public BulletPackage(float x, float y) {
        super(x, y, 48, 32);
        BufferedImage sheet = ResourceManager.loadImage("/assets/textures/entity/package_sheet.png");
        BufferedImage[] frames = ResourceManager.splitSpriteSheet(sheet, 1, 1, 48, 32);
        animSpin = new SpriteAnimation(frames, 0.15F);
    }

    @Override
    public void update(float deltaTime, InputManager inputManager, Tilemap tilemap) {
        animSpin.update(deltaTime);
    }

    @Override
    public void render(RenderSystem renderSystem) {
        renderSystem.drawSpriteWorld(animSpin.getCurrentFrame(), (int) x, (int) y, width, height, false);
    }
}