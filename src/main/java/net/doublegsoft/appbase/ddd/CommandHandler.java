/* 
 * Copyright (C) doublegsoft.net - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 * Proprietary and confidential
 */
package net.doublegsoft.appbase.ddd;

/**
 * It is a command handler type.
 *
 * @see {@link Command}
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public interface CommandHandler<C extends Command, R> {

  R handle(C command) throws DomainException;

}
