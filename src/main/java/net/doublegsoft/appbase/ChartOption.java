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
package net.doublegsoft.appbase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 2.0
 */
public class ChartOption {

    private final List<String> legends = new ArrayList<>();

    private final List<String> dataInAxisX = new ArrayList<>();

    private final List<Series> series = new ArrayList<>();

    public List<String> getLegends() {
        List<String> retVal = new ArrayList<>();
        for (Series ser : series) {
            retVal.add(ser.legend);
        }
        return retVal;
    }

    public List<String> getDataInAxisX() {
        return dataInAxisX;
    }

    public void addSeries(Series series) {
        legends.add(series.legend);
        this.series.add(series);
    }

    public List<Series> getSeries() {
        return series;
    }

    public Series getSeries(String legend) {
        for (Series ser : series) {
            if (ser.legend.equals(legend)) {
                return ser;
            }
        }
        return null;
    }

    public static class Series {

        private String legend;

        private final List<Object> data = new ArrayList<>();

        public String getLegend() {
            return legend;
        }

        public void setLegend(String legend) {
            this.legend = legend;
        }

        public List<Object> getData() {
            return data;
        }

    }
}
