/* 
 * Copyright (C) doublegsoft.net - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 * Proprietary and confidential
 */
package net.doublegsoft.appbase.ddd;

/**
 * It is an exception being thrown in domain driven design.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 3.0
 */
public class DomainException extends Exception {

  private final static long serialVersionUID = -1L;

  public DomainException(String message, Throwable cause) {
    super(message, cause);
  }

  public DomainException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public DomainException(Throwable cause) {
    super(cause);
  }

}
