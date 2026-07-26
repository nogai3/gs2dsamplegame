package com.lighsync.gs2d;

import com.lighsync.gs2d.platform.InputManager;
import com.lighsync.gs2d.platform.WindowManager;
import com.lighsync.gs2d.renderer.Camera;
import com.lighsync.gs2d.renderer.RenderSystem;
import com.lighsync.gs2d.states.StateManager;

import java.awt.*;

public class GS2D implements Runnable {
    private final WindowManager windowManager;
    private final InputManager inputManager;
    private final RenderSystem renderSystem;
    private final StateManager stateManager;

    private boolean running = false;
    private Thread gameThread;

    private final int width;
    private final int height;

    public GS2D(String title, int width, int height) {
        this.width = width;
        this.height = height;

        this.windowManager = new WindowManager(title, width, height);
        this.inputManager = new InputManager();
        this.renderSystem  = new RenderSystem();
        this.stateManager = new StateManager();

        windowManager.getCanvas().addKeyListener(inputManager);
        windowManager.getCanvas().addMouseListener(inputManager);
        windowManager.getCanvas().addMouseMotionListener(inputManager);
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        windowManager.show();
        gameThread = new Thread(this, "GameLoopThread");
        gameThread.start();
    }

    @Override
    public void run() {
        final double UPS = 60.0;
        final double TIME_BEFORE_UPDATE = 1_000_000_000.0 / UPS;

        long lastTime = System.nanoTime();
        double delta = 0;

        long timer = System.currentTimeMillis();
        int frames = 0;
        int updates = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / TIME_BEFORE_UPDATE;
            lastTime = now;

            while (delta >= 1) {
                stateManager.update(1F / (float)UPS, inputManager);
                updates++;
                delta--;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void render() {
        Graphics2D g2d = windowManager.getDrawGraphics();
        if (g2d == null) return;

        Camera activeCamera = null;
        if (stateManager.getCurrentState() != null) {
            activeCamera = stateManager.getCurrentState().getCamera();
        }

        renderSystem.begin(g2d, width, height, activeCamera);
        renderSystem.clear(new Color(20, 20, 30));
        stateManager.render(renderSystem);

        renderSystem.end();
        windowManager.swapBuffers();
    }

    public void quit() {
        this.running = false;
        System.exit(0);
    }

    public StateManager getStateManager() {
        return this.stateManager;
    }
}