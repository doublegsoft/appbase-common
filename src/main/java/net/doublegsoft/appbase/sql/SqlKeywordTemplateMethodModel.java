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

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * @author gg
 *
 */
public class SqlKeywordTemplateMethodModel implements TemplateMethodModelEx {

    @SuppressWarnings("rawtypes")
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        Object val = arguments.get(0);
        String str = val.toString();
        return str.substring(1, str.length() - 1);
    }

}
