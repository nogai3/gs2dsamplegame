package com.lighsync.gs2d.world;

import com.lighsync.gs2d.renderer.Camera;
import com.lighsync.gs2d.renderer.RenderSystem;

import java.awt.image.BufferedImage;

public class Tilemap {
    private final int[][] map;
    private final int mapWidth;
    private final int mapHeight;
    private final int tileSize;

    private final BufferedImage[] tileTextures;

    public Tilemap(int[][] map, int tileSize, BufferedImage[] tileTextures) {
        this.map = map;
        this.mapWidth = map[0].length;
        this.mapHeight = map.length;
        this.tileSize = tileSize;
        this.tileTextures = tileTextures;
    }

    public void render(RenderSystem renderSystem, Camera camera, int screenW, int screenH) {
        int startX = Math.max(0, (int) camera.getX() / tileSize);
        int startY = Math.max(0, (int) camera.getY() / tileSize);

        int endX = Math.min(mapWidth, startX + (screenW / tileSize) + 2);
        int endY = Math.min(mapHeight, startY + (screenH / tileSize) + 2);

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                int tileId = map[y][x];

                if (tileId > 0 && tileId < tileTextures.length) {
                    BufferedImage texture = tileTextures[tileId];

                    int worldX = x * tileSize;
                    int worldY = y * tileSize;

                    renderSystem.drawSpriteWorld(texture, worldX, worldY, tileSize, tileSize, false);
                }
            }
        }
    }

    public boolean isSolid(int worldX, int worldY) {
        int tileX = worldX / tileSize;
        int tileY = worldY / tileSize;

        if (tileX < 0 || tileX >= mapWidth || tileY < 0 || tileY >= mapHeight) {
            return true;
        }
        return map[tileY][tileX] >= 2;
    }

    public int getTileSize() { return this.tileSize; }
    public int getPixelWidth() { return mapWidth * tileSize; }
    public int getPixelHeight() { return mapHeight * tileSize; }
}