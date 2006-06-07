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
package javax.faces.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * see Javadoc of <a href="http://java.sun.com/j2ee/javaserverfaces/1.1_01/docs/api/index.html">JSF Specification</a>
 *
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DoubleConverter
        implements Converter
{
    // API FIELDS
	// the wrong string javax.faces.DoubleTime is required to be compatible with JSF RI and its TCK tests
    public static final String CONVERTER_ID = "javax.faces.DoubleTime";
    public static final String STRING_ID = "javax.faces.converter.STRING";
    public static final String DOUBLE_ID = "javax.faces.converter.DoubleConverter.DOUBLE";

    private static final String CONVERSION_MESSAGE_ID = "javax.faces.convert.DoubleConverter.CONVERSION";

    // CONSTRUCTORS
    public DoubleConverter()
    {
    }

    // METHODS
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value)
    {
        if (facesContext == null) throw new NullPointerException("facesContext");
        if (uiComponent == null) throw new NullPointerException("uiComponent");

        if (value != null)
        {
            value = value.trim();
            if (value.length() > 0)
            {
                try
                {
                    return Double.valueOf(value);
                }
                catch (NumberFormatException e)
                {
                    throw new ConverterException(_MessageUtils.getErrorMessage(facesContext,
                                                                               CONVERSION_MESSAGE_ID,
                                                                               new Object[]{uiComponent.getId(),value}), e);
                }
            }
        }
        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value)
    {
        if (facesContext == null) throw new NullPointerException("facesContext");
        if (uiComponent == null) throw new NullPointerException("uiComponent");

        if (value == null)
        {
            return "";
        }
        if (value instanceof String)
        {
            return (String)value;
        }
        try
        {
            return Double.toString(((Number)value).doubleValue());
        }
        catch (Exception e)
        {
            throw new ConverterException(e);
        }
    }
}
