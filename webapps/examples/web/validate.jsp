<%@ page import="java.math.BigDecimal,
                 java.util.Date"%>
<%@ page session="false"
%><%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"
%><%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"
%><%@ taglib uri="http://myfaces.sourceforge.net/tld/myfaces_ext_0_9.tld" prefix="x"
%><html>

<%@include file="inc/head.inc" %>

<!--
/**
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
//-->

<body>

<!--
managed beans used:
    validateForm
-->

<f:view>

    <f:loadBundle basename="net.sourceforge.myfaces.examples.resource.example_messages" var="example_messages"/>

    <x:panelLayout id="page" layout="#{globalOptions.pageLayout}"
            styleClass="pageLayout"
            headerClass="pageHeader"
            navigationClass="pageNavigation"
            bodyClass="pageBody"
            footerClass="pageFooter" >

        <f:facet name="header">
            <f:subview id="header">
                <jsp:include page="inc/page_header.jsp" />
            </f:subview>
        </f:facet>

        <f:facet name="navigation">
            <f:subview id="menu" >
                <jsp:include page="inc/navigation.jsp" />
            </f:subview>
        </f:facet>


        <f:facet name="body">
            <h:panelGroup id="body">

			<h:form id="form1" name="validateForm">
			   <h:panelGrid columns="3">
			
					<h:outputLabel for="form1:email" value="Email" />
                    <h:inputText id="email" value="#{validateForm.email}" required="true">
                        <f:validator validatorId="net.sourceforge.myfaces.validator.Email"/>
                    </h:inputText>
					<h:message id="emailError" for="form1:email" styleClass="error" />
					
					<h:outputLabel for="form1:email2" value="Email2" />
                    <h:inputText id="email2" value="#{validateForm.email2}" required="true">
                        <x:validateEmail />
                    </h:inputText>
					<h:message id="emailError2" for="form1:email2" styleClass="error" />
					
					<h:outputLabel for="form1:creditCardNumber" value="CreditCard" />
                    <h:inputText id="creditCardNumber" value="#{validateForm.creditCardNumber}" required="true">
                        <x:validateCreditCard />
                    </h:inputText>
					<h:message id="creditCardNumberError" for="form1:creditCardNumber" styleClass="error" />

					<h:outputLabel for="form1:regExprValue" value="Regular Expressions" />
                    <h:inputText id="regExprValue" value="#{validateForm.regExpr}" required="true">
                        <x:validateRegExpr pattern='\d{5}' />
                    </h:inputText>
					<h:message id="regExprValueError" for="form1:regExprValue" styleClass="error" />

					<h:outputLabel for="form1:equal" value="Equal" />
                    <h:inputText id="equal" value="#{validateForm.equal}" required="true"/>
					<h:message id="equalError" for="form1:equal" styleClass="error" />

					<h:outputLabel for="form1:equal2" value="Equal2" />
                    <h:inputText id="equal2" value="#{validateForm.equal2}" required="true">
                        <x:validateEqual for="form1:equal" />
                    </h:inputText>
					<h:message id="equal2Error" for="form1:equal2" styleClass="error" />

                    <h:panelGroup/>
				    <h:commandButton id="validateButton" value="Submit" action="#{validateForm.submit}"/>
                    <h:panelGroup/>
                    
			    </h:panelGrid>
			</h:form>
			
            </h:panelGroup>
        </f:facet>

        <%@include file="inc/page_footer.jsp" %>

    </x:panelLayout>

</f:view>

</body>

</html>