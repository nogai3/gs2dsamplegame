package com.lighsync.gs2d.platform;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SoundManager {
    private static final Map<String, Clip> soundEffects = new HashMap<>();
    private static Clip backgroundMusic = null;

    public static void loadSound(String name, String path) {
        try {
            InputStream is = SoundManager.class.getResourceAsStream(path);
            InputStream bufferedIn = new BufferedInputStream(Objects.requireNonNull(is));
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);

            DataLine.Info info = new DataLine.Info(Clip.class, audioIn.getFormat());
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioIn);

            soundEffects.put(name, clip);
        } catch (Exception e) {
            System.err.println("Error when sound loading: " + name + " at path: " + path);
            e.printStackTrace();
        }
    }

    public static void playSound(String name) {
        Clip clip = soundEffects.get(name);
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public static void playMusic(String path) {
        try {
            stopMusic();

            InputStream is = SoundManager.class.getResourceAsStream(path);
            InputStream bufferedIn = new BufferedInputStream(Objects.requireNonNull(is));
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);

            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioIn);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusic.start();
        } catch (Exception e) {
            System.err.println("Error when music loading: " + path);
            e.printStackTrace();
        }
    }

    public static void stopMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
            backgroundMusic.close();
        }
    }
}