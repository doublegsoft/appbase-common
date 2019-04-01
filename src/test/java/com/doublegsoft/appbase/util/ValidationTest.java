/* 
 * Copyright (C) doublegsoft.biz - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 * Proprietary and confidential
 */
package com.doublegsoft.appbase.util;

import org.junit.Assert;
import org.junit.Test;

import net.doublegsoft.appbase.util.Validation;

/**
 * 
 */
public class ValidationTest {

  private Validation validation = new Validation();

  @Test
  public void testMobiles() {
    Assert.assertTrue(validation.validateMobile("13988776655"));
    Assert.assertFalse(validation.validateMobile("139887766552"));
  }

  @Test
  public void testEmails() {
    Assert.assertTrue(validation.validateEmail("guo.guo.gan@gmail.com"));
    Assert.assertFalse(validation.validateEmail("guo guo.gan@gmail.com"));
  }

}
