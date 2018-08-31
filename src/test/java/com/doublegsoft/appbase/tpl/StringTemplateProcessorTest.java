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

package com.doublegsoft.appbase.tpl;

import org.junit.Assert;
import org.junit.Test;

import net.doublegsoft.appbase.ObjectMap;
import net.doublegsoft.appbase.tpl.StringTemplateProcessor;

/**
 *
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class StringTemplateProcessorTest {

    @Test
    public void test() throws Exception {
        ObjectMap params = new ObjectMap();
        params.put("name", "world");
        String check = StringTemplateProcessor.process("hello, ${name}", params);
        Assert.assertEquals("hello, world", check);
    }

}
