<%@ page import="java.math.BigDecimal,
                 java.util.Date"%>
<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.sourceforge.net/tld/myfaces_ext_0_9.tld" prefix="x"%>
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

                <x:saveState id="save1" value="#{calcForm.number1}" />
                <x:saveState id="save2" value="#{calcForm.number2}" />
                <x:saveState id="save3" value="#{ucaseForm.text}" />

                <x:messages id="messageList" styleClass="error" summaryFormat="{0} in {1}" />

                <f:verbatim>
                    <h4>A Form</h4>
                    <table border="1"><tr><td>
                </f:verbatim>

                <h:form id="form1" name="calcForm">
                    <h:outputLabel for="form1:number1" value="Number 1" />
                    <h:outputText value="#{validationController.number1ValidationLabel}"/>
                    <f:verbatim>: </f:verbatim>
                    <h:inputText id="number1" value="#{calcForm.number1}" maxlength="10" size="25" required="true" >
                       <f:validateLongRange minimum="1" maximum="10" />
                    </h:inputText>
                    <h:message id="number1Error" for="form1:number1" styleClass="error" /><f:verbatim><br></f:verbatim>

                    <h:outputLabel for="form1:number2" value="Number 2" />
                    <h:outputText value="#{validationController.number2ValidationLabel}"/>
                    <f:verbatim>: </f:verbatim>
                    <h:inputText id="number2" value="#{calcForm.number2}" maxlength="10" size="25" required="true" >
                       <f:validateLongRange minimum="20" maximum="50" />
                    </h:inputText>
                    <h:message id="number2Error" for="form1:number2" styleClass="error" /><f:verbatim><br></f:verbatim>

                    <h:outputLabel for="form1:result" value="Result" /><f:verbatim>: </f:verbatim>
                    <h:outputText id="result" value="#{calcForm.result}" /><f:verbatim><br></f:verbatim>

                    <h:commandButton id="addButton" value="Add them" action="none">
                        <f:actionListener type="net.sourceforge.myfaces.examples.example1.CalcActionListener" ></f:actionListener>
                    </h:commandButton>
                    <h:commandButton id="subtractButton" value="Subtract them" action="none">
                        <f:actionListener type="net.sourceforge.myfaces.examples.example1.CalcActionListener" ></f:actionListener>
                    </h:commandButton>
                    <f:verbatim><br></f:verbatim>

                    <h:commandLink id="href1" action="none"><f:verbatim>Add them by clicking this link</f:verbatim>
                        <f:actionListener type="net.sourceforge.myfaces.examples.example1.CalcActionListener" ></f:actionListener>
                    </h:commandLink><f:verbatim><br></f:verbatim>
                    <h:commandLink id="href2" action="none"><f:verbatim>Subtract them by clicking this link</f:verbatim>
                        <f:actionListener type="net.sourceforge.myfaces.examples.example1.CalcActionListener" ></f:actionListener>
                    </h:commandLink>
                </h:form>

                <f:verbatim>
                    </td></tr></table>
                    <h4>Another Form</h4>
                    <table border="1"><tr><td>
                </f:verbatim>

                <h:form id="form2" name="ucaseForm">
                    <h:outputLabel for="form2:text" value="Text" />
                    <h:outputText value="#{validationController.textValidationLabel}"/>
                    <f:verbatim>: </f:verbatim>
                    <h:inputText id="text" value="#{ucaseForm.text}">
                        <f:validateLength minimum="3" maximum="7"/>
                    </h:inputText>
                    <h:message id="textError" for="form2:text" styleClass="error" /><f:verbatim><br></f:verbatim>
                    <h:commandButton id="ucaseButton" value="Make it uppercase" action="none">
                        <f:actionListener type="net.sourceforge.myfaces.examples.example1.UCaseActionListener" />
                    </h:commandButton>
                    <h:commandButton id="lcaseButton" value="Make it lowercase" action="none">
                        <f:actionListener type="net.sourceforge.myfaces.examples.example1.UCaseActionListener" />
                    </h:commandButton>
                    <f:verbatim><br></f:verbatim>
                </h:form>

                <f:verbatim>
                    </td></tr></table>

                    <h4>Validation</h4>
                    <table border="1"><tr><td>
                </f:verbatim>

                <h:form id="form3" name="valForm">
                    <h:commandButton id="valDisable" value="Disable validation" action="#{validationController.disableValidation}" />
                    <h:commandButton id="valEnable" value="Enable validation" action="#{validationController.enableValidation}" />
                </h:form>

                <f:verbatim>
                    </td></tr></table>
                </f:verbatim>

        <f:verbatim><br></f:verbatim>
        <h:commandLink id="jump_home" action="#{ucaseForm.jumpHome}" ><f:verbatim>Go Home</f:verbatim></h:commandLink>

            </h:panelGroup>
        </f:facet>

        <%@include file="inc/page_footer.jsp" %>

    </x:panelLayout>

</f:view>

</body>

</html>