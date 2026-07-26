package com.lighsync.gs2d.world.particle;

import java.awt.Font;
import java.awt.Color;

public class TextParticle extends Particle {
    private final String text;
    private final Font font;

    public TextParticle(float x, float y, float velX, float velY, float lifeTime, Color color, String text, Font font) {
        super(x, y, velX, velY, lifeTime, color, font.getSize());
        this.text = text;
        this.font = font;
    }

    public String getText() { return this.text; }
    public Font getFont() { return this.font; }
}