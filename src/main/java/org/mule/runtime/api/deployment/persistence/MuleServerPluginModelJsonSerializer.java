/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.persistence;

import org.mule.runtime.api.deployment.meta.MuleServerPluginModel;

/**
 * Serializer capable of marshalling an {@link MuleServerPluginModel} instance
 * to {@code JSON} format and back
 *
 * @since 1.0
 */
public class MuleServerPluginModelJsonSerializer extends AbstractMuleArtifactModelJsonSerializer<MuleServerPluginModel> {

  @Override
  protected Class<MuleServerPluginModel> getParameterizedClass() {
    return MuleServerPluginModel.class;
  }
}
