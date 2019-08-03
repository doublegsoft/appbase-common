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

package net.doublegsoft.appbase.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.doublegsoft.appbase.ObjectMap;
import net.doublegsoft.appbase.Pagination;
import net.doublegsoft.appbase.SqlParams;
import net.doublegsoft.appbase.dao.CommonDataAccess;
import net.doublegsoft.appbase.dao.DataAccessException;
import net.doublegsoft.appbase.dao.JdbcCommonDataAccess;
import net.doublegsoft.appbase.sql.SqlManager;

/**
 *
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class CommonService {

  protected SqlManager sqlManager;

  protected CommonDataAccess commonDataAccess;

  private static final Logger TRACER = LoggerFactory.getLogger(CommonService.class);

  private static boolean DEBUG;

  static {
    try {
      DEBUG = "true".equals(System.getProperty("commonservice.debug"));
    } catch (Exception ex) {

    }
  }

  /**
   * Paginates the results.
   * 
   * @param sqlId
   *          the sql id managed by {@link net.doublegsoft.appbase.sql.SqlManager} instance
   * 
   * @param start
   *          the start index
   * 
   * @param limit
   *          the limit count
   * 
   * @param params
   *          the sql parameters
   * 
   * @return the page results
   * 
   * @throws ServiceException
   *           in case of data access errors or not found sql
   * 
   * @see net.doublegsoft.appbase.dao.CommonDataAccess#paginate(String, int, int)
   */
  public Pagination<ObjectMap> paginate(String sqlId, int start, int limit, SqlParams params) throws ServiceException {
    try {
      String sql = sqlManager.getSql(sqlId, params);
      if (DEBUG) {
        TRACER.info(sql);
      }
      return commonDataAccess.paginate(sql, start, limit);
    } catch (DataAccessException | IOException ex) {
      throw new ServiceException(ex);
    }
  }

  /**
   * Executes the any sql without generated values.
   * 
   * @param sqlId
   *          the sql id managed by {@link net.doublegsoft.appbase.sql.SqlManager} instance
   * 
   * @param params
   *          the sql parameters
   * 
   * @throws ServiceException
   *           in case of data access errors or not found sql
   */
  public void execute(String sqlId, SqlParams params) throws ServiceException {
    try {
      String sql = sqlManager.getSql(sqlId, params);
      if (DEBUG) {
        TRACER.info(sql);
      }
      commonDataAccess.execute(sql);
    } catch (DataAccessException | IOException ex) {
      throw new ServiceException(ex);
    }
  }

  public void execute(String sqlId, String name, Object value) throws ServiceException {
    execute(sqlId, new SqlParams().set(name, value));
  }

  /**
   * Executes batch sql statements for insert, update or delete.
   * 
   * @param sqls
   *          the sql statements
   * 
   * @throws ServiceException
   *           in case of data access errors or not found sql
   */
  public void batch(List<String> sqls) throws ServiceException {
    try {
      commonDataAccess.batch(sqls);
    } catch (DataAccessException ex) {
      throw new ServiceException(ex);
    }
  }

  /**
   * Executes the insert sql and gets the auto generated keys.
   * 
   * @param sqlId
   *          the sql id managed by {@link net.doublegsoft.appbase.sql.SqlManager} instance
   * 
   * @param params
   *          the sql parameters
   * 
   * @return the generated keys
   * 
   * @throws ServiceException
   *           in case of data access errors or not found sql
   * 
   * @see net.doublegsoft.appbase.dao.CommonDataAccess#insert(java.lang.String)
   */
  public ObjectMap insert(String sqlId, SqlParams params) throws ServiceException {
    try {
      String sql = sqlManager.getSql(sqlId, params);
      if (DEBUG) {
        TRACER.info(sql);
      }
      return commonDataAccess.insert(sql);
    } catch (DataAccessException | IOException ex) {
      throw new ServiceException(ex);
    }
  }

  /**
   * Gets the single row and the single column result.
   * 
   * @param <T>
   *          the generic jdbc-java type
   * 
   * @param sqlId
   *          the sql id managed by {@link net.doublegsoft.appbase.sql.SqlManager} instance
   * 
   * @param params
   *          the sql parameters
   * 
   * @return the value
   * 
   * @throws ServiceException
   *           in case of data access errors or not found sql
   * 
   * @see net.doublegsoft.appbase.dao.CommonDataAccess#value(java.lang.String)
   */
  public <T> T value(String sqlId, SqlParams params) throws ServiceException {
    try {
      String sql = sqlManager.getSql(sqlId, params);
      if (DEBUG) {
        TRACER.info(sql);
      }
      return commonDataAccess.value(sql);
    } catch (DataAccessException | IOException ex) {
      throw new ServiceException(ex);
    }
  }

  /**
   * Gets the many results.
   * 
   * @param sqlId
   *          the sql id managed by {@link net.doublegsoft.appbase.sql.SqlManager} instance
   * 
   * @param params
   *          the sql parameters
   * 
   * @return the many results
   * 
   * @throws ServiceException
   *           in case of data access errors or not found sql
   * 
   * @see net.doublegsoft.appbase.dao.CommonDataAccess#many(java.lang.String)
   */
  public List<ObjectMap> many(String sqlId, SqlParams params) throws ServiceException {
    try {
      String sql = sqlManager.getSql(sqlId, params);
      if (DEBUG) {
        TRACER.info(sql);
      }
      return commonDataAccess.many(sql);
    } catch (DataAccessException | IOException ex) {
      throw new ServiceException(ex);
    }
  }

  public List<ObjectMap> many(String sqlId, String name, Object value) throws ServiceException {
    return many(sqlId, new SqlParams().set(name, value));
  }

  public List<ObjectMap> all(String sqlId, SqlParams params) throws ServiceException {
    try {
      String sql = sqlManager.getSql(sqlId, params);
      if (DEBUG) {
        TRACER.info(sql);
      }
      return commonDataAccess.many2(sql);
    } catch (DataAccessException | IOException ex) {
      throw new ServiceException(ex);
    }
  }

  /**
   * Gets the results as a tree by group field name.
   * 
   * @param sqlId
   *          the sql id
   * 
   * @param params
   *          the sql parameters
   * 
   * @param groupField
   *          the group field name
   * 
   * @return the results as a tree, and the non-leaf node has a appended property named "children"
   * 
   * @throws ServiceException
   *           in case of any errors
   */
  public List<ObjectMap> tree(String sqlId, SqlParams params, String groupField) throws ServiceException {
    List<ObjectMap> objs = many(sqlId, params);
    List<ObjectMap> retVal = new ArrayList<>();
    Map<Object, ObjectMap> groups = new HashMap<>();
    objs.forEach((o) -> {
      Object val = o.get(groupField);
      ObjectMap group = null;
      if (!groups.containsKey(val)) {
        group = new ObjectMap();
        group.set(groupField, val);
      } else {
        group = groups.get(val);
      }
      group.add("children", o);
    });
    retVal.addAll(groups.values());
    return retVal;
  }

  /**
   * Gets the results as a tree.
   * <p>
   * The object list reference themselves with the childField(id) and the parentField(parentId).
   * 
   * @param sqlId
   *          the sql id
   * 
   * @param params
   *          the sql parameters
   * 
   * @param childField
   *          the child field name
   * 
   * @param parentField
   *          the parent field name
   * 
   * @return the resutls as a tree, and the non-leaf node has a appended property named "children"
   * 
   * @throws ServiceException
   *           in case of any errors
   */
  public List<ObjectMap> tree(String sqlId, SqlParams params, String childField, String parentField)
      throws ServiceException {
    List<ObjectMap> objs = many(sqlId, params);
    List<ObjectMap> retVal = new ArrayList<>();
    objs.forEach((o) -> {
      String str = String.valueOf(o.get(parentField));
      if ("null".equals(str) || str.trim().length() == 0 || "0".equals(str.trim())) {
        retVal.add(o);
      }
      objs.forEach((o1) -> {
        if (o.get(childField).equals(o1.get(parentField))) {
          o.add("children", o1);
        }
      });
    });
    return retVal;
  }

  /**
   * Gets a single result or {@code null}.
   * 
   * @param sqlId
   *          the sql id managed by {@link net.doublegsoft.appbase.sql.SqlManager} instance
   * 
   * @param params
   *          the sql parameters
   * 
   * @return a single result
   * 
   * @throws ServiceException
   *           in case of data access errors or not found sql
   * 
   * @see net.doublegsoft.appbase.dao.CommonDataAccess#single(java.lang.String)
   */
  public ObjectMap single(String sqlId, SqlParams params) throws ServiceException {
    try {
      String sql = sqlManager.getSql(sqlId, params);
      if (DEBUG) {
        TRACER.info(sql);
      }
      return commonDataAccess.single(sql);
    } catch (DataAccessException | IOException ex) {
      throw new ServiceException(ex);
    }
  }

  /**
   * Gets a single result or {@code null}.
   * 
   * @param sqlId
   *          the sql id managed by {@link net.doublegsoft.appbase.sql.SqlManager} instance
   * 
   * @param name
   *          the parameter name
   * 
   * @param value
   *          the parameter value
   * 
   * @return a single result
   * 
   * @throws ServiceException
   *           in case of data access errors or not found sql
   */
  public ObjectMap single(String sqlId, String name, Object value) throws ServiceException {
    return single(sqlId, new SqlParams().set(name, value));
  }

  public void prepare(String sqlId, SqlParams params, Object... objs) throws ServiceException {
    try {
      String sql = sqlManager.getSql(sqlId, params);
      if (DEBUG) {
        TRACER.info(sql);
      }
      commonDataAccess.prepare(sql, objs);
    } catch (DataAccessException | IOException ex) {
      throw new ServiceException(ex);
    }
  }

  public List<ObjectMap> prepareQuery(String sqlId, Object... objs) throws ServiceException {
    try {
      String sql = sqlManager.getSql(sqlId, new SqlParams());
      if (DEBUG) {
        TRACER.info(sql);
      }
      return commonDataAccess.prepareQuery(sql, objs);
    } catch (DataAccessException | IOException ex) {
      throw new ServiceException(ex);
    }
  }


  /**
   * @see JdbcCommonDataAccess#call(String)
   *
   * @since 3.1
   */
  public List<ObjectMap> call(String sql) throws ServiceException {
    try {
      return commonDataAccess.call(sql);
    } catch (DataAccessException ex) {
      throw new ServiceException(ex);
    }
  }

  public void beginTransaction(int level)  {
    if (commonDataAccess instanceof JdbcCommonDataAccess) {
      try {
        ((JdbcCommonDataAccess) commonDataAccess).beginTransaction(level);
      } catch (DataAccessException ex) {
        TRACER.error("数据库开始事务出错", ex);
      }
    }
  }

  public void beginTransaction() throws ServiceException {
    if (commonDataAccess instanceof JdbcCommonDataAccess) {
      try {
        ((JdbcCommonDataAccess) commonDataAccess).beginTransaction();
      } catch (DataAccessException ex) {
        TRACER.error("数据库开始事务出错", ex);
      }
    }
  }

  public void commit() {
    if (commonDataAccess instanceof JdbcCommonDataAccess) {
      try {
        ((JdbcCommonDataAccess) commonDataAccess).commit();
      } catch (DataAccessException ex) {
        TRACER.error("数据库提交事务出错", ex);
      }
    }
  }

  public void rollback() {
    if (commonDataAccess instanceof JdbcCommonDataAccess) {
      try {
        ((JdbcCommonDataAccess) commonDataAccess).rollback();
      } catch (DataAccessException ex) {
        TRACER.error("数据库回滚事务出错", ex);
      }
    }
  }

  public void setDebug(boolean debug) {
    DEBUG = debug;
  }

  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  public void setCommonDataAccess(CommonDataAccess commonDataAccess) {
    this.commonDataAccess = commonDataAccess;
  }

  public CommonDataAccess getCommonDataAccess() {
    return commonDataAccess;
  }
}
