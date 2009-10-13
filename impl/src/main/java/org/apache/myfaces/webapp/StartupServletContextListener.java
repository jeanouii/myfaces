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
package org.apache.myfaces.webapp;

import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FactoryFinder;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.myfaces.config.ManagedBeanBuilder;
import org.apache.myfaces.shared_impl.util.ClassUtils;
import org.apache.myfaces.util.ContainerUtils;

/**
 * Initialise the MyFaces system.
 * <p>
 * This context listener is registered by the JSP TLD file for the standard JSF "f" components. Normally, servlet
 * containers will automatically load and process .tld files at startup time, and therefore register and run this class
 * automatically.
 * <p>
 * Some very old servlet containers do not do this correctly, so in those cases this listener may be registered manually
 * in web.xml. Registering it twice (ie in both .tld and web.xml) will result in a harmless warning message being
 * generated. Very old versions of MyFaces Core do not register the listener in the .tld file, so those also need a
 * manual entry in web.xml. However all versions since at least 1.1.2 have this entry in the tld.
 * 
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class StartupServletContextListener extends AbstractMyFacesListener implements ServletContextListener
{
    static final String FACES_INIT_DONE = StartupServletContextListener.class.getName() + ".FACES_INIT_DONE";
    /*comma delimited list of plugin classes which can be hooked into myfaces*/
    static final String FACES_INIT_PLUGINS = "org.apache.myfaces.FACES_INIT_PLUGINS";

    private static final byte FACES_INIT_PHASE_PREINIT = 0;
    private static final byte FACES_INIT_PHASE_POSTINIT = 1;
    private static final byte FACES_INIT_PHASE_PREDESTROY = 2;
    private static final byte FACES_INIT_PHASE_POSTDESTROY = 3;

    //private static final Log log = LogFactory.getLog(StartupServletContextListener.class);
    private static final Logger log = Logger.getLogger(StartupServletContextListener.class.getName());

    private FacesInitializer _facesInitializer;
    private ServletContext _servletContext;


    /**
     * the central initialisation event dispatcher which calls
     * our listeners
     * @param event
     * @param operation
     */
    private void dispatchInitializationEvent(ServletContextEvent event, int operation) {
        String [] pluginEntries = (String []) _servletContext.getAttribute(FACES_INIT_PLUGINS);

        if(pluginEntries == null) {
            String plugins = (String) _servletContext.getInitParameter(FACES_INIT_PLUGINS);
            if(plugins == null) return;
            log.info("MyFaces Plugins found");
            pluginEntries = plugins.split(",");
            _servletContext.setAttribute(FACES_INIT_PLUGINS, pluginEntries);
        }

        //now we process the plugins
        for(String plugin: pluginEntries) {
            log.info("Processing plugin:"+plugin);
            try {
                //for now the initializers have to be stateless to
                //so that we do not have to enforce that the initializer
                //must be serializable
                Class pluginClass = ClassUtils.getContextClassLoader().loadClass(plugin);
                StartupListener initializer = (StartupListener) pluginClass.newInstance();
                
                switch(operation) {
                    case FACES_INIT_PHASE_PREINIT:
                        initializer.preInit(event);
                        break;
                    case FACES_INIT_PHASE_POSTINIT:
                        initializer.postInit(event);
                        break;
                    case FACES_INIT_PHASE_PREDESTROY:
                        initializer.preDestroy(event);
                        break;
                    default:
                        initializer.postDestroy(event);
                        break;
                }

               
            } catch (ClassNotFoundException e) {
                log.log(Level.SEVERE, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                log.log(Level.SEVERE, e.getMessage(), e);
            } catch (InstantiationException e) {
                log.log(Level.SEVERE, e.getMessage(), e);
            }

        }
        log.info("Processing MyFaces plugins done");
    }


    public void contextInitialized(ServletContextEvent event)
    {
        if (_servletContext != null)
        {
            throw new IllegalStateException("context is already initialized");
        }
        _servletContext = event.getServletContext();
        Boolean b = (Boolean) _servletContext.getAttribute(FACES_INIT_DONE);

        if (b == null || b.booleanValue() == false)
        {
            dispatchInitializationEvent(event, FACES_INIT_PHASE_PREINIT);
            getFacesInitializer().initFaces(_servletContext);
            dispatchInitializationEvent(event, FACES_INIT_PHASE_POSTINIT);
            _servletContext.setAttribute(FACES_INIT_DONE, Boolean.TRUE);
        }
        else
        {
            log.info("MyFaces already initialized");
        }
    }

    protected FacesInitializer getFacesInitializer()
    {
        if (_facesInitializer == null)
        {
            if (ContainerUtils.isJsp21())
            {
                _facesInitializer = new Jsp21FacesInitializer();
            }
            else
            {
                _facesInitializer = new Jsp20FacesInitializer();
            }
        }

        return _facesInitializer;
    }

    /**
     * configure the faces initializer
     * 
     * @param facesInitializer
     */
    public void setFacesInitializer(FacesInitializer facesInitializer)
    {
        if (_facesInitializer != null && _facesInitializer != facesInitializer && _servletContext != null)
        {
            _facesInitializer.destroyFaces(_servletContext);
        }
        _facesInitializer = facesInitializer;
        if (_servletContext != null)
        {
            facesInitializer.initFaces(_servletContext);
        }
    }

    public void contextDestroyed(ServletContextEvent event)
    {
        doPredestroy(event);

        if (_facesInitializer != null && _servletContext != null)
        {
            _facesInitializer.destroyFaces(_servletContext);
        }
        FactoryFinder.releaseFactories();
        dispatchInitializationEvent(event, FACES_INIT_PHASE_POSTDESTROY);

        _servletContext = null;
    }

    @SuppressWarnings("unchecked")
    private void doPredestroy(ServletContextEvent event)
    {
        ServletContext ctx = event.getServletContext();
        dispatchInitializationEvent(event, FACES_INIT_PHASE_PREDESTROY);

        Enumeration<String> attributes = ctx.getAttributeNames();

        while (attributes.hasMoreElements())
        {
            String name = attributes.nextElement();
            Object value = ctx.getAttribute(name);
            doPreDestroy(value, name, ManagedBeanBuilder.APPLICATION);
        }
    }
}
