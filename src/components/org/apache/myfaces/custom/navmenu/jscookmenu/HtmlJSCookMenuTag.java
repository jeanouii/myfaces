package net.sourceforge.myfaces.custom.navmenu.jscookmenu;

import net.sourceforge.myfaces.taglib.UIComponentTagBase;
import net.sourceforge.myfaces.component.UserRoleAware;

import javax.faces.component.UIComponent;

/**
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlJSCookMenuTag
        extends UIComponentTagBase
{
    private static final String COMPONENT_TYPE = "net.sourceforge.myfaces.JSCookMenu".intern();
    private static final String RENDERER_TYPE = "net.sourceforge.myfaces.JSCookMenu".intern();

    private static final String LAYOUT_ATTR = "layout";
    private static final String THEME_ATTR  = "theme";

    private String _layout;
    private String _theme;

    // User Role support
    private String _enabledOnUserRole;
    private String _visibleOnUserRole;

    public String getComponentType()
    {
        return COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return RENDERER_TYPE;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, LAYOUT_ATTR, _layout);
        setStringProperty(component, THEME_ATTR, _theme);

        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
    }

    public void setLayout(String layout)
    {
        _layout = layout;
    }

    public void setTheme(String theme)
    {
        _theme = theme;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }

    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }
}
