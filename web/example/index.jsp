<%@ page import="java.math.BigDecimal,
                 java.util.Date"%>
<%@ page session="false"
%><%@ taglib uri="/WEB-INF/myfaces_basic.tld" prefix="f"
%><%@ taglib uri="/WEB-INF/myfaces_ext.tld" prefix="x"
%><html>

<!--
/**
 * MyFaces - the free JSF implementation
 * Copyright (C) 2003  The MyFaces Team (http://myfaces.sourceforge.net)
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
//-->

<body>

<f:use_faces>

    <table border="1"><tr>
        <td valign="top" width="140"><%@ include file="inc/navigation.jsp"  %></td>
        <td align="center" width="640">

        <table border="0">
            <tr>
                <td valign="middle">
                    <f:message id="welcome" bundle="net.sourceforge.myfaces.example.example_messages" key="welcome" />
                </td>
                <td valign="middle">
                    <f:image id="logo" url="images/logo.jpg"/>
                </td>
            </tr>
        </table>

        <f:message id="today" bundle="net.sourceforge.myfaces.example.example_messages" key="today" >
            <f:param id="p0" value="<%=new Date()%>" />
        </f:message>

        </td>
    </tr></table>

</f:use_faces>

</body>

</html>