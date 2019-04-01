/* 
 * Copyright (C) doublegsoft.net - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 * Proprietary and confidential
 */
package net.doublegsoft.appbase.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * TODO: ADD DESCRIPTION
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class Safe {

  public static String safeString(Integer val) {
    if (val == null) {
      return null;
    }
    return val.toString();
  }

  public static String safeString(Long val) {
    if (val == null) {
      return null;
    }
    return val.toString();
  }

  public static String safeString(BigDecimal val) {
    if (val == null) {
      return null;
    }
    return val.toPlainString();
  }

  public static String safeString(java.sql.Date val) {
    if (val == null) {
      return null;
    }
    return val.toString();
  }

  public static String safeString(java.sql.Timestamp val) {
    if (val == null) {
      return null;
    }
    return val.toString();
  }

  public static Integer safeInteger(String val) {
    try {
      return Integer.valueOf(val);
    } catch (Exception ex) {
      return null;
    }
  }

  public static Integer safeInteger(Long val) {
    if (val == null) {
      return null;
    }
    return val.intValue();
  }

  public static Long safeLong(String val) {
    try {
      return Long.valueOf(val);
    } catch (Exception ex) {
      return null;
    }
  }

  public static Long safeLong(Integer val) {
    if (val == null) {
      return null;
    }
    return val.longValue();
  }

  public static BigDecimal safeBigDecimal(String val) {
    try {
      return new BigDecimal(val);
    } catch (Exception ex) {
      return null;
    }
  }

  public static java.sql.Date safeDate(String val) {
    try {
      return java.sql.Date.valueOf(val);
    } catch (Exception ex) {
      return null;
    }
  }

  public static java.sql.Date safeDate(Long val) {
    if (val == null) {
      return null;
    }
    return new java.sql.Date(val);
  }

  public static java.sql.Timestamp safeTimestamp(String val) {
    try {
      return java.sql.Timestamp.valueOf(val);
    } catch (Exception ex) {
      return null;
    }
  }

  public static java.sql.Timestamp safeTimestamp(Long val) {
    if (val == null) {
      return null;
    }
    return new java.sql.Timestamp(val);
  }

  public static <T> T safe(String val, Class<T> clazz) {
    if (Strings.isBlank(val)) {
      return null;
    }
    if (clazz == String.class) {
      return (T) val;
    } else if (clazz == BigDecimal.class) {
      return (T) safeBigDecimal(val);
    } else if (clazz == Timestamp.class) {
      return (T) safeTimestamp(val);
    } else if (clazz == Date.class) {
      return (T) safeDate(val);
    } else if (clazz == Integer.class) {
      return (T) safeInteger(val);
    } else if (clazz == Long.class) {
      return (T) safeLong(val);
    }
    return (T) val;
  }

  private Safe() {

  }

}
