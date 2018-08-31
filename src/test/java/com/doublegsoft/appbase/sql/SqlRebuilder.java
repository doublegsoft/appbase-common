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

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.ValuesList;
import net.sf.jsqlparser.statement.select.WithItem;

import org.javatuples.Pair;

/**
 *
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class SqlRebuilder extends StatementVisitorAdapter implements FromItemVisitor, SelectVisitor {

    private List<Pair<String, String>> tables = new ArrayList<>();

    @Override
    public void visit(Select select) {
        SelectBody body = select.getSelectBody();
        if (body instanceof PlainSelect) {
            PlainSelect ps = (PlainSelect) body;
            FromItem fi = ps.getFromItem();
            if (fi instanceof Table) {
                visit((Table) fi);
            } else if (fi instanceof LateralSubSelect) {
                visit((LateralSubSelect) fi);
            } else if (fi instanceof SubSelect) {
                visit((SubSelect) fi);
            }
        }
    }

    public void dump(Writer writer) throws IOException {
        for (Pair<String, String> t : tables) {
            writer.write(t.getValue0() + " " + t.getValue1());
        }
    }

    @Override
    public void visit(Table tableName) {
        Pair<String, String> p = Pair.with(tableName.getName(), tableName.getAlias() == null ? null : tableName
                .getAlias().getName());
        tables.add(p);
    }

    @Override
    public void visit(SubSelect subSelect) {
        SelectBody sb = subSelect.getSelectBody();
        if (sb instanceof PlainSelect) {
            visit((PlainSelect) sb);
        } else if (sb instanceof SetOperationList) {
            visit((SetOperationList) sb);
        } else if (sb instanceof WithItem) {
            visit((WithItem) sb);
        }
    }

    @Override
    public void visit(SubJoin subjoin) {
    }

    @Override
    public void visit(LateralSubSelect lateralSubSelect) {
        visit(lateralSubSelect.getSubSelect());
    }

    @Override
    public void visit(ValuesList valuesList) {
    }

    @Override
    public void visit(PlainSelect plainSelect) {
        FromItem fi = plainSelect.getFromItem();
    }

    @Override
    public void visit(SetOperationList setOpList) {
        for (SelectBody sb : setOpList.getSelects()) {
            if (sb instanceof PlainSelect) {
                visit((PlainSelect) sb);
            } else if (sb instanceof SetOperationList) {
                visit((SetOperationList) sb);
            } else if (sb instanceof WithItem) {
                visit((WithItem) sb);
            }
        }
    }

    @Override
    public void visit(WithItem withItem) {
    }

}
