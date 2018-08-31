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

package net.doublegsoft.appbase.tpl;

import java.io.IOException;
import java.io.StringWriter;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.doublegsoft.appbase.ObjectMap;

/**
 *
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class StringTemplateProcessor {

    private static final Configuration FREEMARKER = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

    private static final StringTemplateLoader TEMPLATE_LOADER = new StringTemplateLoader();

    static {
        FREEMARKER.setTemplateLoader(TEMPLATE_LOADER);
    }

    public static String process(String templateString, ObjectMap params) throws IOException {
        Template tpl = new Template("template", templateString, FREEMARKER);
        StringWriter retVal = new StringWriter();
        try {
            tpl.process(params, retVal);
        } catch (TemplateException ex) {
            throw new IOException(ex);
        }
        retVal.flush();
        return retVal.toString();
    }

    private StringTemplateProcessor() {

    }
}
