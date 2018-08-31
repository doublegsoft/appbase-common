/*
 * Copyright 2016 doublegsoft.net
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
package com.doublegsoft.appbase;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.doublegsoft.appbase.ObjectMap;
import net.doublegsoft.appbase.SqlParams;

/**
 * Test for {@link SqlParams}.
 */
public class SqlParamsTest {

  @Test
  public void test_map() {
    ObjectMap obj = new ObjectMap();
    obj.set("string", "abc");
    obj.set("bigdecimal", BigDecimal.valueOf(10000.123456789D));
    obj.set("date", Timestamp.valueOf("2017-01-01 12:00:00"));

    SqlParams params = new SqlParams();
    params.set(obj);

    Assert.assertEquals("'abc'", params.get("string"));
    Assert.assertEquals("10000.123456789", params.get("bigdecimal"));
    Assert.assertEquals("'2017-01-01 12:00:00'", params.get("date"));
    Assert.assertEquals("null", params.get("non-existing"));
  }

  @Test
  public void test_map_list() {
    ObjectMap criteria = new ObjectMap();
    for (int i = 0; i < 10; i++) {
      ObjectMap obj = new ObjectMap();
      obj.set("value", "abc" + i);
      criteria.add("objs", obj);
    }
    SqlParams params = new SqlParams();
    params.set(criteria);

    List<SqlParams> values = params.get("objs");
    for (int i = 0; i < 10; i++) {
      Assert.assertEquals("'abc" + i + "'", values.get(i).get("value"));
    }
  }

  @Test
  public void test_string_list() {
    ObjectMap criteria = new ObjectMap();
    for (int i = 0; i < 10; i++) {
      criteria.add("strs", "string" + i);
    }
    SqlParams params = new SqlParams();
    params.set(criteria);

    List<String> values = params.get("strs");
    for (int i = 0; i < 10; i++) {
      Assert.assertEquals("'string" + i + "'", values.get(i));
    }
  }

}
