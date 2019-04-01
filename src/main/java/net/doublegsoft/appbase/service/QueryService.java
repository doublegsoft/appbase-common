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
package net.doublegsoft.appbase.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.doublegsoft.appbase.ChartOption;
import net.doublegsoft.appbase.ChartOption.Series;
import net.doublegsoft.appbase.ObjectMap;
import net.doublegsoft.appbase.Pagination;
import net.doublegsoft.appbase.SqlParams;
import net.doublegsoft.appbase.TreeNode;
import net.doublegsoft.appbase.util.Numbers;

/**
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 2.0
 */
public class QueryService {

    public static final String PARAM_SQLID = "sqlId";

    public static final String PARAM_LIMIT = "limit";

    public static final String PARAM_START = "start";

    public static final String PARAM_ID_FIELD = "idField";

    public static final String PARAM_PARENT_ID_FIELD = "parentIdField";

    public static final String PARAM_PARENT_CODE_FIELD = "parentCodeField";

    public static final String PARAM_PARENT_CODE = "parentCode";

    /**
     * the chart legend data field from flatten data set.
     */
    public static final String PARAM_LEGEND_FIELD = "legendField";

    /**
     * the chart x axis data field from flatten data set.
     */
    public static final String PARAM_X_AXIS_FIELD = "xAxisField";

    /**
     * the chart value data field from flatten data set.
     */
    public static final String PARAM_VALUE_FIELD = "valueField";

    public static final String ATTR_CHILDREN = "children";

    private CommonService commonService;

    public List<ObjectMap> find(ObjectMap criteria) throws ServiceException {
        String sqlId = criteria.get(PARAM_SQLID);
        return commonService.many(sqlId, new SqlParams().set(criteria));
    }

    public Pagination<ObjectMap> paginate(ObjectMap criteria) throws ServiceException {
        String sqlId = criteria.get(PARAM_SQLID);
        Integer limit = Numbers.safeInt(criteria.get(PARAM_LIMIT));
        Integer start = Numbers.safeInt(criteria.get(PARAM_START));
        return commonService.paginate(sqlId, start, limit, new SqlParams().set(criteria));
    }

    public List<TreeNode<ObjectMap>> treeize(ObjectMap criteria) throws ServiceException {
        String sqlId = criteria.get(PARAM_SQLID);
        List<ObjectMap> data = commonService.many(sqlId, new SqlParams().set(criteria));

        String parentCode = criteria.get(PARAM_PARENT_CODE);
        List<TreeNode<ObjectMap>> retVal = new ArrayList<>();
        // assemble root
        for (ObjectMap obj : data) {
            String objParentCode = obj.get(PARAM_PARENT_CODE_FIELD);
            if (compare(parentCode, objParentCode)) {
                TreeNode<ObjectMap> node = new TreeNode<>();
                node.setData(obj);
                retVal.add(node);
            }
        }
        for (ObjectMap obj : data) {
            String objId = obj.get(PARAM_ID_FIELD);
            for (ObjectMap innerObj : data) {
                String innerObjParentId = obj.get(PARAM_PARENT_ID_FIELD);
                if (innerObjParentId.equals(objId)) {
                    obj.add(ATTR_CHILDREN, innerObj);
                }
            }
        }
        return retVal;
    }

    public ChartOption chartize(ObjectMap criteria) throws ServiceException {
        String sqlId = criteria.get(PARAM_SQLID);
        List<ObjectMap> data = commonService.many(sqlId, new SqlParams().set(criteria));

        String legendField = criteria.get(PARAM_LEGEND_FIELD);
        String xAxisField = criteria.get(PARAM_X_AXIS_FIELD);
        String valueField = criteria.get(PARAM_VALUE_FIELD);

        Set<String> legendSet = new HashSet<>();
        Set<String> xAxisSet = new HashSet<>();

        ChartOption retVal = new ChartOption();
        for (ObjectMap obj : data) {
            String legend = obj.get(legendField);
            String xAxis = obj.get(xAxisField);
            legendSet.add(legend);
            xAxisSet.add(xAxis);
        }
        // TODO: SORT LEGENDS, XAXIS

        // legends
        for (String legend : legendSet) {
            Series series = new Series();
            series.setLegend(legend);
            retVal.addSeries(series);
        }

        // x axis
        for (String xAxis : xAxisSet) {
            retVal.getDataInAxisX().add(xAxis);
        }
        for (String xAxis : retVal.getDataInAxisX()) {
            for (ObjectMap obj : data) {
                String objLegend = obj.get(legendField);
                String objXAxis = obj.get(xAxisField);
                Object objValue = obj.get(valueField);
                if (compare(xAxis, objXAxis)) {
                    Series ser = retVal.getSeries(objLegend);
                    ser.getData().add(objValue);
                }
            }
        }

        return retVal;
    }

    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }

    private static boolean compare(String value1, String value2) {
        if (value1 == null) {
            if (value2 == null) {
                return true;
            } else {
                return false;
            }
        }
        return value1.equals(value2);
    }
}
