package com.chocoh.ql.common.utils;

import java.util.List;

/**
 * @author chocoh
 */
public class KmpUtil {
    public static boolean matchPattern(String text, String pattern) {
        return kmpMatch(text, pattern);
    }

    public static boolean matchPatterns(String text, List<String> patterns) {
        for (String pattern : patterns) {
            if (kmpMatch(text, pattern)) {
                return true;
            }
        }
        return false;
    }

    private static boolean kmpMatch(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();
        int[] next = getNextArray(pattern);
        int i = 0, j = 0;
        while (i < textLength && j < patternLength) {
            if (j == -1 || text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        return j == patternLength;
    }

    private static int[] getNextArray(String pattern) {
        int patternLength = pattern.length();
        int[] next = new int[patternLength];
        next[0] = -1;
        int k = -1, j = 0;
        while (j < patternLength - 1) {
            if (k == -1 || pattern.charAt(j) == pattern.charAt(k)) {
                j++;
                k++;
                if (pattern.charAt(j) != pattern.charAt(k)) {
                    next[j] = k;
                } else {
                    next[j] = next[k];
                }
            } else {
                k = next[k];
            }
        }
        return next;
    }
}
