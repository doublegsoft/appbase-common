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

package net.doublegsoft.appbase.dao;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import net.doublegsoft.appbase.ObjectMap;
import net.doublegsoft.appbase.Pagination;

/**
 *
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class JdbcCommonDataAccess implements CommonDataAccess, Closeable {

  private Connection conn;

  /**
   * @see net.doublegsoft.appbase.dao.CommonDataAccess#insert(java.lang.String)
   */
  @Override
  public ObjectMap insert(String sql) throws DataAccessException {
    try (Statement stmt = conn.createStatement()) {
      ObjectMap retVal = new ObjectMap();
      stmt.execute(sql, Statement.RETURN_GENERATED_KEYS);
      ResultSet rs = stmt.getGeneratedKeys();
      if (rs != null) {
        ResultSetMetaData metadata = rs.getMetaData();
        int columnCount = metadata.getColumnCount();
        rs.next();
        for (int i = 1; i <= columnCount; ++i) {
          retVal.set(metadata.getColumnLabel(i), getAndConvert(metadata, rs, i));
        }
      }
      return retVal;
    } catch (SQLException ex) {
      throw new DataAccessException(ex);
    }
  }

  /**
   * @see net.doublegsoft.appbase.dao.CommonDataAccess#execute(java.lang.String)
   */
  @Override
  public void execute(String sql) throws DataAccessException {
    try (Statement stmt = conn.createStatement()) {
      stmt.execute(sql);
    } catch (SQLException ex) {
      throw new DataAccessException(ex);
    }
  }

  /**
   * @see net.doublegsoft.appbase.dao.CommonDataAccess#batch(List)
   */
  @Override
  public void batch(List<String> sqls) throws DataAccessException {
    try (Statement stmt = conn.createStatement()) {
      for (String sql : sqls) {
        stmt.addBatch(sql);
      }
      stmt.executeBatch();
    } catch (SQLException ex) {
      throw new DataAccessException(ex);
    }
  }

  @Override
  public void prepare(String sql, Object... objs) throws DataAccessException {
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      for (int i = 1; i <= objs.length; i++) {
        pstmt.setObject(i, objs[i - 1]);
      }
      pstmt.execute();
    } catch (SQLException ex) {
      throw new DataAccessException(ex);
    }
  }

  public List<ObjectMap> prepareQuery(String sql, Object... objs) throws DataAccessException {
    List<ObjectMap> retVal = new ArrayList<>();
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      for (int i = 1; i <= objs.length; i++) {
        pstmt.setObject(i, objs[i - 1]);
      }
      ResultSet rs = pstmt.executeQuery();
      ResultSetMetaData metadata = rs.getMetaData();
      int columnCount = metadata.getColumnCount();
      while (rs.next()) {
        ObjectMap obj = new ObjectMap();
        for (int i = 1; i <= columnCount; ++i) {
          obj.set(metadata.getColumnLabel(i), getAndConvert(metadata, rs, i));
        }
        retVal.add(obj);
      }
      return retVal;
    } catch (SQLException ex) {
      throw new DataAccessException(ex);
    }
  }

  /**
   * @see net.doublegsoft.appbase.dao.CommonDataAccess#paginate(java.lang.String, int, int)
   */
  @Override
  public Pagination<ObjectMap> paginate(String sql, int start, int limit) throws DataAccessException {
    Pagination<ObjectMap> retVal = new Pagination<>();
    ResultSet rs = null;
    try (Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
      rs = stmt.executeQuery(sql);
      int tp = rs.getType();
      if (tp == ResultSet.TYPE_SCROLL_INSENSITIVE) {
        rs.last();
        retVal.setTotal(rs.getRow());
        rs.absolute(start);
        int count = 0;
        ResultSetMetaData metadata = rs.getMetaData();
        int columnCount = metadata.getColumnCount();
        while (rs.next()) {
          if (count >= limit && limit > 0) {
            break;
          }
          ObjectMap obj = new ObjectMap();
          for (int i = 1; i <= columnCount; ++i) {
            obj.set(metadata.getColumnLabel(i), getAndConvert(metadata, rs, i));
          }
          retVal.add(obj);
          count++;
        }
      } else if (tp == ResultSet.TYPE_FORWARD_ONLY) {
        int crt = 0, added = 0, total = 0;
        while (rs.next()) {
          // 取翻页范围内的数据
          if (crt++ >= start && (limit > 0 ? (added++ < limit) : true)) {
            ResultSetMetaData metadata = rs.getMetaData();
            int columnCount = metadata.getColumnCount();
            ObjectMap obj = new ObjectMap();
            for (int i = 1; i <= columnCount; ++i) {
              obj.set(metadata.getColumnLabel(i), getAndConvert(metadata, rs, i));
            }
            retVal.add(obj);
          }
          total++;
        }
        retVal.setTotal(total);
      }

    } catch (SQLException ex) {
      throw new DataAccessException(ex);
    }
    return retVal;
  }

  @Override
  public List<ObjectMap> many2(String sql) throws DataAccessException {
    List<ObjectMap> retVal = new ArrayList<>();
    ResultSet rs = null;
    try (Statement stmt = conn.createStatement()) {
      rs = stmt.executeQuery(sql);
      ResultSetMetaData metadata = rs.getMetaData();
      int columnCount = metadata.getColumnCount();
      while (rs.next()) {
        ObjectMap obj = new ObjectMap();
        for (int i = 1; i <= columnCount; ++i) {
          obj.set(metadata.getColumnLabel(i), getAndConvert(metadata, rs, i));
        }
        retVal.add(obj);
      }
    } catch (SQLException ex) {
      throw new DataAccessException(ex);
    }
    return retVal;
  }

  /**
   * @see net.doublegsoft.appbase.dao.CommonDataAccess#value(java.lang.String)
   */
  @SuppressWarnings("unchecked")
  @Override
  public <T> T value(String sql) throws DataAccessException {
    T retVal = null;
    try (Statement stmt = conn.createStatement()) {
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next()) {
        retVal = (T) rs.getObject(1);
      }
      rs.close();
      return retVal;
    } catch (SQLException ex) {
      throw new DataAccessException(ex);
    }
  }

  /**
   * @see net.doublegsoft.appbase.dao.CommonDataAccess#list(java.lang.String)
   */
  @Override
  public List<List<Object>> list(String sql) throws DataAccessException {
    List<List<Object>> retVal = new ArrayList<>();
    try (Statement stmt = conn.createStatement()) {
      ResultSet rs = stmt.executeQuery(sql);
      ResultSetMetaData metadata = rs.getMetaData();
      int columnCount = metadata.getColumnCount();
      while (rs.next()) {
        List<Object> row = new ArrayList<>();
        for (int i = 1; i <= columnCount; ++i) {
          row.add(getAndConvert(metadata, rs, i));
        }
        retVal.add(row);
      }
    } catch (SQLException ex) {
      throw new DataAccessException(ex);
    }
    return retVal;
  }

  /**
   * @see net.doublegsoft.appbase.dao.CommonDataAccess#single(java.lang.String)
   */
  @Override
  public ObjectMap single(String sql) throws DataAccessException {
    try (Statement stmt = conn.createStatement()) {
      ResultSet rs = stmt.executeQuery(sql);
      ResultSetMetaData metadata = rs.getMetaData();
      int columnCount = metadata.getColumnCount();
      if (rs.next()) {
        ObjectMap retVal = new ObjectMap();
        for (int i = 1; i <= columnCount; ++i) {
          retVal.set(metadata.getColumnLabel(i), getAndConvert(metadata, rs, i));
        }
        return retVal;
      }
      return null;
    } catch (SQLException ex) {
      throw new DataAccessException(ex);
    }
  }

  @Override
  public List<ObjectMap> call(String sql) throws DataAccessException {
    List<ObjectMap> retVal = new ArrayList<>();
    try (CallableStatement stmt = conn.prepareCall(sql)) {
      ResultSet rs = stmt.executeQuery();
      ResultSetMetaData metadata = rs.getMetaData();
      int columnCount = metadata.getColumnCount();
      while (rs.next()) {
        ObjectMap obj = new ObjectMap();
        for (int i = 1; i <= columnCount; ++i) {
          obj.set(metadata.getColumnLabel(i), getAndConvert(metadata, rs, i));
        }
        retVal.add(obj);
      }
    } catch (SQLException ex) {
      throw new DataAccessException(ex);
    }
    return retVal;
  }

  public void beginTransaction(int level) throws DataAccessException {
    try {
      conn.setAutoCommit(false);
      conn.setTransactionIsolation(level);
    } catch (SQLException ex) {
      throw new DataAccessException(ex);
    }
  }

  public void beginTransaction() throws DataAccessException {
    beginTransaction(Connection.TRANSACTION_READ_UNCOMMITTED);
  }

  public void rollback() throws DataAccessException {
    try {
      conn.rollback();
      conn.setAutoCommit(true);
    } catch (SQLException ex) {
      throw new DataAccessException(ex);
    }
  }

  public void commit() throws DataAccessException {
    try {
      conn.commit();
      conn.setAutoCommit(true);
    } catch (SQLException ex) {
      throw new DataAccessException(ex);
    }
  }

  /**
   * @see java.io.Closeable#close()
   */
  @Override
  public void close() throws IOException {
    try {
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException ex) {

    }
  }

  public void setDataSource(DataSource ds) {
    try {
      conn = ds.getConnection();
    } catch (SQLException ex) {
      ex.printStackTrace(System.out);
    }
  }

  public void setConnection(Connection conn) {
    this.conn = conn;
  }

  protected Object getAndConvert(ResultSetMetaData metadata, ResultSet rs, int index) throws SQLException {
    int type = metadata.getColumnType(index);
    try {
      switch (type) {
      case Types.CLOB:
        Clob clob = rs.getClob(index);
        if (clob == null) {
          return null;
        }
        StringBuilder str = new StringBuilder();
        byte[] bytes = new byte[1024];
        InputStream in = clob.getAsciiStream();
        int len = 0;
        while ((len = in.read(bytes)) != -1) {
          str.append(new String(bytes, 0, len, "UTF-8"));
        }
        clob.free();
        return str.toString().trim();
      case Types.BLOB:
        Blob blob = rs.getBlob(index);
        if (blob == null) {
          return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bytes = new byte[1024];
        in = blob.getBinaryStream();
        len = 0;
        while ((len = in.read(bytes)) != -1) {
          out.write(bytes, 0, len);
        }
        blob.free();
        return out.toByteArray();
      default:
        return rs.getObject(index);
      }
    } catch (IOException ex) {
      throw new SQLException(ex);
    }
  }

}
