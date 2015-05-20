<%--
 * list.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="boxInstances" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">

	<spring:message code="boxInstance.edit" var="editHeader" />
	<display:column title="${editHeader}">
		<a href="boxInstance/administrator/edit.do?boxInstanceId=${row.id}">
			[<jstl:out value="${editHeader}"/>]
		</a>
	</display:column>
		
	<spring:message code="boxInstance.date" var="dateHeader" />
	<display:column property="date" title="${dateHeader}" sortable="true" />
	
		<spring:message code="boxInstance.amount" var="amountHeader" />
	<display:column property="price.amount" title="${amountHeader}" />

	<spring:message code="boxInstance.currency" var="currencyHeader" />
	<display:column property="price.currency" title="${currencyHeader}" />

</display:table>

<div>
	<acme:cancel url="welcome/index.do" code="boxInstance.cancel"/>
</div>