<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/extensions" prefix="x"%>
<html>

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

<%@include file="inc/head.inc" %>

<body>

<f:view>

    <f:loadBundle basename="org.apache.myfaces.examples.resource.example_messages" var="example_messages"/>

    <h:outputText value="#{example_messages['dataList_simple']}" styleClass="standard_bold" />

    <br/>

    <x:dataList id="data1"
        styleClass="standardList"
        var="country"
        value="#{countryList.countries}"
        layout="simple"
        rowCountVar="rowCount"
        rowIndexVar="rowIndex" >
        <h:outputText value="#{country.name}" />
        <h:outputText value=", " rendered="#{rowIndex + 1 < rowCount}" />
    </x:dataList>

    <br/><br/>

    <h:outputText value="#{example_messages['dataList_ul']}" styleClass="standard_bold" />
    <x:dataList id="data2"
        styleClass="standardList"
        var="country"
        value="#{countryList.countries}"
        layout="unorderedList">
        <h:outputText value="#{country.name}" />
    </x:dataList>

    <br/>
    <h:outputText value="#{example_messages['dataList_ol']}" styleClass="standard_bold" />
    <x:dataList id="data3"
        styleClass="standardList"
        var="country"
        value="#{countryList.countries}"
        layout="orderedList">
        <h:outputText value="#{country.name}" />
    </x:dataList>

</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>
