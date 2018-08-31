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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;

/**
 * It's the sql parameters represented by key/value pairs.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class SqlParams extends TreeMap<String, Object> {

    /**
     * the serial version uid.
     */
    private static final long serialVersionUID = 8182059198688275573L;

    @SuppressWarnings("serial")
    public static final SqlParams EMPTY = new SqlParams() {

        @Override
        public SqlParams set(String name, Object value) {
            return this;
        }

    };

    public SqlParams() {
        super(String.CASE_INSENSITIVE_ORDER);
    }

    public SqlParams set(String name, Object value) {
        super.put(name, objectToNative(value));
        return this;
    }

    /**
     * Sets the sql parameters from a map object.
     * 
     * @param params
     *            the parameters held by the map object
     * 
     * @return always {@code this}
     */
    public SqlParams set(Map<String, Object> params) {
        if (params == null) {
            return this;
        }
        for (Entry<String, Object> e : params.entrySet()) {
            set(e.getKey(), e.getValue());
        }
        return this;
    }

    /**
     * Sets the sql parameters from a json string.
     * 
     * @param json
     *            the json string
     * 
     * @return always {@code this}
     */
    @SuppressWarnings("unchecked")
    public SqlParams set(String json) {
        try {
            Map<String, Object> vals = (Map<String, Object>) JSON.parse(json);
            set(vals);
        } catch (Exception ex) {
            // nothing to do
        }
        return this;
    }

    /**
     * Gets the string applied in sql statement.
     * <ul>
     * <li>if the string is null will return {@code "null"} string</li>
     * <li>if the string is a number will return number string</li>
     * <li>if the string is string will return the string value with single quotes, like {@code "'string'"}</li>
     * </ul>
     * 
     * @see java.util.HashMap#get(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        if (!containsKey(name)) {
            return (T) "null";
        }
        return (T) super.get(name);
    }

    @Override
    public Object get(Object key) {
        if (!containsKey(key)) {
            return "null";
        }
        return super.get(key);
    }

    /**
     * Do not use it to put key/value pair.
     * <p>
     * Workaround for freemarker, avoiding freemarker put key/value pair to this map.
     * 
     * @deprecated
     */
    @Deprecated
    @Override
    public Object put(String key, Object value) {
        return null;
    }

    /**
     * Do not use it to put key/value pairs.
     * 
     * @deprecated
     */
    @Deprecated
    @Override
    public void putAll(Map<? extends String, ? extends Object> m) {
        return;
    }

    /**
     * Do not use it to put key/value pair.
     * 
     * @deprecated
     */
    @Deprecated
    @Override
    public Object putIfAbsent(String key, Object value) {
        return null;
    }

    private String zeroFill(int val) {
        if (val < 10) {
            return "0" + val;
        }
        return String.valueOf(val);
    }

    @SuppressWarnings("unchecked")
    private Object objectToNative(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).toPlainString();
        } else if (obj instanceof Number) {
            return obj.toString();
        } else if (obj instanceof String) {
            obj = ((String) obj).replaceAll("'", "''");
            if (((String) obj).trim().length() == 0) {
                return "null";
            }
        } else if (Collection.class.isAssignableFrom(obj.getClass())) {
            List<Object> retVal = new ArrayList<>();
            Collection<Object> retColl = (Collection<Object>) obj;
            Iterator<?> ite = retColl.iterator();
            while (ite.hasNext()) {
                retVal.add(objectToNative(ite.next()));
            }
            return retVal;
        } else if (obj.getClass().isArray()) {
            Class<?> cls = obj.getClass();
            Class<?> cc = cls.getComponentType();
            if (cc != null && Map.class.isAssignableFrom(cc)) {
                Map<String, Object>[] arr = (Map<String, Object>[]) obj;
                List<SqlParams> sps = new ArrayList<>();
                for (Map<String, Object> sp : arr) {
                    sps.add(new SqlParams().set(sp));
                }
                return sps;
            }
            return obj;
        } else if (Date.class.isAssignableFrom(obj.getClass())) {
            Calendar cal = Calendar.getInstance();
            cal.setTime((Date) obj);
            StringBuilder dt = new StringBuilder("'");
            dt.append(cal.get(Calendar.YEAR)).append("-");
            dt.append(zeroFill(cal.get(Calendar.MONTH) + 1)).append("-");
            dt.append(zeroFill(cal.get(Calendar.DAY_OF_MONTH)));
            dt.append(" ").append(zeroFill(cal.get(Calendar.HOUR_OF_DAY)));
            dt.append(":").append(zeroFill(cal.get(Calendar.MINUTE)));
            dt.append(":").append(zeroFill(cal.get(Calendar.SECOND)));
            return dt.append("'").toString();
        } else if (Map.class.isAssignableFrom(obj.getClass())) {
            SqlParams retVal = new SqlParams();
            retVal.set((Map<String, Object>) obj);
            return retVal;
        }
        return "'" + obj.toString() + "'";
    }
}
