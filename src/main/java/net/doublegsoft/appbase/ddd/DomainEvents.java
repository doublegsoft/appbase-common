/* 
 * Copyright (C) doublegsoft.net - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 * Proprietary and confidential
 */
package net.doublegsoft.appbase.ddd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: ADD DESCRIPTION
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 3.0
 */
public final class DomainEvents {

  private final static Logger TRACER = LoggerFactory.getLogger(DomainEvents.class);

  private final static Map<Class<Event>, List<EventHandler<Event>>> HANDLERS = new HashMap<>();

  /**
   * 
   * @param clazz
   * @param handler
   */
  public static void register(Class<Event> clazz, EventHandler<Event> handler) {
    if (clazz == null || handler == null) {
      return;
    }
    List<EventHandler<Event>> chain = HANDLERS.get(clazz);
    if (chain == null) {
      chain = new ArrayList<>();
      HANDLERS.put(clazz, chain);
    }
    chain.add(handler);
  }

  /**
   * 
   * @param event
   */
  public static void raise(Event event) {
    if (event == null) {
      return;
    }
    List<EventHandler<Event>> chain = HANDLERS.get(event.getClass());
    if (chain == null) {
      return;
    }
    for (EventHandler<Event> handler : chain) {
      try {
        handler.handle(event);
      } catch (DomainException ex) {

      }
    }
  }

  private DomainEvents() {

  }

}
