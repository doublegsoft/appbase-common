/* 
 * Copyright (C) doublegsoft.net - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 * Proprietary and confidential
 */
package net.doublegsoft.appbase.ddd;

/**
 * It is an event handler interface to specialize event handler pattern in domain driven design.
 * <p>
 * And visits the link below:
 * <p>
 * <a href=
 * "https://blogs.msdn.microsoft.com/cesardelatorre/2017/02/07/domain-events-vs-integration-events-in-domain-driven-design-and-microservices-architectures/">
 * Domain Events vs. Integration Events in Domain-Driven Design and microservices architectures </a>
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 3.0
 */
public interface EventHandler<E extends Event> {

  void handle(E event) throws DomainException;

}
