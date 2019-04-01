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

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.doublegsoft.appbase.SqlParams;

/**
 *
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class SqlManager {

  private static final Map<String, String> sqls = new HashMap<>();

  protected static final Configuration FREEMARKER = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

  protected static final StringTemplateLoader TEMPLATE_LOADER = new StringTemplateLoader();

  static {
    StringBuilder include = new StringBuilder();
    byte[] buf = new byte[1024];
    int len = 0;
    try (InputStream in = SqlManager.class.getResourceAsStream("/sql-include.ftl")) {
      while ((len = in.read(buf)) != -1) {
        include.append(new String(buf, 0, len));
      }
    } catch (IOException ex) {
      ex.printStackTrace(System.err);
    }
    FREEMARKER.setTemplateLoader(TEMPLATE_LOADER);
    TEMPLATE_LOADER.putTemplate("autoInclude", include.toString());
    FREEMARKER.addAutoInclude("autoInclude");
    // NOT NEED BELOW LINE
    // FREEMARKER.setObjectWrapper(new DefaultObjectWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
  }

  /**
   * @param resources
   *          the resources to set
   */
  public void setResources(List<String> resources) {
    resources.forEach((r) -> {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      try {
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(SqlManager.class.getResourceAsStream(r));
        Element root = doc.getDocumentElement();
        NodeList children = root.getElementsByTagName("sql");
        for (int i = 0; i < children.getLength(); ++i) {
          Element el = (Element) children.item(i);
          sqls.put(el.getAttribute("id"), el.getTextContent());
          TEMPLATE_LOADER.putTemplate(el.getAttribute("id"), el.getTextContent());
        }
      } catch (Exception ex) {
        System.err.println("error to process \"" + r + "\"");
        ex.printStackTrace(System.out);
      }
    });
  }

  /**
   * Gets the real sql which processed by template engine.
   * 
   * @param sqlId
   *          the sql id
   * 
   * @param params
   *          the sql parameters
   * 
   * @return the sql
   * 
   * @throws IOException
   *           in case of any io errors
   */
  public String getSql(String sqlId, SqlParams params) throws IOException {
    String tpl = sqls.get(sqlId);
    if (params == null) {
      return tpl;
    }
    Template t = FREEMARKER.getTemplate(sqlId);
    StringWriter retVal = new StringWriter();
    try {
      t.process(params, retVal);
    } catch (TemplateException ex) {
      throw new IOException(ex);
    }
    retVal.flush();
    return retVal.toString();
  }

  /**
   * Gets the real sql processed by template engine.
   * 
   * @param sqlId
   *          the sql id
   * 
   * @param vars
   *          the template variables used by template engine
   * 
   * @param defaultTpl
   *          the default template string if not found with the sql id
   * 
   * @return the sql
   * 
   * @throws IOException
   *           in case of any io errors
   */
  public String getSql(String sqlId, Map<String, Object> vars, String defaultTpl) throws IOException {
    if (vars instanceof SqlParams) {
      return getSql(sqlId, (SqlParams) vars);
    }
    if (!sqls.containsKey(sqlId)) {
      sqls.put(sqlId, defaultTpl);
      TEMPLATE_LOADER.putTemplate(sqlId, defaultTpl);
    }
    Template t = FREEMARKER.getTemplate(sqlId);
    StringWriter retVal = new StringWriter();
    try {
      t.process(vars, retVal);
    } catch (TemplateException ex) {
      throw new IOException(ex);
    }
    retVal.flush();
    return retVal.toString();
  }

  public String getSql(String sqlId) throws IOException {
    return getSql(sqlId, null);
  }

}
