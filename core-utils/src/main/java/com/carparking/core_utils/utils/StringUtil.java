package com.carparking.core_utils.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtil {

  public static String removeAccent(String s) {
    return StringUtils.stripAccents(s)
        .replace('đ', 'd')
        .replace('Đ', 'D');
  }

  /**
   * Build SQL search keyword from request
   * @param s
   * @return
   */
  public static String buildSearchKeyword(String s) {
    if (!hasText(s)) return null;
    String[] words = s.trim().split("\\s");
    return "%" + Arrays.stream(words)
        .filter(w -> !w.isEmpty())
        .collect(Collectors.joining("% ")) + "%";
  }

  public static boolean hasText(String str) {
    return (str != null && !str.isEmpty() && containsText(str));
  }

  private static boolean containsText(CharSequence str) {
    int strLen = str.length();
    for (int i = 0; i < strLen; i++) {
      if (!Character.isWhitespace(str.charAt(i))) {
        return true;
      }
    }
    return false;
  }

  public static String capitalizeFirstWord(String s) {
    String replace = String.valueOf(s.charAt(0));
    return s.replaceFirst(replace, replace.toUpperCase());
  }
}
