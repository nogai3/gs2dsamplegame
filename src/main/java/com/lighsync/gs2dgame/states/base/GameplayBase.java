package com.lighsync.gs2dgame.states.base;

import com.lighsync.gs2d.platform.InputManager;
import com.lighsync.gs2d.platform.ResourceManager;
import com.lighsync.gs2d.platform.SoundManager;
import com.lighsync.gs2d.renderer.Camera;
import com.lighsync.gs2d.renderer.RenderSystem;
import com.lighsync.gs2d.states.GameState;
import com.lighsync.gs2d.world.Tilemap;
import com.lighsync.gs2d.world.entity.Entity;
import com.lighsync.gs2d.world.entity.EntityManager;
import com.lighsync.gs2d.world.particle.ParticleManager;
import com.lighsync.gs2dgame.Components;
import com.lighsync.gs2dgame.GS2DGame;
import com.lighsync.gs2dgame.entity.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameplayBase implements GameState {
    protected Tilemap tilemap;
    protected Camera camera;
    protected EntityManager entityManager;
    protected ParticleManager particleManager;
    protected Player player;

    protected int score = 0;
    protected int playerHealth = 3;
    protected int playerBullets = 0;

    @Override
    public void init() {
        camera = new Camera(GS2DGame.width, GS2DGame.height);
        camera.setLerpSpeed(0.08F);

        SoundManager.loadSound("coin", "/assets/sounds/coin.wav");
        SoundManager.loadSound("hit", "/assets/sounds/hit.wav");
        SoundManager.loadSound("shoot", "/assets/sounds/shoot.wav");
        SoundManager.playMusic("/assets/sounds/bg_music.wav");

        BufferedImage tileset = ResourceManager.loadImage("/assets/textures/tileset.png");
        BufferedImage[] tileTextures = ResourceManager.splitSpriteSheet(tileset, 1, 4, 16, 16);
        tilemap = new Tilemap(generateMap(), 64, tileTextures);

        particleManager = new ParticleManager();
        entityManager = new EntityManager();

        player = new Player(100, 100, entityManager);
        entityManager.addEntity(player);
        playerBullets = player.getBullets();

        entityManager.addEntity(new Coin(250, 120));
        entityManager.addEntity(new Coin(450, 200));
        entityManager.addEntity(new BulletPackage(250, 180));
        entityManager.addEntity(new HorrorThing(600, 250, camera));
        entityManager.addEntity(new Enemy(600, 120, player));
    }

    @Override
    public void update(float deltaTime, InputManager input) {
        entityManager.update(deltaTime, input, tilemap);
        particleManager.update(deltaTime);

        for (Entity e : entityManager.getEntities()) {
            if (!e.isActive()) continue;

            if (e instanceof Coin && player.intersects(e)) {
                e.setActive(false);
                score += 100;
                SoundManager.playSound("coin");
                particleManager.spawnExplosion(e.getX() + e.getWidth() / 2F, e.getY() + e.getHeight() / 2F, Color.YELLOW, 15);
            }

            if (e instanceof BulletPackage && player.intersects(e)) {
                e.setActive(false);
                playerBullets += 20;
                player.setBullets(playerBullets);
                SoundManager.playSound("coin");
                particleManager.spawnExplosion(e.getX() + e.getWidth() / 2F, e.getY() + e.getHeight() / 2F, Color.ORANGE, 15);
            }

            if (e instanceof Enemy && player.intersects(e)) {
                playerHealth--;
                SoundManager.playSound("hit");
                particleManager.spawnExplosion(player.getX() + player.getWidth() / 2F, player.getY() + player.getHeight() / 2F, Color.RED, 30);

                player.setActive(false);
                player = new Player(100, 100, entityManager);
                entityManager.addEntity(player);

                e.setActive(false);
                Enemy newEnemy = new Enemy(e.getX(), e.getY(), player);
                entityManager.addEntity(newEnemy);

                break;
            }

            if (e instanceof Bullet) {
                for (Entity other : entityManager.getEntities()) {
                    if (other instanceof Enemy && other.isActive() && e.intersects(other)) {
                        other.setActive(false);
                        e.setActive(false);
                        score += 200;

                        particleManager.spawnExplosion(
                                other.getX() + other.getWidth() / 2F, other.getY() + other.getHeight() / 2F,
                                Color.RED, 20
                        );
                        break;
                    }
                }
            }
        }

        camera.focusOn(player.getX(), player.getY(), player.getWidth(), player.getHeight(),
                tilemap.getPixelWidth(), tilemap.getPixelHeight());
        playerBullets = player.getBullets();
    }

    protected int[][] generateMap() {
        return new int[][]{
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
            {2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 2},
            {2, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 2},
            {2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
        };
    }

    @Override
    public void render(RenderSystem renderer) {
        renderer.clear(Color.BLACK);

        tilemap.render(renderer, camera, GS2DGame.width, GS2DGame.height);
        entityManager.render(renderer);
        particleManager.render(renderer);

        renderer.drawText(Components.SCORE + score, 20, 40, GS2DGame.buildGameFont(Font.BOLD, 20), Color.YELLOW);
        renderer.drawText(Components.HEALTH + playerHealth, 20, 70, GS2DGame.buildGameFont(Font.BOLD, 20), Color.RED);
        renderer.drawText(Components.AMMO + playerBullets, 20, 100, GS2DGame.buildGameFont(Font.BOLD, 20), Color.CYAN);

        if (playerHealth <= 0) {
            renderer.drawText(Components.GAME_OVER, 280, 300, GS2DGame.buildGameFont(Font.BOLD, 44), Color.RED);
        }
    }

    @Override
    public void dispose() {
        entityManager.clear();
        particleManager.clear();
        SoundManager.stopMusic();
    }

    @Override
    public Camera getCamera() {
        return this.camera;
    }
}