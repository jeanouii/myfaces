/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.spi;

import java.security.AccessController;
import java.security.PrivilegedActionException;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;

import org.apache.commons.discovery.tools.DiscoverSingleton;
import org.apache.myfaces.spi.impl.DefaultFaceletConfigResourceProviderFactory;

/**
 * 
 * @since 2.0.2
 * @author Leonardo Uribe
 */
public abstract class FaceletConfigResourceProviderFactory
{
    protected static final String FACTORY_DEFAULT = DefaultFaceletConfigResourceProviderFactory.class.getName();

    //private static final String FACTORY_KEY = FaceletConfigResourceProviderFactory.class.getName();

    public static FaceletConfigResourceProviderFactory getFacesConfigResourceProviderFactory(ExternalContext ctx)
    {
        //FaceletConfigResourceProviderFactory instance = (FaceletConfigResourceProviderFactory) ctx.getApplicationMap().get(FACTORY_KEY);
        //if (instance != null)
        //{
        //    return instance;
        //}
        FaceletConfigResourceProviderFactory lpf = null;
        try
        {

            if (System.getSecurityManager() != null)
            {
                lpf = (FaceletConfigResourceProviderFactory) AccessController.doPrivileged(new java.security.PrivilegedExceptionAction<Object>()
                        {
                            public Object run() throws PrivilegedActionException
                            {
                                return DiscoverSingleton.find(
                                        FaceletConfigResourceProviderFactory.class,
                                        FACTORY_DEFAULT);
                            }
                        });
            }
            else
            {
                lpf = (FaceletConfigResourceProviderFactory) DiscoverSingleton.find(FaceletConfigResourceProviderFactory.class, FACTORY_DEFAULT);
            }
        }
        catch (PrivilegedActionException pae)
        {
            throw new FacesException(pae);
        }
        //if (lpf != null)
        //{
        //    setFaceletConfigResourceProviderFactory(ctx, lpf);
        //}
        return lpf;
    }

    //public static void setFaceletConfigResourceProviderFactory(ExternalContext ctx, FaceletConfigResourceProviderFactory instance)
    //{
    //    ctx.getApplicationMap().put(FACTORY_KEY, instance);
    //}

    public abstract FaceletConfigResourceProvider createFaceletConfigResourceProvider(ExternalContext externalContext);
}