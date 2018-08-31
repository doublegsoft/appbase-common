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

package net.doublegsoft.appbase.sql;

import java.util.List;

import freemarker.template.SimpleSequence;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * @author christian
 *
 */
public class SqlInTemplateMethodModel implements TemplateMethodModelEx {

    /**
     * Generates the in-statement sql: in ('argument0[0]', 'argument0[1]', 'argument0[2]')
     * 
     * @param arguments
     *            the arguments
     */
    @SuppressWarnings({ "rawtypes" })
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        StringBuilder retVal = new StringBuilder("(");
        Object obj = arguments.get(0);
        if (obj.getClass() == SimpleSequence.class) {
            SimpleSequence seq = (SimpleSequence) obj;
            int size = seq.size();
            for (int i = 0; i < size; i++) {
                appendEach(retVal, seq.get(i), i);
            }
        } else {
            retVal.append(obj);
        }
        retVal.append(")");
        return retVal.toString();
    }

    private void appendEach(StringBuilder sql, Object single, int index) {
        if (index > 0) {
            sql.append(",");
        }
        if (single == null) {
            sql.append("null");
        } else {
            sql.append(single);
        }
    }
}
