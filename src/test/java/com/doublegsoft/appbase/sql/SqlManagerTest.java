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

package com.doublegsoft.appbase.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;

import net.doublegsoft.appbase.SqlParams;
import net.doublegsoft.appbase.sql.SqlManager;

/**
 *
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class SqlManagerTest {

    @BeforeClass
    public static void setup() throws Exception {
        SqlManager sqlmgr = new SqlManager();
        sqlmgr.setResources(Arrays.asList("/test.xml"));
    }

    @Test
    public void testInjection() throws Exception {
        SqlManager sqlmgr = new SqlManager();
        SqlParams params = new SqlParams();
        params.set("name", "'x'; DROP TABLE members;");
        String sql = sqlmgr.getSql("test.injection", params);
        System.out.println(sql);
        Statement stmt = CCJSqlParserUtil.parse(sql);
        Assert.assertNotNull(stmt);
    }

    @Test
    public void testLike() throws Exception {
        SqlManager sqlmgr = new SqlManager();
        SqlParams params = new SqlParams();
        params.set("name", "x");
        String sql = sqlmgr.getSql("test.like.injection", params);
        System.out.println(sql);
        Statement stmt = CCJSqlParserUtil.parse(sql);

        Assert.assertNotNull(stmt);

        sql = sqlmgr.getSql("test.llike.injection", params);
        System.out.println(sql);
        stmt = CCJSqlParserUtil.parse(sql);

        Assert.assertNotNull(stmt);

        sql = sqlmgr.getSql("test.rlike.injection", params);
        System.out.println(sql);
        stmt = CCJSqlParserUtil.parse(sql);

        Assert.assertNotNull(stmt);
    }

    @Test
    public void testInWithArray() throws Exception {
        SqlManager sqlmgr = new SqlManager();
        SqlParams params = new SqlParams();
        List<String> names = new ArrayList<>();
        names.add("jack");
        names.add("张三");
        names.add("李四");
        params.set("names", names.toArray(new String[names.size()]));
        String sql = sqlmgr.getSql("test.in.injection", params);
        System.out.println(sql);
        Statement stmt = CCJSqlParserUtil.parse(sql);
        Assert.assertNotNull(stmt);
    }

    @Test
    public void testInWithList() throws Exception {
        SqlManager sqlmgr = new SqlManager();
        SqlParams params = new SqlParams();
        List<String> names = new ArrayList<>();
        names.add("jack");
        names.add("张三");
        names.add("李四");
        params.set("names", names);
        String sql = sqlmgr.getSql("test.in.injection", params);
        System.out.println(sql);
        Statement stmt = CCJSqlParserUtil.parse(sql);

        Assert.assertNotNull(stmt);
    }

    @Test
    public void testInWithSingle() throws Exception {
        SqlManager sqlmgr = new SqlManager();
        SqlParams params = new SqlParams();
        params.set("names", "张三");
        String sql = sqlmgr.getSql("test.in.injection", params);
        System.out.println(sql);
        Statement stmt = CCJSqlParserUtil.parse(sql);

        Assert.assertNotNull(stmt);
    }

    @Test
    public void testPerformance() throws Exception {
        long start = System.currentTimeMillis();
        int count = 100000;
        for (int i = 0; i < count; i++) {
            SqlManager sqlmgr = new SqlManager();
            SqlParams params = new SqlParams();
            List<String> names = new ArrayList<>();
            names.add("jack");
            names.add("张三");
            names.add("李四");
            params.set("names", names);
            sqlmgr.getSql("test.in.injection", params);
        }
        long end = System.currentTimeMillis();
        System.out.println(count + " times exhausted: " + (end - start) + "ms");
    }

}
