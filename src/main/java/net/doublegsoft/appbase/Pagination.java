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

/**
 * It's the paged result object.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class Pagination<T> {

    /**
     * the total number.
     */
    private int total;

    /**
     * the data for the current page.
     */
    private final List<T> data = new ArrayList<>();

    /**
     * Gets the total number.
     * 
     * @return the total number
     */
    public int getTotal() {
        return total;
    }

    /**
     * Sets the total number.
     * 
     * @param total
     *            the total number
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * Gets the data for the current page.
     * 
     * @return the data
     */
    public List<T> getData() {
        return data;
    }

    /**
     * Sets the data for the current page.
     * 
     * @param obj
     *            the object
     */
    public void add(T obj) {
        data.add(obj);
    }

}
