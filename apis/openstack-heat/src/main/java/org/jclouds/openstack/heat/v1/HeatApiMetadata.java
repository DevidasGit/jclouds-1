/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.openstack.heat.v1;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import org.jclouds.apis.ApiMetadata;
import org.jclouds.openstack.heat.v1.config.HeatHttpApiModule;
import org.jclouds.openstack.keystone.v2_0.config.AuthenticationApiModule;
import org.jclouds.openstack.keystone.v2_0.config.CredentialTypes;
import org.jclouds.openstack.keystone.v2_0.config.KeystoneAuthenticationModule;
import org.jclouds.openstack.keystone.v2_0.config.KeystoneAuthenticationModule.RegionModule;
import org.jclouds.rest.internal.BaseHttpApiMetadata;

import java.net.URI;
import java.util.Properties;

import static org.jclouds.openstack.keystone.v2_0.config.KeystoneProperties.CREDENTIAL_TYPE;
import static org.jclouds.openstack.keystone.v2_0.config.KeystoneProperties.SERVICE_TYPE;

/**
 * Implementation of {@link ApiMetadata} for the Heat API.
 */
@AutoService(ApiMetadata.class)
public class HeatApiMetadata extends BaseHttpApiMetadata<HeatApi> {

   @Override
   public Builder toBuilder() {
      return new Builder().fromApiMetadata(this);
   }

   public HeatApiMetadata() {
      this(new Builder());
   }

   protected HeatApiMetadata(Builder builder) {
      super(builder);
   }

   public static Properties defaultProperties() {
      Properties properties = BaseHttpApiMetadata.defaultProperties();
      properties.setProperty(SERVICE_TYPE, "orchestration");
      properties.setProperty(CREDENTIAL_TYPE, CredentialTypes.PASSWORD_CREDENTIALS);
      return properties;
   }

   public static class Builder extends BaseHttpApiMetadata.Builder<HeatApi, Builder> {

      protected Builder() {
          id("openstack-heat")
         .name("OpenStack Heat API")
         .identityName("${tenantName}:${userName} or ${userName}, if your keystone supports a default tenant")
         .credentialName("${password}")
         .documentation(URI.create("https://wiki.openstack.org/wiki/Heat"))
         .version("1")
         .endpointName("Keystone base url ending in /v2.0/")
         .defaultEndpoint("http://localhost:5000/v2.0/")
         .defaultProperties(HeatApiMetadata.defaultProperties())
         .defaultModules(ImmutableSet.<Class<? extends Module>>builder()
                           .add(AuthenticationApiModule.class)
                           .add(KeystoneAuthenticationModule.class)
                           .add(RegionModule.class)
                           .add(HeatHttpApiModule.class).build());
      }

      @Override
      public HeatApiMetadata build() {
         return new HeatApiMetadata(this);
      }

      @Override
      protected Builder self() {
         return this;
      }
   }
}