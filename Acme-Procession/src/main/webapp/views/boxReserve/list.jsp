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


<display:table name="boxReserves" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">

	<security:authorize  access="hasRole('VIEWER')">
	
		<spring:message code="boxReserve.edit" var="editHeader" />
		<display:column title="${editHeader}">
			<a href="boxReserve/viewer/edit.do?boxInstanceId=${row.id}">
				[<jstl:out value="${editHeader}"/>]
			</a>
		</display:column>
		
	</security:authorize>
		
	<spring:message code="boxReserve.createMoment" var="createMomentHeader" />
	<display:column property="createMoment" title="${createMomentHeader}" sortable="true" />
	
	<spring:message code="boxReserve.reserveCode" var="reserveCodeHeader" />
	<display:column property="reserveCode" title="${reserveCodeHeader}"  />
	
	<spring:message code="boxReserve.numberOfChair" var="numberOfChairHeader" />
	<display:column property="numberOfChair" title="${numberOfChairHeader}"  />
	
	<spring:message code="boxReserve.date" var="dateHeader" />
	<display:column property="date" title="${dateHeader}"  />
	
	<spring:message code="boxReserve.amount" var="amountHeader" />
	<display:column property="price.amount" title="${amountHeader}" />

	<spring:message code="boxReserve.currency" var="currencyHeader" />
	<display:column property="price.currency" title="${currencyHeader}" />

</display:table>

<div>
	<acme:cancel url="welcome/index.do" code="boxReserve.cancel"/>
</div>