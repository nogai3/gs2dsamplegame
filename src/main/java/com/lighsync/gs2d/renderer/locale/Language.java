package com.lighsync.gs2d.renderer.locale;

import com.lighsync.gs2d.platform.ResourceManager;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Language {
    private static final Map<String, String> translations = new HashMap<>();
    private static String currentLanguage;
    private static final String TRANSLATIONS_PATH = "/assets/lang/";

    static {
        Locale locale = Locale.getDefault();
        String defaultLang = locale.getLanguage().toLowerCase() + "_" + locale.getCountry().toLowerCase();

        if (!loadLanguage(defaultLang)) {
            loadLanguage("en_us");
        }
    }

    public static boolean loadLanguage(String langKey) {
        String path = TRANSLATIONS_PATH + langKey + ".json";
        String content = ResourceManager.readTextFile(path);

        if (content == null) {
            System.err.println("[Languagger]: Language not found: " + path);
            return false;
        }

        translations.clear();
        parseSimpleJson(content, translations);

        currentLanguage = langKey;
        System.out.println("[Languagger]: Successfully loaded language: " + currentLanguage);
        return true;
    }

    private static void parseSimpleJson(String json, Map<String, String> outMap) {
        Pattern pattern = Pattern.compile("\"([^\"]+)\"\\s*:\\s*\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(json);

        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2)
                    .replace("\\n", "\n")
                    .replace("\\\"", "\"")
                    .replace("\\\\", "\\");
            outMap.put(key, value);
        }
    }

    public static String getText(String key) {
        return translations.getOrDefault(key, key);
    }

    public static String getCurrentLanguage() {
        return currentLanguage;
    }
}