package net.doublegsoft.appbase.service;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import net.doublegsoft.appbase.ObjectMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GroovyService {

  private CommonService commonService;

  public <T> T execute(String scriptPath, ObjectMap params) throws ServiceException {
    Binding binding = new Binding();
    params.entrySet().forEach(e -> {
      binding.setVariable(e.getKey(), e.getValue());
    });
    GroovyShell shell = new GroovyShell(binding);

    try {
      String groovyScript = new String(Files.readAllBytes(new File(scriptPath).toPath()));
      return (T) shell.evaluate(groovyScript);
    } catch (IOException ex) {
      throw new ServiceException(ex);
    }
  }

  public void setCommonService(CommonService commonService) {
    this.commonService = commonService;
  }
}
