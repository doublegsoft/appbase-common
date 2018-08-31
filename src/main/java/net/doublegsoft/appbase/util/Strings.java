/*
 * Copyright 2017 doublegsoft.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.doublegsoft.appbase.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * It's the string utility.
 * 
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 2.0
 */
public class Strings {

  public static boolean isBlank(String str) {
    if (str == null || trim(str).isEmpty()) {
      return true;
    }
    return false;
  }

  /**
   * Uses md5 approach to encrypt the given string.
   * 
   * @param original
   *          the string to encrypt
   * 
   * @return the encrypted string
   */
  public static String md5(String original) {
    MessageDigest md5 = null;
    try {
      md5 = MessageDigest.getInstance("MD5");
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
    char[] charArray = original.toCharArray();
    byte[] byteArray = new byte[charArray.length];

    for (int i = 0; i < charArray.length; i++)
      byteArray[i] = (byte) charArray[i];

    byte[] md5Bytes = md5.digest(byteArray);

    StringBuffer hexValue = new StringBuffer();

    for (int i = 0; i < md5Bytes.length; i++) {
      int val = (md5Bytes[i]) & 0xff;
      if (val < 16)
        hexValue.append("0");
      hexValue.append(Integer.toHexString(val));
    }

    return hexValue.toString().toUpperCase();
  }

  /**
   * Checks the string is null or empty.
   * 
   * @param str
   *          the string to check
   * 
   * @return {@code true} if null or empty, otherwise {@code false}
   * 
   * @since 3.0
   */
  public static boolean isNullOrEmpty(String str) {
    if (str == null || trim(str).isEmpty()) {
      return true;
    }
    return false;
  }

  /**
   * Trims the string. If the string is null, and gets an empty string.
   * 
   * @param str
   *          the string to trim
   * 
   * @return the trimmed string
   * 
   * @since 2.0
   */
  public static String trim(String str) {
    if (str == null) {
      return "";
    }
    return str.trim();
  }

  /**
   * Converts the string to pinyin.
   * 
   * @param str
   *          the string to be converted
   * 
   * @return pinyin array for each character, may contain null in array
   */
  public static String[] pinyin(String str) {
    List<String> retVal = new ArrayList<>();
    for (Character ch : str.toCharArray()) {
      String[] strs = PinyinHelper.toHanyuPinyinStringArray(ch);
      // TODO: 多音字如何处理
      for (String pinyin : strs) {
        char last = pinyin.charAt(pinyin.length() - 1);
        if (last >= '0' && last <= '9') {
          pinyin = pinyin.substring(0, pinyin.length() - 1);
        }
        if (pinyin.equals("none")) {
          retVal.add(null);
        } else {
          retVal.add(pinyin);
        }
        break;
      }
    }
    return retVal.toArray(new String[retVal.size()]);
  }

  private Strings() {

  }
}
