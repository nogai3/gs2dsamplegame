package com.lighsync.gs2d.world.particle;

import java.awt.*;

public class Particle {
    public float x, y;
    public float velX, velY;
    public float lifeTime;
    public float maxLifeTime;
    public Color color;
    public int size;

    public Particle(float x, float y, float velX, float velY, float lifeTime, Color color, int size) {
        this.x = x;
        this.y = y;
        this.velX = velX;
        this.velY = velY;
        this.lifeTime = lifeTime;
        this.maxLifeTime = lifeTime;
        this.color = color;
        this.size = size;
    }

    public boolean update(float deltaTime) {
        x += velX * deltaTime;
        y += velY * deltaTime;
        lifeTime -= deltaTime;
        return lifeTime <= 0;
    }
}