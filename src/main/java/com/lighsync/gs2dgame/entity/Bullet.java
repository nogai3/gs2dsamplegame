package com.lighsync.gs2dgame.entity;

import com.lighsync.gs2d.platform.InputManager;
import com.lighsync.gs2d.platform.ResourceManager;
import com.lighsync.gs2d.renderer.RenderSystem;
import com.lighsync.gs2d.renderer.SpriteAnimation;
import com.lighsync.gs2d.world.Tilemap;
import com.lighsync.gs2d.world.entity.Entity;

import java.awt.image.BufferedImage;

public class Bullet extends Entity {
    private SpriteAnimation animShoot;
    private final float step = 0.15F;
    private final float dirX;
    private final boolean facingLeft;

    public Bullet(float x, float y, boolean facingLeft) {
        super(x, y, 16, 16);
        BufferedImage sheet = ResourceManager.loadImage("/assets/textures/entity/bullet_sheet.png");
        BufferedImage[] frames = ResourceManager.splitSpriteSheet(sheet, 1, 4, 16, 16);
        animShoot = new SpriteAnimation(frames, 0.15F);
        this.facingLeft = facingLeft;
        this.dirX = facingLeft ? -1F : 1F;
    }

    @Override
    public void update(float deltaTime, InputManager inputManager, Tilemap tilemap) {
        animShoot.update(deltaTime);
        this.x += dirX * (step / deltaTime);

        if (hasTileCollision(tilemap)) {
            this.setActive(false);
        }
    }

    private boolean hasTileCollision(Tilemap tilemap) {
        return tilemap.isSolid((int) x, (int) y) ||
                tilemap.isSolid((int) (x + width), (int) y) ||
                tilemap.isSolid((int) x, (int) (y + height)) ||
                tilemap.isSolid((int) (x + width), (int) (y + height));
    }

    @Override
    public void render(RenderSystem renderSystem) {
        renderSystem.drawSpriteWorld(animShoot.getCurrentFrame(), (int) x, (int) y, width, height, facingLeft);
    }
}