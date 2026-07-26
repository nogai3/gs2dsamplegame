package com.lighsync.gs2d.world.particle;

import com.lighsync.gs2d.renderer.RenderSystem;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticleManager {
    private final List<Particle> particles = new ArrayList<>();
    private final Random random = new Random();

    public void spawnExplosion(float centerX, float centerY, Color color, int count) {
        for (int i = 0; i < count; i++) {
            double angle = random.nextDouble() * Math.PI * 2;
            float speed = 50F + random.nextFloat() * 150F;
            float velX = (float) Math.cos(angle) * speed;
            float velY = (float) Math.sin(angle) * speed;

            float lifeTime = 0.3F + random.nextFloat() * 0.5F;
            int size = 3 + random.nextInt(4);

            particles.add(new Particle(centerX, centerY, velX, velY, lifeTime, color, size));
        }
    }

    public void spawnText(float x, float y, String text, Font font, Color color) {
        float offsetX = (this.random.nextFloat() - 0.5F) * 20F;
        float velX = (this.random.nextFloat() - 0.5F) * 15F;
        float velY = -40F;
        float lifeTime = 0.8F;
        this.particles.add(new TextParticle(x + offsetX, y, velX, velY, lifeTime, color, text, font));
    }

    public void update(float deltaTime) {
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i).update(deltaTime)) {
                particles.remove(i);
                i--;
            }
        }
    }

    public void render(RenderSystem renderSystem) {
        for (Particle p : particles) {
            float alphaRation = Math.clamp(p.lifeTime / p.maxLifeTime, 0F, 1F);
            int alpha = (int) (alphaRation * 255);
            Color fadeColor = new Color(p.color.getRed(), p.color.getGreen(), p.color.getBlue(), alpha);

            renderSystem.fillRectWorld((int) p.x, (int) p.y, p.size, p.size, fadeColor);
        }
    }

    public void clear() {
        particles.clear();
    }
}