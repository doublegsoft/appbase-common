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

import freemarker.template.SimpleScalar;
import freemarker.template.SimpleSequence;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * @author gg
 *
 */
public class SqlEmptyTemplateMethodModel implements TemplateMethodModelEx {

    @SuppressWarnings("rawtypes")
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        Object val = arguments.get(0);
        if (val == null) {
            return true;
        }
        if (val instanceof SimpleScalar) {
            String str = val.toString();
            return str.trim().isEmpty() || "null".equals(str) || "'null'".equals(str) || "''".equals(str);
        } else if (val instanceof SimpleSequence) {
            SimpleSequence ss = (SimpleSequence) val;
            return ss.size() == 0;
        }
        return true;
    }

}
