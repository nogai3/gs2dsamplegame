package com.lighsync.gs2d.platform;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class WindowManager {
    private final Frame frame;
    private final Canvas canvas;
    private BufferStrategy bufferStrategy;

    public WindowManager(String title, int width, int height) {
        frame = new Frame();
        frame.setTitle(title);
        frame.setResizable(false);
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        canvas = new Canvas();
        Dimension size = new Dimension(width, height);
        canvas.setPreferredSize(size);
        canvas.setMinimumSize(size);
        canvas.setMaximumSize(size);

        frame.add(canvas);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
    }

    public void initBufferStrategy() {
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
    }

    public void show() {
        frame.setVisible(true);
        initBufferStrategy();
        canvas.requestFocus();
    }

    public Graphics2D getDrawGraphics() {
        if (bufferStrategy == null) return null;
        return (Graphics2D) bufferStrategy.getDrawGraphics();
    }

    public void swapBuffers() {
        if (bufferStrategy != null) {
            bufferStrategy.show();
        }
    }

    public void close() {
        frame.dispose();
        System.exit(0);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}