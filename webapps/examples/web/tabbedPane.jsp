<%@ page import="java.math.BigDecimal,
                 java.util.Date"%>
<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/extensions" prefix="x"%>
<html>

<%@include file="inc/head.inc" %>

<!--
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
//-->

<body>

<!--
managed beans used:
    calcForm
    ucaseForm
-->

<f:view>

    <x:saveState id="ss1" value="#{tabbedPaneBean}" />

    <f:loadBundle basename="org.apache.myfaces.examples.resource.example_messages" var="example_messages"/>

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

                <x:messages id="messageList" showSummary="true" showDetail="true" summaryFormat="{0}:" />

                <x:panelTabbedPane bgcolor="#FFFFCC" >

                    <f:verbatim>
                        <p><h:outputText value="#{example_messages['tabbed_common']}"/></p>
                    </f:verbatim>

                    <x:panelTab id="tab1" label="#{example_messages['tabbed_tab1']}" rendered="#{tabbedPaneBean.tab1Visible}">
                        <h:inputText id="inp1"/><f:verbatim><br></f:verbatim>
                        <h:inputText id="inp2" required="true" /><h:message for="inp2" showSummary="false" showDetail="true" />
                    </x:panelTab>

                    <f:subview id="tab2" >
                        <jsp:include page="tab2.jsp"/>
                    </f:subview>

                    <x:panelTab id="tab3" label="#{example_messages['tabbed_tab3']}" rendered="#{tabbedPaneBean.tab3Visible}">
                        <h:inputText id="inp3"/><f:verbatim><br></f:verbatim>
                        <h:inputText id="inp4"/><f:verbatim><br></f:verbatim>
                        <h:inputText id="inp5"/><f:verbatim><br></f:verbatim>
                    </x:panelTab>

                    <f:verbatim><br></f:verbatim>

                    <h:selectBooleanCheckbox value="#{tabbedPaneBean.tab1Visible}"/><h:outputText value="#{example_messages['tabbed_visible1']}"/><br>
                    <h:selectBooleanCheckbox value="#{tabbedPaneBean.tab2Visible}"/><h:outputText value="#{example_messages['tabbed_visible2']}" /><br>
                    <h:selectBooleanCheckbox value="#{tabbedPaneBean.tab3Visible}"/><h:outputText value="#{example_messages['tabbed_visible3']}" /><br>

                    <h:commandButton value="#{example_messages['tabbed_submit']}" />

                </x:panelTabbedPane>

            </h:panelGroup>
        </f:facet>

        <%@include file="inc/page_footer.jsp" %>

    </x:panelLayout>

</f:view>

</body>

</html>