package com.lighsync.gs2d.world.entity;

import com.lighsync.gs2d.platform.InputManager;
import com.lighsync.gs2d.renderer.RenderSystem;
import com.lighsync.gs2d.world.Tilemap;

import java.awt.*;

public abstract class Entity {
    protected float x, y;
    protected int width, height;
    protected boolean active = true;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void update(float deltaTime, InputManager inputManager, Tilemap tilemap);
    public abstract void render(RenderSystem renderSystem);

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    public boolean intersects(Entity other) {
        return this.getBounds().intersects(other.getBounds());
    }

    public boolean isActive() { return this.active; }
    public void setActive(boolean active) { this.active = active; }
    public float getX() { return this.x; }
    public float getY() { return this.y; }
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
}