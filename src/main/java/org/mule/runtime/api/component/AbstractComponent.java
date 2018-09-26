/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.component;

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;

import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.api.component.location.Location;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * Base implementation for {@link Component}
 */
public abstract class AbstractComponent implements Component {

  public static QName LOCATION_KEY = new QName("mule", "COMPONENT_LOCATION");
  public static QName ROOT_CONTAINER_NAME_KEY = new QName("mule", "ROOT_CONTAINER_NAME");

  private volatile Map<QName, Object> annotations = emptyMap();

  private final Object locationInitLock = new Object();
  private volatile ComponentLocation location;
  private final Object rootContainerLocationInitLock = new Object();
  private volatile Location rootContainerLocation;

  @Override
  public Object getAnnotation(QName qName) {
    return annotations.get(qName);
  }

  @Override
  public Map<QName, Object> getAnnotations() {
    return unmodifiableMap(annotations);
  }

  @Override
  public void setAnnotations(Map<QName, Object> newAnnotations) {
    annotations = new HashMap<>(newAnnotations);
  }

  @Override
  public ComponentLocation getLocation() {
    if (location == null) {
      synchronized (locationInitLock) {
        if (location == null) {
          this.location = (ComponentLocation) getAnnotation(LOCATION_KEY);
        }
      }
    }
    return location;
  }

  @Override
  public Location getRootContainerLocation() {
    if (rootContainerLocation == null) {
      synchronized (rootContainerLocationInitLock) {
        if (rootContainerLocation == null) {
          String rootContainerName = (String) getAnnotation(ROOT_CONTAINER_NAME_KEY);
          if (rootContainerName == null) {
            rootContainerName = getLocation().getRootContainerName();
          }
          this.rootContainerLocation = Location.builder().globalName(rootContainerName).build();
        }
      }
    }
    return rootContainerLocation;
  }
}
