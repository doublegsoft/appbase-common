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

import java.io.StringWriter;
import java.util.Arrays;

import net.doublegsoft.appbase.SqlParams;
import net.doublegsoft.appbase.sql.SqlManager;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;

import org.junit.Test;

/**
 *
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class SqlRebuilderTest {

    private static SqlManager sqlmgr = new SqlManager();

    static {
        sqlmgr.setResources(Arrays.asList(new String[] { "/test-actual-program.xml" }));
    }

    @Test
    public void test0() throws Exception {
        SqlRebuilder sca = new SqlRebuilder();
        String sql = sqlmgr.getSql("reservior.getRegistedList");
        System.out.println(sql);
        Statement stmt = CCJSqlParserUtil.parse(sql);
        stmt.accept(sca);
        StringWriter sw = new StringWriter();
        sca.dump(sw);
        System.out.println(sw.toString());
    }

    @Test
    public void test1() throws Exception {
        SqlRebuilder sca = new SqlRebuilder();
        String sql = sqlmgr.getSql("reservior.validateTap8", new SqlParams());
        System.out.println(sql);
        Statement stmt = CCJSqlParserUtil.parse(sql);
        stmt.accept(sca);
        StringWriter sw = new StringWriter();
        sca.dump(sw);
        System.out.println(sw.toString());
    }

    @Test
    public void test2() throws Exception {
        String sql = "SELECT Name FROM Production.Product WHERE NOT EXISTS (SELECT * FROM Production.ProductSubcategory WHERE ProductSubcategoryID = Production.Product.ProductSubcategoryID AND Name = 'Wheels')";
        SqlRebuilder sca = new SqlRebuilder();
        System.out.println(sql);
        Statement stmt = CCJSqlParserUtil.parse(sql);
        stmt.accept(sca);
        StringWriter sw = new StringWriter();
        sca.dump(sw);
        System.out.println(sw.toString());
    }
}
