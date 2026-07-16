package com.kuafu.common.sensitive;

import com.kuafu.common.util.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Map;

public class SensitiveWordFilter {

    private final Map<Character, Map> sensitiveWordMap = new HashMap<>();

    public SensitiveWordFilter() {
        loadSensitiveWords();
    }

    private void loadSensitiveWords() {
        try {
            ClassPathResource classPathResource = new ClassPathResource("sensitive_words.txt");
            String value = IOUtils.toString(classPathResource.getInputStream(), "UTF-8");
            String[] words = StringUtils.split(value, "\n");
            for (String word : words) {
                addWordToDFA(word);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addWordToDFA(String word) {
        Map<Character, Map> currentMap = sensitiveWordMap;
        for (char c : word.toCharArray()) {
            currentMap = currentMap.computeIfAbsent(c, k -> new HashMap<>());
        }
        currentMap.put('E', new HashMap<>());
    }

    public String filter(String text) {
        StringBuilder result = new StringBuilder(text);
        int length = text.length();
        for (int i = 0; i < length; i++) {
            Map<Character, Map> currentMap = sensitiveWordMap;
            int j = i;
            while (j < length && currentMap.containsKey(text.charAt(j))) {
                currentMap = (Map<Character, Map>) currentMap.get(text.charAt(j));
                if (currentMap.containsKey('E')) { // 找到敏感词
                    for (int k = i; k <= j; k++) {
                        result.setCharAt(k, '*');
                    }
                    i = j; // 跳过已匹配部分
                    break;
                }
                j++;
            }
        }
        return result.toString();
    }
}
