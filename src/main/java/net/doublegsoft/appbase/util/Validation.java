/* 
 * Copyright (C) doublegsoft.biz - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 * Proprietary and confidential
 */
package net.doublegsoft.appbase.util;

import java.util.List;
import java.util.regex.Pattern;

/**
 * It a base validation to encapsulate some common validation methods.
 */
public class Validation {

  public static final Pattern MOBILE = Pattern.compile("^\\d{11}$");

  public static final Pattern EMAIL = Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$",
      Pattern.CASE_INSENSITIVE);

  public final boolean isNullOrEmpty(Object any) {
    if (any == null) {
      return true;
    }
    if (any instanceof String) {
      return any.toString().trim().isEmpty();
    }
    return false;
  }

  public final String validate(Object any, String message) {
    if (isNullOrEmpty(any)) {
      return message;
    }
    return null;
  }

  public final String validate(List<Object[]> valuesAndMessages) {
    StringBuilder retVal = new StringBuilder();
    for (Object[] valueAndMessage : valuesAndMessages) {
      if (validate(valueAndMessage[0], (String) valueAndMessage[1]) != null) {
        retVal.append(valueAndMessage[1]).append("\n");
      }
    }
    if (retVal.length() > 0) {
      return retVal.toString();
    }
    return null;
  }

  public final boolean validateState(String flag) {
    if (isNullOrEmpty(flag)) {
      return true;
    }
    return "A".equals(flag) || "D".equals(flag);
  }

  public final boolean validateBool(String flag) {
    if (isNullOrEmpty(flag)) {
      return true;
    }
    return "T".equals(flag) || "F".equals(flag);
  }

  public final boolean validateEnum(String flag, String[] values) {
    if (isNullOrEmpty(flag)) {
      return true;
    }
    for (String val : values) {
      if (val.equals(flag)) {
        return true;
      }
    }
    return false;
  }

  public final boolean validateMobile(String mobile) {
    return MOBILE.matcher(mobile).matches();
  }

  public final boolean validateEmail(String email) {
    return EMAIL.matcher(email).matches();
  }

}
