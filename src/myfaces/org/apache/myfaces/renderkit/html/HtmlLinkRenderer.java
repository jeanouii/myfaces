/*
 * MyFaces - the free JSF implementation
 * Copyright (C) 2003, 2004  The MyFaces Team (http://myfaces.sourceforge.net)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package net.sourceforge.myfaces.renderkit.html;

import net.sourceforge.myfaces.MyFacesConfig;
import net.sourceforge.myfaces.renderkit.JSFAttr;
import net.sourceforge.myfaces.renderkit.RendererUtils;
import net.sourceforge.myfaces.renderkit.html.util.DummyFormResponseWriter;
import net.sourceforge.myfaces.renderkit.html.util.DummyFormUtils;
import net.sourceforge.myfaces.renderkit.html.util.HTMLUtil;

import javax.faces.application.ViewHandler;
import javax.faces.component.*;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlLinkRenderer
    extends HtmlRenderer
{
    //private static final Log log = LogFactory.getLog(HtmlLinkRenderer.class);

    public boolean getRendersChildren()
    {
        // We must be able to render the children without a surrounding anchor
        // if the Link is disabled
        return true;
    }

    private static final String URL_PARAM_VALUE = "1";

    public void decode(FacesContext facesContext, UIComponent component)
    {
        RendererUtils.checkParamValidity(facesContext, component, null);

        if (component instanceof UICommand)
        {
            String clientId = component.getClientId(facesContext);
            String reqValue = (String)facesContext.getExternalContext().getRequestParameterMap().get(clientId);
            if (reqValue != null && reqValue.equals(URL_PARAM_VALUE))
            {
                component.queueEvent(new ActionEvent(component));
            }
        }
        else if (component instanceof UIOutput)
        {
            //do nothing
        }
        else
        {
            throw new IllegalArgumentException("Unsupported component class " + component.getClass().getName());
        }
    }

    public void encodeBegin(FacesContext facesContext, UIComponent component) throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, component, null);

        if (component instanceof UICommand)
        {
            renderCommandLinkStart(facesContext, (UICommand)component);
        }
        else if (component instanceof UIOutput)
        {
            renderOutputLinkStart(facesContext, (UIOutput)component);
        }
        else
        {
            throw new IllegalArgumentException("Unsupported component class " + component.getClass().getName());
        }
    }

    public void renderCommandLinkStart(FacesContext facesContext, UICommand command)
            throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();

        if (RendererUtils.isEnabledOnUserRole(facesContext, command))
        {
            if (MyFacesConfig.isAllowJavascript(facesContext.getExternalContext()))
            {
                renderJavaScriptAnchorStart(facesContext, writer, command);
            }
            else
            {
                renderNonJavaScriptAnchorStart(facesContext, writer, command);
            }

            HTMLUtil.renderHTMLAttributes(writer, command,
                                          HTML.ANCHOR_PASSTHROUGH_ATTRIBUTES_WITHOUT_STYLE_CLASS);
            HTMLUtil.renderHTMLAttribute(writer, HTML.STYLE_CLASS_ATTR, HTML.STYLE_CLASS_ATTR,
                                         getStyleClass(facesContext, command));
        }

        //MyFaces extension: Render text given by value
        Object value = command.getValue();
        if(value != null)
        {
            writer.writeText(value.toString(), JSFAttr.VALUE_ATTR);
        }
    }


    /**
     * Can be overwritten by derived classes to overrule the style class to be used.
     */
    protected String getStyleClass(FacesContext facesContext, UICommand link)
    {
        if (link instanceof HtmlCommandLink)
        {
            return ((HtmlCommandLink)link).getStyleClass();
        }
        else
        {
            return (String)link.getAttributes().get(HTML.STYLE_CLASS_ATTR);
        }
    }


    private void renderJavaScriptAnchorStart(FacesContext facesContext,
                                             ResponseWriter writer,
                                             UICommand command)
        throws IOException
    {
        //Find form
        UIComponent parent = command.getParent();
        while (parent != null && !(parent instanceof UIForm))
        {
            parent = parent.getParent();
        }

        boolean insideForm;
        String formName;
        DummyFormResponseWriter dummyFormResponseWriter;
        if (parent != null)
        {
            //link is nested inside a form
            UIForm form = (UIForm)parent;
            formName = form.getClientId(facesContext);
            insideForm = true;
            dummyFormResponseWriter = null;
        }
        else
        {
            //not nested in form, we must add a dummy form at the end of the document
            formName = DummyFormUtils.DUMMY_FORM_NAME;
            insideForm = false;
            dummyFormResponseWriter = DummyFormUtils.getDummyFormResponseWriter(facesContext);
            dummyFormResponseWriter.setWriteDummyForm(true);
        }

        StringBuffer onClick = new StringBuffer();

        String commandOnclick;
        if (command instanceof HtmlCommandLink)
        {
            commandOnclick = ((HtmlCommandLink)command).getOnclick();
        }
        else
        {
            commandOnclick = (String)command.getAttributes().get(HTML.ONCLICK_ATTR);
        }
        if (commandOnclick != null)
        {
            onClick.append(commandOnclick);
            onClick.append(';');
        }

        String jsForm = "document.forms['" + formName + "']";

        //add id parameter for decode
        String clientId = command.getClientId(facesContext);
        onClick.append(jsForm);
        onClick.append(".elements['").append(clientId).append("']");
        onClick.append(".value='").append(URL_PARAM_VALUE).append("';");
        if (insideForm)
        {
            renderHiddenParam(writer, clientId);
            //TODO: We must not render duplicate hidden params!
        }
        else
        {
            dummyFormResponseWriter.addDummyFormParameter(clientId);
        }

        //add child parameters
        for (Iterator it = command.getChildren().iterator(); it.hasNext(); )
        {
            UIComponent child = (UIComponent)it.next();
            if (child instanceof UIParameter)
            {
                String name = ((UIParameter)child).getName();
                if (name == null)
                {
                    throw new IllegalArgumentException("Unnamed parameter value not allowed within command link.");
                }
                Object value = ((UIParameter)child).getValue();
                onClick.append(jsForm);
                onClick.append(".elements['").append(name).append("']");
                //UIParameter is no ValueHolder, so no conversion possible
                String strParamValue = value != null ? value.toString() : ""; //TODO: Use Converter?
                onClick.append(".value='").append(strParamValue).append("';");

                if (insideForm)
                {
                    renderHiddenParam(writer, name);
                }
                else
                {
                    dummyFormResponseWriter.addDummyFormParameter(name);
                }
            }
        }

        //submit
        onClick.append(jsForm);
        onClick.append(".submit();");

        writer.startElement(HTML.ANCHOR_ELEM, command);
        writer.writeURIAttribute(HTML.HREF_ATTR, "#", null);
        writer.writeAttribute(HTML.ONCLICK_ATTR, onClick.toString(), null);
    }


    private void renderNonJavaScriptAnchorStart(FacesContext facesContext,
                                                ResponseWriter writer,
                                                UICommand command)
        throws IOException
    {
        ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
        String viewId = facesContext.getViewRoot().getViewId();
        String path = viewHandler.getActionURL(facesContext, viewId);

        StringBuffer hrefBuf = new StringBuffer(path);

        //add clientId parameter for decode
        String clientId = command.getClientId(facesContext);
        if (path.indexOf('?') == -1)
        {
            hrefBuf.append('?');
        }
        else
        {
            hrefBuf.append('&');
        }
        hrefBuf.append(clientId);
        hrefBuf.append('=');
        hrefBuf.append(URL_PARAM_VALUE);

        if (command.getChildCount() > 0)
        {
            addChildParametersToHref(command, hrefBuf,
                                     false, //not the first url parameter
                                     writer.getCharacterEncoding());
        }

        String href = hrefBuf.toString();
        writer.startElement(HTML.ANCHOR_ELEM, command);
        writer.writeURIAttribute(HTML.HREF_ATTR, href, null);
    }

    private void addChildParametersToHref(UIComponent linkComponent,
                                          StringBuffer hrefBuf,
                                          boolean firstParameter,
                                          String charEncoding)
            throws IOException
    {
        for (Iterator it = linkComponent.getChildren().iterator(); it.hasNext(); )
        {
            UIComponent child = (UIComponent)it.next();
            if (child instanceof UIParameter)
            {
                String name = ((UIParameter)child).getName();
                if (name == null)
                {
                    throw new IllegalArgumentException("Unnamed parameter value not allowed within command link.");
                }
                Object value = ((UIParameter)child).getValue();

                hrefBuf.append(firstParameter ? '?' : '&');
                hrefBuf.append(URLEncoder.encode(name, charEncoding));
                hrefBuf.append('=');
                if (value != null)
                {
                    //UIParameter is no ConvertibleValueHolder, so no conversion possible
                    hrefBuf.append(URLEncoder.encode(value.toString(), charEncoding));
                }
            }
        }
    }



    public void renderOutputLinkStart(FacesContext facesContext, UIOutput output)
            throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();

        if (!RendererUtils.isEnabledOnUserRole(facesContext, output))
        {
            RendererUtils.renderChildren(facesContext, output);
            return;
        }

        //calculate href
        String href = RendererUtils.getStringValue(facesContext, output);
        if (output.getChildCount() > 0)
        {
            StringBuffer hrefBuf = new StringBuffer(href);
            addChildParametersToHref(output, hrefBuf,
                                     (href.indexOf('?') == -1), //first url parameter?
                                     writer.getCharacterEncoding());
            href = hrefBuf.toString();
        }
        href = facesContext.getExternalContext().encodeResourceURL(href);    //TODO: or encodeActionURL ?

        //write anchor
        writer.startElement(HTML.ANCHOR_ELEM, output);
        writer.writeURIAttribute(HTML.HREF_ATTR, href, null);
        HTMLUtil.renderHTMLAttributes(writer, output, HTML.ANCHOR_PASSTHROUGH_ATTRIBUTES);
        writer.flush();
    }


    public void encodeChildren(FacesContext facesContext, UIComponent component) throws IOException
    {
        RendererUtils.renderChildren(facesContext, component);
    }

    public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException
    {
        renderLinkEnd(facesContext, component);
    }

    public void renderLinkEnd(FacesContext facesContext, UIComponent component)
            throws IOException
    {
        if (RendererUtils.isEnabledOnUserRole(facesContext, component))
        {
            ResponseWriter writer = facesContext.getResponseWriter();
            writer.endElement(HTML.ANCHOR_ELEM);
        }
    }


    private static void renderHiddenParam(ResponseWriter writer, String paramName)
        throws IOException
    {
        writer.startElement(HTML.INPUT_ELEM, null);
        writer.writeAttribute(HTML.TYPE_ATTR, "hidden", null);
        writer.writeAttribute(HTML.NAME_ATTR, paramName, null);
        writer.endElement(HTML.INPUT_ELEM);
    }


}
