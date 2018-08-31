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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * It's a simple object type to represent a any object as a map, and its property is not case sensitive.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class ObjectMap extends TreeMap<String, Object> {

    /**
     * the serial version uid
     */
    private static final long serialVersionUID = -3487727105463322342L;

    public ObjectMap() {
        super(String.CASE_INSENSITIVE_ORDER);
    }

    public ObjectMap(Map<String, ?> values) {
        super(String.CASE_INSENSITIVE_ORDER);
        putAll(values);
    }

    /**
     * Gets the property value.
     * 
     * @param <T>
     *            the generic java type
     * 
     * @param property
     *            the property name
     * 
     * @return the value
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String property) {
        return (T) super.get(property);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String property, Class<T> klass) {
        return (T) super.get(property);
    }

    /**
     * Sets the property and value to {@code this} instance.
     * 
     * @param property
     *            the property name
     * 
     * @param value
     *            the value
     * 
     * @return always {@code this}
     */
    public ObjectMap set(String property, Object value) {
        put(property, value);
        return this;
    }

    /**
     * Adds the value to a list which named the property.
     * 
     * @param property
     *            the list property name
     * 
     * @param value
     *            the value
     * 
     * @return always {@code this}
     */
    public ObjectMap add(String property, Object value) {
        List<Object> objs = get(property);
        if (objs == null) {
            objs = new ArrayList<>();
            put(property, objs);
        }
        objs.add(value);
        return this;
    }

    /**
     * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public Object put(String key, Object value) {
        if (key == null) {
            return super.put(null, value);
        }
        return super.put(key, value);
    }

    /**
     * Creates a new {@link ObjectMap} instance without the given properties.
     * 
     * @param properties
     *            the property names
     * 
     * @return a new {@link ObjectMap} instance
     */
    public ObjectMap cloneExcept(String... properties) {
        ObjectMap retVal = new ObjectMap(this);
        if (properties != null && properties.length != 0 && properties[0] != null) {
            for (String prop : properties) {
                retVal.remove(prop);
            }
        }
        return retVal;
    }

}
