package com.lighsync.gs2dgame.states.gui;

import com.lighsync.gs2d.platform.ResourceManager;
import com.lighsync.gs2d.renderer.RenderSystem;
import com.lighsync.gs2dgame.GS2DGame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DialogWidget {
    protected final String speakerName;
    protected final String fullText;
    protected final Font font;
    protected final Font nameFont;
    protected final Color color;
    protected final Color nameColor;
    protected final boolean animated;
    protected final BufferedImage background = ResourceManager.loadImage("/assets/textures/gui/dialog_background.png");

    private static final int DIALOG_HEIGHT = 225;
    private static final int PADDING_X = 40;
    private static final int PADDING_Y = 40;
    private static final int NAME_OFFSET_Y = 30;

    private float visibleCharsCount = 0F;
    private final float charsPerSecond = 35F;
    private List<String> cachedLines = null;

    public DialogWidget(String speakerName, String text, Font font, Color color, boolean animated) {
        this.speakerName = speakerName;
        this.fullText = text;
        this.font = font;
        this.nameFont = font.deriveFont(Font.BOLD, font.getSize2D() + 2F);
        this.color = color;
        this.nameColor = new Color(255, 215, 0);
        this.animated = animated;

        if (!animated) {
            this.visibleCharsCount = fullText.length();
        }
    }

    public DialogWidget(String text, Font font, Color color, boolean animated) {
        this(null, text, font, color, animated);
    }

    public void update(float deltaTime) {
        if (animated && visibleCharsCount < fullText.length()) {
            visibleCharsCount += charsPerSecond * deltaTime;
            if (visibleCharsCount > fullText.length()) {
                visibleCharsCount = fullText.length();
            }
        }
    }

    public void render(RenderSystem renderSystem) {
        int dialogY = GS2DGame.height - DIALOG_HEIGHT;
        renderSystem.drawTexture(background, 0, dialogY, GS2DGame.width, DIALOG_HEIGHT);

        int maxTextWidth = GS2DGame.width - (PADDING_X * 2);
        if (cachedLines == null) {
            cachedLines = wrapText(fullText, font, maxTextWidth, renderSystem);
        }

        int currentY = dialogY + PADDING_Y;

        if (speakerName != null && !speakerName.isEmpty()) {
            renderSystem.drawText(speakerName, PADDING_X, currentY, nameFont, nameColor);
            currentY += NAME_OFFSET_Y;
        }

        int visibleLimit = (int) visibleCharsCount;
        int charsProcessed = 0;
        int lineHeight = renderSystem.getFontHeight(font);

        for (String line : cachedLines) {
            if (charsProcessed >= visibleLimit) {
                break;
            }

            if (charsProcessed + line.length() <= visibleLimit) {
                renderSystem.drawText(line, PADDING_X, currentY, font, color);
                charsProcessed += line.length() + 1;
            } else {
                int charsForThisLine = visibleLimit - charsProcessed;
                String part = line.substring(0, charsForThisLine);
                renderSystem.drawText(part, PADDING_X, currentY, font, color);
                break;
            }

            currentY += lineHeight;
        }
    }

    private List<String> wrapText(String text, Font font, int maxWidth, RenderSystem renderSystem) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (currentLine.length() == 0) {
                currentLine.append(word);
            } else {
                String testLine = currentLine + " " + word;
                if (renderSystem.getTextWidth(testLine, font) <= maxWidth) {
                    currentLine.append(" ").append(word);
                } else {
                    lines.add(currentLine.toString());
                    currentLine = new StringBuilder(word);
                }
            }
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }

    public void skipAnimation() {
        this.visibleCharsCount = fullText.length();
    }

    public boolean isFinished() {
        return visibleCharsCount >= fullText.length();
    }
}