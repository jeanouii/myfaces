/*
 * Copyright 2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.myfaces.custom.stylesheet;

import javax.faces.component.UIComponent;

import org.apache.myfaces.taglib.html.HtmlOutputTextTagBase;

/**
 * @author mwessendorf (latest modification by $Author$) 
 * @version $Revision$ $Date$ 
 * $Log$
 * Revision 1.3  2005/02/18 17:19:31  matzew
 * added release() to tag clazzes.
 *
 * Revision 1.2  2004/10/13 11:50:58  matze
 * renamed packages to org.apache
 *
 * Revision 1.1  2004/08/18 15:36:08  mwessendorf
 * added a new Stylesheet-Component
 * 
*/
public class StylesheetTag extends HtmlOutputTextTagBase {


    private String _path = null;
	// User Role support
	private String _enabledOnUserRole;
	private String _visibleOnUserRole;

    public String getComponentType() {

        return Stylesheet.COMPONENT_TYPE;

    }

    public String getRendererType() {

        return "org.apache.myfaces.Stylesheet";

    }


     public void release() {

        super.release();
        _path=null;
    	_enabledOnUserRole=null;
    	_visibleOnUserRole=null;


    }
 
     /**
 	 * overrides setProperties() form UIComponentTag.
 	 */
    protected void setProperties(UIComponent component) {

        super.setProperties(component);
        setStringProperty(component, "path", _path);

    }
    
    //---------------------------------------------only the Setters
   
    public void setPath(String path) {
        this._path = path;
    }
	public void setEnabledOnUserRole(String string) {
		_enabledOnUserRole = string;
	}

	public void setVisibleOnUserRole(String string) {
		_visibleOnUserRole = string;
	}
}