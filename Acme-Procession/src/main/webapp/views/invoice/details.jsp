<%--
 * details&Pay.jsp
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
	<security:authorize access="hasRole('BROTHER')">
	
		<b><spring:message code="invoice.brotherhood" />:</b>
		<jstl:out value="${registration.procession.brotherhood.name}" />
		<br />
	 	
	 	<b><spring:message code="invoice.processionName" />:</b>
		<jstl:out value="${registration.procession.name}" />
		<br />
		
	</security:authorize>
	
	<security:authorize access="hasRole('VIEWER')">
		<b><spring:message code="invoice.box" />:</b>
		<jstl:out value="${boxReserve.boxInstance.box.name}" />
		<br />
		
		<b><spring:message code="invoice.boxReserveCode" />:</b>
		<jstl:out value="${boxReserve.reserveCode}" />
		<br />
	</security:authorize>
 	
	<b><spring:message code="invoice.createMoment" />:</b>
	<jstl:out value="${registrationInvoice.createMoment}" />
	<br />
 	
 	<b><spring:message code="invoice.paidMoment" />:</b>
 	<jstl:out value="${registrationInvoice.paidMoment}" />
 	<br />
 	
	<b><spring:message code="invoice.totalCost" />:</b>
	<jstl:out value="${registrationInvoice.totalCost}" />
	<br />
	
</div>

<div>
	<security:authorize access="hasRole('BROTHER')">
	<br />
	<input type="button" onclick="javascript:history.back();" value="<spring:message code='invoice.cancel' />" />
	<jstl:if test="${registrationInvoice.paidMoment == null}">
		<input type="button" onclick="location.href='./registrationInvoice/brother/pay.do?registrationInvoiceId=${registrationInvoice.id}'" value="<spring:message code='invoice.pay' />" >
	</jstl:if>
	<br />
	</security:authorize>
	
	<security:authorize access="hasRole('VIEWER')">
	<br />
	<input type="button" onclick="javascript:history.back();" value="<spring:message code='invoice.cancel' />" />
	<jstl:if test="${registrationInvoice.paidMoment == null}">
		<input type="button" onclick="location.href='./boxInvoice/viewer/pay.do?boxInvoiceId=${registrationInvoice.id}'" value="<spring:message code='invoice.pay' />" >
	</jstl:if>
	<br />
	</security:authorize>
</div>