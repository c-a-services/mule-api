/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.exception;

import static java.lang.String.format;
import static org.mule.runtime.api.util.ComponentLocationProvider.resolveProcessorRepresentation;

import org.mule.runtime.api.i18n.I18nMessage;
import org.mule.runtime.api.meta.NameableObject;
import org.mule.runtime.api.meta.NamedObject;

/**
 * {@code LocatedMuleException} is a general exception that adds context location about the Exception (i.e.: where it
 * occurred in the application).
 *
 * @since 1.0
 */

public class LocatedMuleException extends MuleException {

  /**
   * Serial version
   */
  private static final long serialVersionUID = 6941498759267936649L;

  /**
   * @param component the object that failed during a lifecycle method call
   */
  public LocatedMuleException(Object component) {
    super();
    setLocation(component);
  }

  /**
   * @param message the exception message
   * @param component the object that failed during a lifecycle method call
   */
  public LocatedMuleException(I18nMessage message, Object component) {
    super(message);
    setLocation(component);
  }

  /**
   * @param message the exception message
   * @param cause the exception that cause this exception to be thrown
   * @param component the object that failed during a lifecycle method call
   */
  public LocatedMuleException(I18nMessage message, Throwable cause, Object component) {
    super(message, cause);
    setLocation(component);
  }

  /**
   * @param cause the exception that cause this exception to be thrown
   * @param component the object that failed during a lifecycle method call
   */
  public LocatedMuleException(Throwable cause, Object component) {
    super(cause);
    setLocation(component);
  }


  protected void setLocation(Object component) {
    if (component != null) {
      addInfo(INFO_LOCATION_KEY, resolveProcessorPath(component));
    }
  }

  protected String resolveProcessorPath(Object component) {
    if (component instanceof NamedObject) {
      // Cannot currently get the application name without an event/context.
      return resolveProcessorRepresentation("app", "/" + ((NamedObject) component).getName(),
                                            component);
    } else {
      return resolveProcessorRepresentation("app", (component == null ? "null" : component.toString()), component);
    }
  }

  public static String toString(Object obj) {
    if (obj == null) {
      return "";
    }

    String str = obj.getClass().getName();
    if (obj instanceof NameableObject) {
      str += format(" '%s'", ((NameableObject) obj).getName());
    }
    return str;
  }

}
