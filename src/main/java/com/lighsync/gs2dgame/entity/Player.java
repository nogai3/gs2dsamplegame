package com.lighsync.gs2dgame.entity;

import com.lighsync.gs2d.platform.InputManager;
import com.lighsync.gs2d.platform.ResourceManager;
import com.lighsync.gs2d.platform.SoundManager;
import com.lighsync.gs2d.renderer.RenderSystem;
import com.lighsync.gs2d.renderer.SpriteAnimation;
import com.lighsync.gs2d.states.StateManager;
import com.lighsync.gs2d.world.Tilemap;
import com.lighsync.gs2d.world.entity.Entity;
import com.lighsync.gs2d.world.entity.EntityManager;
import com.lighsync.gs2dgame.GS2DGame;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    private final float speed = 200F;
    private boolean facingLeft = false;
    private boolean isMoving = false;

    private SpriteAnimation animWalk;
    private BufferedImage idleSprite;
    private EntityManager entityManager;
    private boolean shootKeyPressed = true;
    private int bullets = 20;

    public Player(float x, float y, EntityManager entityManager) {
        super(x, y, 40, 56);
        this.entityManager = entityManager;
        initGraphics();
    }

    private void initGraphics() {
        BufferedImage playerSheet = ResourceManager.loadImage("/assets/textures/entity/player_sheet.png");
        BufferedImage[] walkFrames = ResourceManager.splitSpriteSheet(playerSheet, 1, 4, 32, 32);
        animWalk = new SpriteAnimation(walkFrames, 0.12F);
        if (walkFrames.length > 0) {
            idleSprite = walkFrames[0];
        }
    }

    @Override
    public void update(float deltaTime, InputManager inputManager, Tilemap tilemap) {
        float moveX = 0;
        float moveY = 0;

        if (inputManager.isKeyPressed(KeyEvent.VK_A) || inputManager.isKeyPressed(KeyEvent.VK_LEFT)) { moveX -= speed * deltaTime; facingLeft = true; }
        if (inputManager.isKeyPressed(KeyEvent.VK_D) || inputManager.isKeyPressed(KeyEvent.VK_RIGHT)) { moveX += speed * deltaTime; facingLeft = false; }
        if (inputManager.isKeyPressed(KeyEvent.VK_W) || inputManager.isKeyPressed(KeyEvent.VK_UP)) moveY -= speed * deltaTime;
        if (inputManager.isKeyPressed(KeyEvent.VK_S) || inputManager.isKeyPressed(KeyEvent.VK_DOWN)) moveY += speed * deltaTime;
        if (inputManager.isKeyPressed(KeyEvent.VK_ENTER)) {
            if (shootKeyPressed) {
                shoot();
                shootKeyPressed = false;
            }
        }  else {
            shootKeyPressed = true;
        }
        if (inputManager.isKeyPressed(KeyEvent.VK_ESCAPE)) GS2DGame.requestChangeState("menu");

        isMoving = (moveX != 0 || moveY != 0);

        x += moveX;
        if (hasTileCollision(tilemap)) {
            if (moveX > 0) {
                x = ((float) (int) (x + width) / tilemap.getTileSize()) * tilemap.getTileSize() - width - 1;
            } else if (moveX < 0) {
                x = ((float) (int) x / tilemap.getTileSize()) * tilemap.getTileSize() + tilemap.getTileSize();
            }
        }

        y += moveY;
        if (hasTileCollision(tilemap)) {
            if (moveY > 0) {
                y = ((float) (int) (y + height) / tilemap.getTileSize()) * tilemap.getTileSize() - height - 1;
            } else if (moveY < 0) {
                y = ((float) (int) y / tilemap.getTileSize()) * tilemap.getTileSize() + tilemap.getTileSize();
            }
        }

        if (isMoving) animWalk.update(deltaTime);
        else animWalk.reset();
    }

    private void shoot() {
        if (bullets <= 0) return;
        float bulletX = facingLeft ? (x + 15) : (x + width + 5);
        float bulletY = y + (height / 2F) - 6;

        Bullet bullet = new Bullet(bulletX, bulletY, facingLeft);
        entityManager.addEntity(bullet);
        SoundManager.playSound("shoot");
        bullets--;
    }

    private boolean hasTileCollision(Tilemap tilemap) {
        return tilemap.isSolid((int) x, (int) y) ||
                tilemap.isSolid((int) (x + width), (int) y) ||
                tilemap.isSolid((int) x, (int) (y + height)) ||
                tilemap.isSolid((int) (x + width), (int) (y + height));
    }

    @Override
    public void render(RenderSystem renderSystem) {
        BufferedImage frame = isMoving ? animWalk.getCurrentFrame() : idleSprite;
        renderSystem.drawSpriteWorld(frame, (int) x, (int) y, width, height, facingLeft);
    }

    public int getBullets() {
        return this.bullets;
    }

    public void setBullets(int bullets) {
        this.bullets = bullets;
    }
}