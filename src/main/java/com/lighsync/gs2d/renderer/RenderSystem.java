package com.lighsync.gs2d.renderer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RenderSystem {
    private Graphics2D g;
    private Composite defaultComposite;
    private int width, height;
    private float camX, camY = 0;

    public void begin(Graphics2D g2d, int width, int height, Camera camera) {
        this.g = g2d;
        this.width = width;
        this.height = height;

        if (camera != null) {
            this.camX = camera.getX();
            this.camY = camera.getY();
        } else {
            this.camX = 0;
            this.camY = 0;
        }

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        this.defaultComposite = g.getComposite();
    }

    public void clear(Color color) {
        g.setColor(color);
        g.fillRect(0, 0, width, height);
    }

    public void drawRect(int x, int y, int w, int h, Color color) {
        g.setColor(color);
        g.drawRect(x, y, w, h);
    }

    public void fillRect(int x, int y, int w, int h, Color color) {
        g.setColor(color);
        g.fillRect(x, y, w, h);
    }

    public void fillRectWorld(int worldX, int worldY, int w, int h, Color color) {
        int screenX = worldX - (int) camX;
        int screenY = worldY - (int) camY;
        if (screenX + w < 0 || screenX > width || screenY + h < 0 || screenY > height) return;
        g.setColor(color);
        g.fillRect(screenX, screenY, w, h);
    }

    public void drawTexture(BufferedImage image, int x, int y) {
        if (image != null) g.drawImage(image, x, y, null);
    }

    public void drawTexture(BufferedImage image, int x, int y, int w, int h) {
        if (image != null) g.drawImage(image, x, y, w, h, null);
    }

    public void drawSprite(SpriteAnimation animation, int x, int y) {
        if (animation != null) {
            drawTexture(animation.getCurrentFrame(), x, y);
        }
    }

    public void drawSprite(SpriteAnimation animation, int x, int y, int w, int h) {
        if (animation != null) {
            drawTexture(animation.getCurrentFrame(), x, y, w, h);
        }
    }

    public void drawSprite(BufferedImage frame, int x, int y, int w, int h, boolean flipX) {
        if (frame == null || g == null) return;

        if (flipX) {
            g.drawImage(frame, x + w, y, -w, h, null);
        } else {
            g.drawImage(frame, x + w, y, w, h, null);
        }
    }

    public void drawSpriteWorld(BufferedImage frame, int worldX, int worldY, int w, int h, boolean flipX) {
        int screenX = worldX - (int) camX;
        int screenY = worldY - (int) camY;

        if (screenX + w < 0 || screenX > this.width || screenY + h < 0 || screenY > this.height) {
            return;
        }

        if (flipX) {
            g.drawImage(frame, screenX + w, screenY, -w, h, null);
        } else {
            g.drawImage(frame, screenX, screenY, w, h, null);
        }
    }

    public void drawSpriteScreen(BufferedImage frame, int screenX, int screenY, int w, int h) {
        if (frame != null) g.drawImage(frame, screenX, screenY, w, h, null);
    }

    public void drawText(String text, int x, int y, Font font, Color color) {
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, x, y);
    }

    public void drawTextWorld(String text, int worldX, int worldY, Font font, Color color) {
        int screenX = worldX - (int) camX;
        int screenY = worldY - (int) camY;
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, screenX, screenY);
    }

    public int getTextWidth(String text, Font font) {
        if (g == null || text == null || text.isEmpty()) return 0;
        return g.getFontMetrics(font).stringWidth(text);
    }

    public int getFontHeight(Font font) {
        if (g == null) return 0;
        return g.getFontMetrics(font).getHeight();
    }

    public void setAlpha(float alpha) {
        if (g == null) return;

        if (alpha < 0F) alpha = 0F;
        if (alpha > 1F) alpha = 1F;

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }

    public void resetAlpha() {
        if (g == null) return;
        g.setComposite(defaultComposite);
    }

    public void end() {
        if (g != null) {
            g.dispose();
        }
    }
}