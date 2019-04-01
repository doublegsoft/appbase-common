package com.doublegsoft.appbase.service;

import net.doublegsoft.appbase.ObjectMap;
import net.doublegsoft.appbase.service.GroovyService;
import org.junit.Test;
import org.testng.Assert;

public class GroovyServiceTest {

  @Test
  public void test() throws Exception {
    GroovyService serv = new GroovyService();
    ObjectMap params = new ObjectMap();
    ObjectMap param1 = new ObjectMap();
    param1.set("hello", "world");
    params.set("param1", param1);
    String result = serv.execute("./src/test/resources/groovy/test.groovy", params);

    Assert.assertEquals("world", result);
  }

}
