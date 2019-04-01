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

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import net.doublegsoft.appbase.ObjectMap;
import net.doublegsoft.appbase.Pagination;

/**
 *
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public interface CommonDataAccess {

  /**
   * Executes the insert sql and gets the auto generated keys.
   * 
   * @param sql
   *          the sql
   * 
   * @return the generated keys
   * 
   * @throws DataAccessException
   *           in case of access errors
   */
  ObjectMap insert(String sql) throws DataAccessException;

  /**
   * Executes the any sql without generated values.
   * 
   * @param sql
   *          the sql
   * 
   * @throws DataAccessException
   *           in case of access errors
   */
  void execute(String sql) throws DataAccessException;

  /**
   * Paginates the results.
   * 
   * @param sql
   *          the sql
   * 
   * @param start
   *          the start index
   * 
   * @param limit
   *          the limit count
   * 
   * @return the paginated results
   * 
   * @throws DataAccessException
   *           in case of access errors
   */
  Pagination<ObjectMap> paginate(String sql, int start, int limit) throws DataAccessException;

  default <T> Pagination<T> paginate(String sql, Class<T> klass, int start, int limit) throws DataAccessException {
    Pagination<ObjectMap> page = paginate(sql, start, limit);
    Pagination<T> retVal = new Pagination<>();
    page.getData().stream().forEach((d) -> {
      try {
        T obj = klass.newInstance();
        BeanUtils.populate(obj, d);
        retVal.add(obj);
      } catch (IllegalAccessException | InvocationTargetException | InstantiationException ex) {
        throw new RuntimeException(ex);
      }
    });
    return retVal;
  }

  /**
   * Gets the single row and single column result.
   * 
   * @param <T>
   *          the jdbc-java type
   * 
   * @param sql
   *          the sql
   * 
   * @return the value
   * 
   * @throws DataAccessException
   *           in case of access errors
   */
  <T> T value(String sql) throws DataAccessException;

  /**
   * Executes batch sql statements for insert, update, delete.
   * 
   * @param sqls
   *          the batch sqls
   * 
   * @throws DataAccessException
   *           in case of database access errors
   */
  void batch(List<String> sqls) throws DataAccessException;

  /**
   * Prepares and executes a sql statement with only blobs.
   * 
   * @param sql
   *          the sql statement
   * 
   * @param blobs
   *          the blob parameters
   * 
   * @throws DataAccessException
   *           in case of database access errors
   */
  public void prepare(String sql, Blob... blobs) throws DataAccessException;

  /**
   * Prepares and executes a sql statement with only input streams.
   * 
   * @param sql
   *          the sql statement
   * 
   * @param streams
   *          the input stream parameters
   * 
   * @throws DataAccessException
   *           in case of database access errors
   */
  public void prepare(String sql, InputStream... streams) throws DataAccessException;

  /**
   * Gets the many results.
   * 
   * @param sql
   *          the sql
   * 
   * @return the many results
   * 
   * @throws DataAccessException
   *           in case of access errors
   */
  default List<ObjectMap> many(String sql) throws DataAccessException {
    return paginate(sql, 0, -1).getData();
  }

  default <T> List<T> many(String sql, Class<T> klass) throws DataAccessException {
    List<T> retVal = new ArrayList<>();
    List<ObjectMap> rs = many(sql);
    rs.forEach((d) -> {
      try {
        T obj = klass.newInstance();
        BeanUtils.populate(obj, d);
        retVal.add(obj);
      } catch (IllegalAccessException | InvocationTargetException | InstantiationException ex) {
        throw new RuntimeException(ex);
      }
    });
    return retVal;
  }

  public <T> List<T> many2(String sql) throws DataAccessException;

  /**
   * Gets the many results as a list.
   * 
   * @param sql
   *          the sql
   * 
   * @return the many results as list
   * 
   * @throws DataAccessException
   *           in case of access errors
   */
  List<List<Object>> list(String sql) throws DataAccessException;

  /**
   * Gets a single result or {@code null}.
   * 
   * @param sql
   *          the sql
   * 
   * @return a single result
   * 
   * @throws DataAccessException
   *           in case of access errors
   */
  ObjectMap single(String sql) throws DataAccessException;

  default <T> T single(String sql, Class<T> klass) throws DataAccessException {
    ObjectMap om = single(sql);
    try {
      T retVal = klass.newInstance();
      BeanUtils.populate(retVal, om);
      return retVal;
    } catch (IllegalAccessException | InvocationTargetException | InstantiationException ex) {
      throw new DataAccessException(ex);
    }
  }

  /**
   * Supports to call stored procedure to fetch data.
   *
   * @param sql
   *        the procedure call
   *
   * @return the fetching data
   *
   * @throws DataAccessException
   *        in case of any other errors
   *
   * @since 3.1
   */
  List<ObjectMap> call(String sql) throws DataAccessException;

}
