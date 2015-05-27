<%--
details.jsp
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

<div>
	
	<b><spring:message code="boxReserve.reserveCode" />:</b>
	<jstl:out value="${boxReserve.reserveCode}" />
	<br />
 	
 	<b><spring:message code="boxReserve.date" />:</b>
 	<jstl:out value="${boxReserve.date}" />
 	<br />
 	
	<b><spring:message code="boxReserve.numberOfChair" />:</b>
	<jstl:out value="${boxReserve.numbersOfchairs}" />
	<br />
	
	<b><spring:message code="boxReserve.createMoment" />:</b>
	<jstl:out value="${boxReserve.createMoment}" />
	<br />
	
	<b><spring:message code="boxReserve.isCancelled" />:</b>
		<jstl:if test="${boxReserve.isCancelled==true}">
			<spring:message code="boxReserve.true" />
		</jstl:if>
		<jstl:if test="${boxReserve.isCancelled==false}">
			<spring:message code="boxReserve.false" />
		</jstl:if>
	<br />
	
	<fieldset>
	<legend><spring:message code="boxReserve.totalCost" /></legend>
	
		<b><spring:message code="boxReserve.amount" />:</b>
		<jstl:out value="${boxReserve.totalCost.amount}" />
		<br />
		
		<b><spring:message code="boxReserve.currency" />:</b>
		<jstl:out value="${boxReserve.totalCost.currency}" />
		<br />
	
	</fieldset>
	
</div>

<security:authorize  access="hasRole('ADMINISTRATOR')">

	<div>
		<acme:cancel url="box/administrator/list.do" code="box.cancel"/>
	</div>
	
</security:authorize>

<security:authorize  access="hasRole('VIEWER')">

	<div>
		<acme:cancel url="box/viewer/list.do" code="box.cancel"/>
	</div>
	
</security:authorize>