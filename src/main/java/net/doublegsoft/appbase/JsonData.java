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

package net.doublegsoft.appbase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * It's a json string represented as a java object, and is transfered between server and client.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 * @since 1.0
 */
public class JsonData {

  public static final String EMPTY_JSON = "{}";

  @SuppressWarnings("unchecked")
  public static final JsonData EMPTY = new JsonData(Collections.EMPTY_MAP) {

    @Override
    public JsonData set(ObjectMap obj) {
      return this;
    }

    @Override
    public JsonData set(Map<String, Object> values) {
      return this;
    }

    @Override
    public JsonData set(Pagination<?> page) {
      return this;
    }

    @Override
    public JsonData add(String name, Object value) {
      return this;
    }

    @Override
    public JsonData set(String name, Object value) {
      return this;
    }

    @Override
    public JsonData add(Object value, String... names) {
      return this;
    }

    @Override
    public JsonData set(Object value, String... names) {
      return this;
    }

  };

  @Deprecated
  public static final JsonData ERROR = new JsonData(Collections.unmodifiableMap(new ObjectMap().set("error", true)));

  private final Map<String, Object> values = new HashMap<>();

  /**
   * The constructor with the multiple key-value pairs.
   *
   * @param values
   *        the key-value pairs
   */
  public JsonData(Map<String, Object> values) {
    this.values.putAll(values);
  }

  public JsonData(Pagination<?> page) {
    values.put("total", page.getTotal());
    values.put("data", page.getData());
  }

  public JsonData(ObjectMap obj) {
    values.putAll(obj);
  }

  /**
   * The default constructor.
   */
  public JsonData() {

  }

  public JsonData set(ObjectMap obj) {
    values.putAll(obj);
    return this;
  }

  public JsonData set(Map<String, Object> values) {
    this.values.putAll(values);
    return this;
  }

  public JsonData set(Pagination<?> page) {
    values.put("total", page.getTotal());
    values.put("data", page.getData());
    return this;
  }

  /**
   * Adds the value into a list which is named with the name.
   *
   * @param name
   *        the name
   *
   * @param value
   *        the value
   *
   * @return always {@code this}
   */
  public JsonData add(String name, Object value) {
    add(values, value, 0, name);
    return this;
  }

  /**
   * Sets the value with the name in {@code this}.
   *
   * @param name
   *        the name
   *
   * @param value
   *        the value
   *
   * @return always {@code this}
   */
  public JsonData set(String name, Object value) {
    set(values, value, 0, name);
    return this;
  }

  /**
   * Adds the value into a list which is named with the name.
   *
   * @param value the value
   * @param names the hierachy names
   * @return always {@code this}
   */
  public JsonData add(Object value, String... names) {
    add(values, value, 0, names);
    return this;
  }

  /**
   * Sets the hierarchical value with the hierarchical names.
   *
   * @param value the value
   * @param names the hierarchical names
   * @return always {@code this}
   */
  public JsonData set(Object value, String... names) {
    set(values, value, 0, names);
    return this;
  }

  /**
   * Sets the error message.
   *
   * @param message
   *        the error message
   *
   * @return always {@code this}
   */
  public JsonData error(String message) {
    return error(-1, message);
  }

  /**
   * Sets the error code and message.
   *
   * @param code
   *        the error code
   *
   * @param message
   *        the error message
   *
   * @return always {@code this}
   */
  public JsonData error(int code, String message) {
    ObjectMap error = new ObjectMap();
    error.set("code", code);
    error.set("message", message);
    values.put("error", error);
    return this;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    if (values.isEmpty()) {
      return EMPTY_JSON;
    }
    return JSON.toJSONString(values);
  }

  @SuppressWarnings("unchecked")
  private void set(Map<String, Object> holder, Object value, int index, String... names) {
    if (index == names.length - 1) {
      if (value instanceof JsonData) {
        holder.put(names[index], ((JsonData) value).values);
      } else {
        holder.put(names[index], value);
      }
    } else {
      Map<String, Object> map = (Map<String, Object>) holder.get(names[index]);
      if (map == null) {
        map = new HashMap<>();
        holder.put(names[index], map);
      }
      set(map, value, ++index, names);
    }
  }

  @SuppressWarnings("unchecked")
  private void add(Map<String, Object> holder, Object value, int index, String... names) {
    if (index == names.length - 1) {
      List<Object> list = (List<Object>) holder.get(names[index]);
      if (list == null) {
        list = new ArrayList<>();
        holder.put(names[index], list);
      }
      if (value instanceof JsonData) {
        list.add(((JsonData) value).values);
      } else {
        list.add(value);
      }
    } else {
      Map<String, Object> map = (Map<String, Object>) holder.get(names[index]);
      if (map == null) {
        map = new HashMap<>();
        holder.put(names[index], map);
      }
      add(map, value, ++index, names);
    }
  }

}
