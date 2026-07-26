package com.lighsync.gs2d.renderer;

public class Camera {
    private float x, y;
    private final int viewWidth, viewHeight;
    private float lerpSpeed = 0.1F;

    public Camera(int viewWidth, int viewHeight) {
        this.x = 0;
        this.y = 0;
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
    }

    public void focusOn(float targetX, float targetY, int targetW, int targetH, int mapWPixels, int mapHPixels) {
        float targetCamX = targetX + targetW / 2F - viewWidth / 2F;
        float targetCamY = targetY + targetH / 2F - viewHeight / 2F;

        x += (targetCamX - x) * lerpSpeed;
        y += (targetCamY - y) * lerpSpeed;

        if (x < 0) x = 0;
        if (mapWPixels > viewWidth) {
            if (x > mapWPixels - viewWidth) x = mapWPixels - viewWidth;
        } else {
            x = 0;
        }

        if (y < 0) y = 0;
        if (mapHPixels > viewHeight) {
            if (y > mapHPixels - viewHeight) y = mapHPixels - viewHeight;
        } else {
            y = 0;
        }
    }

    public float getX() { return this.x; }
    public float getY() { return this.y; }
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setLerpSpeed(float speed) { this.lerpSpeed = speed; }
}