package com.lighsync.gs2d.renderer.locale;

import java.util.IllegalFormatException;

public interface Component {
    String getString();

    static Component literal(String text) {
        return new LiteralComponent(text);
    }

    static Component translatable(String key, Object... args) {
        return new TranslatableComponent(key, args);
    }

    static Component empty() {
        return new LiteralComponent("");
    }

    class LiteralComponent implements Component {
        private final String text;

        public LiteralComponent(String text) {
            this.text = text != null ? text : "";
        }

        @Override
        public String getString() {
            return text;
        }

        @Override
        public String toString() {
            return getString();
        }
    }

    class TranslatableComponent implements Component {
        private final String key;
        private final Object[] args;

        public TranslatableComponent(String key, Object... args) {
            this.key = key;
            this.args = args;
        }

        @Override
        public String getString() {
            String translated = Language.getText(key);
            if (args != null && args.length > 0) {
                try {
                    return String.format(translated, args);
                } catch (IllegalFormatException e) {
                    return translated;
                }
            }
            return translated;
        }

        @Override
        public String toString() {
            return getString();
        }
    }
}