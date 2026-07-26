package com.lighsync.gs2d.platform;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;

public class ResourceManager {
    public static BufferedImage loadImage(String path) {
        try {
            InputStream is = ResourceManager.class.getResourceAsStream(path);
            if (is != null) {
                return ImageIO.read(is);
            }
        } catch (Exception e) {
            System.err.println("Error when loading image: " + path);
        }
        return createDummySheet();
    }

    public static BufferedImage[] splitSpriteSheet(BufferedImage sheet, int columns, int rows, int spriteWidth, int spriteHeight) {
        if (sheet == null) {
            sheet = createDummySheet();
        }

        BufferedImage[] frames = new BufferedImage[columns * rows];
        int count = 0;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                int targetX = x * spriteWidth;
                int targetY = y * spriteHeight;

                if (targetX + spriteWidth > sheet.getWidth() || targetY + spriteHeight > sheet.getHeight()) {
                    frames[count] = createColoredSquare(spriteWidth, spriteHeight, Color.MAGENTA);
                } else {
                    frames[count] = sheet.getSubimage(targetX, targetY, spriteWidth, spriteHeight);
                }
                count++;
            }
        }
        return frames;
    }

    private static BufferedImage createDummySheet() {
        BufferedImage dummy = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dummy.createGraphics();
        g.setColor(Color.MAGENTA);
        g.fillRect(0, 0, 128, 128);
        g.dispose();
        return dummy;
    }

    private static BufferedImage createColoredSquare(int w, int h, Color color) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(color);
        g.fillRect(0, 0, w, h);
        g.setColor(color.brighter());
        g.drawRect(0, 0, w - 1, h - 1);
        g.dispose();
        return img;
    }

    public static Font loadFont(String path, float size) {
        try (InputStream is = ResourceManager.class.getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("File not found: " + path);
                return new Font("Arial", Font.PLAIN, (int) size);
            }

            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);

            return font.deriveFont(size);
        } catch (FontFormatException | IOException e) {
            System.err.println("Error when loading font at " + path + ": " + e.getMessage());
            return new Font("Arial", Font.PLAIN, (int) size);
        }
    }

    public static String readTextFile(String path) {
        try (InputStream is = ResourceManager.class.getResourceAsStream(path)) {
            if (is == null) return null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            System.err.println("Error reading text file: " + path);
            return null;
        }
    }
}