package com.lighsync.gs2d.renderer;

import java.awt.image.BufferedImage;

public class SpriteAnimation {
    private final BufferedImage[] frames;
    private int currentFrame;

    private final float frameDuration;
    private float elapsedTime;

    private boolean isPlaying = true;

    public SpriteAnimation(BufferedImage[] frames, float speedSecs) {
        this.frames = frames;
        this.frameDuration = speedSecs;
        this.currentFrame = 0;
        this.elapsedTime = 0F;
    }

    public void update(float deltaTime) {
        if (!isPlaying || frames.length <= 1) return;

        elapsedTime += deltaTime;
        while (elapsedTime >= frameDuration) {
            elapsedTime -= frameDuration;
            currentFrame++;

            if (currentFrame >= frames.length) {
                currentFrame = 0;
            }
        }
    }

    public BufferedImage getCurrentFrame() {
        if (frames.length == 0) return null;
        return frames[currentFrame];
    }

    public void play() { isPlaying = true; }
    public void pause() { isPlaying = false; }
    public void reset() { currentFrame = 0; elapsedTime = 0F; }

    public int getCurrentFrameIndex() { return currentFrame; }
}