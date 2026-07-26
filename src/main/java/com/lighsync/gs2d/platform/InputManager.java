package com.lighsync.gs2d.platform;

import java.awt.event.*;

public class InputManager implements KeyListener, MouseListener, MouseMotionListener {
    private final boolean[] keys = new boolean[256];
    private int mouseX, mouseY;
    private final boolean[] mouseButtons = new boolean[5];

    public boolean isKeyPressed(int keyCode) {
        if (keyCode < 0 || keyCode >= keys.length) return false;
        return keys[keyCode];
    }

    public boolean isMousePressed(int button) {
        if (button < 0 || button >= mouseButtons.length) return false;
        return mouseButtons[button];
    }

    public int getMouseX() { return this.mouseX; }
    public int getMouseY() { return this.mouseY; }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < keys.length) keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < keys.length) keys[e.getKeyCode()] = false;
    }
    @Override public void keyTyped(KeyEvent e) {}

    @Override
    public void mousePressed(MouseEvent e ){
        if (e.getButton() < mouseButtons.length) mouseButtons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() < mouseButtons.length) mouseButtons[e.getButton()] = false;
    }
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e ){
        mouseX = e.getX();
        mouseY = e.getY();
    }
}