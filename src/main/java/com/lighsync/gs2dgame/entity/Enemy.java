package com.lighsync.gs2dgame.entity;

import com.lighsync.gs2d.platform.InputManager;
import com.lighsync.gs2d.platform.ResourceManager;
import com.lighsync.gs2d.renderer.RenderSystem;
import com.lighsync.gs2d.renderer.SpriteAnimation;
import com.lighsync.gs2d.world.Tilemap;
import com.lighsync.gs2d.world.entity.Entity;

import java.awt.image.BufferedImage;

public class Enemy extends Entity {
    private final float speed = 100F;
    private float dirX = 1F;

    private SpriteAnimation animWalk;
    private final Player playerTarget;

    public Enemy(float x, float y, Player player) {
        super(x, y, 48, 48);
        this.playerTarget = player;

        BufferedImage sheet = ResourceManager.loadImage("/assets/textures/entity/enemy_sheet.png");
        BufferedImage[] frames = ResourceManager.splitSpriteSheet(sheet, 1, 4, 32, 32);
        animWalk = new SpriteAnimation(frames, 0.15F);
    }

    @Override
    public void update(float deltaTime, InputManager input, Tilemap tilemap) {
        animWalk.update(deltaTime);

        float diffX = playerTarget.getX() - this.x;
        float diffY = playerTarget.getY() - this.y;
        double distance = Math.sqrt(diffX * diffX + diffY * diffY);

        if (distance < 200) {
            dirX = (diffX > 0) ? 1F : -1F;
            float dirY = (diffY > 0) ? 1F : -1F;

            x += dirX * speed * deltaTime;
            if (hasTileCollision(tilemap)) x -= dirX * speed * deltaTime;

            y += dirY * speed * deltaTime;
            if (hasTileCollision(tilemap)) y -= dirY * speed * deltaTime;
        } else {
            x += dirX * speed * deltaTime;
            if (hasTileCollision(tilemap)) {
                x -= dirX * speed * deltaTime;
                dirX *= -1F;
            }
        }
    }

    private boolean hasTileCollision(Tilemap tilemap) {
        return tilemap.isSolid((int) x, (int) y) ||
                tilemap.isSolid((int) (x + width), (int) y) ||
                tilemap.isSolid((int) x, (int) (y + height)) ||
                tilemap.isSolid((int) (x + width), (int) (y + height));
    }

    @Override
    public void render(RenderSystem renderer) {
        boolean flipX = (dirX < 0);
        renderer.drawSpriteWorld(animWalk.getCurrentFrame(), (int) x, (int) y, width, height, flipX);
    }
}