package net.doublegsoft.appbase.util;

import net.doublegsoft.appbase.SqlParams;
import net.doublegsoft.appbase.service.CommonService;
import net.doublegsoft.appbase.service.ServiceException;

import java.util.Calendar;
import java.util.Date;

public class IdGenerator {

  public static String uuid() {
    return Strings.id();
  }

  public static Long sequence(CommonService commonService, String maxSql, String idField) throws ServiceException {
    Long value = commonService.value(maxSql, new SqlParams());
    if (value == null) {
      return 1L;
    }
    return value + 1;
  }

  public static String code(Date base, String format, int digitLength,
                            CommonService commonService, String maxSql, String idField)
      throws ServiceException {
    int year = Dates.get(base, Calendar.YEAR);
    int month = Dates.get(base, Calendar.MONTH) + 1;
    int day = Dates.get(base, Calendar.DAY_OF_MONTH);
    int hour = Dates.get(base, Calendar.HOUR_OF_DAY);
    int minute = Dates.get(base, Calendar.MINUTE);
    int second = Dates.get(base, Calendar.SECOND);

    String strBase = format;
    strBase = strBase.replaceAll("YYYY", String.valueOf(year));
    strBase = strBase.replaceAll("MM", String.format("%02d", month));
    strBase = strBase.replaceAll("DD", String.format("%02d", day));
    strBase = strBase.replaceAll("hh", String.format("%02d", hour));
    strBase = strBase.replaceAll("mm", String.format("%02d", minute));
    strBase = strBase.replaceAll("ss", String.format("%02d", second));
    return code(strBase, digitLength, commonService, maxSql, idField);
  }

  public static String code(String base, int digitLength,
                            CommonService commonService, String maxSql, String idField)
      throws ServiceException {
    SqlParams sqlParams = new SqlParams();
    sqlParams.set(idField, base);
    String value = commonService.value(maxSql, sqlParams);
    int index = 0;
    if (value == null) {
      index = 1;
    } else {
      index = Integer.valueOf(value.substring(value.length() - digitLength));
      index++;
    }
    return base + String.format("%0" + digitLength + "d", index);
  }

}
