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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<security:authorize access="hasRole('BROTHER')">

		<jstl:choose>
			<jstl:when test="${registration != null}">
				<b><spring:message code="invoice.brotherhood" />:</b>
				<jstl:out value="${registration.procession.brotherhood.name}" />
				<br />

				<b><spring:message code="invoice.processionName" />:</b>
				<jstl:out value="${registration.procession.name}" />
				<br />
			</jstl:when>
			<jstl:when test="${costumeReserve != null}">
				<b><spring:message code="invoice.brotherhood" />:</b>
				<jstl:out value="${costumeReserve.costume.brotherhood.name}" />
				<br />
				
				<b><spring:message code="invoice.type" />:</b>
				<jstl:out value="${costumeReserve.type}" />
				<br />
				
			</jstl:when>
		</jstl:choose>

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
	<fmt:formatDate value="${invoice.createMoment}"
		pattern="dd-MM-yyyy HH:mm" />

	<br /> <b><spring:message code="invoice.paidMoment" />:</b>
	<fmt:formatDate value="${invoice.paidMoment}"
		pattern="dd-MM-yyyy HH:mm" />

	<br /> <b><spring:message code="invoice.totalCost" />:</b>
	<jstl:out value="${invoice.totalCost}" />
	<br />

</div>

<div>
	<security:authorize access="hasRole('BROTHER')">
		<br />
		<input type="button" onclick="javascript:history.back();"
			value="<spring:message code='invoice.cancel' />" />
		<jstl:if test="${invoice.paidMoment == null}">
			<jstl:choose>
				<jstl:when test="${registration != null}">
					<input type="button"
						onclick="location.href='./registrationInvoice/brother/pay.do?registrationInvoiceId=${invoice.id}'"
						value="<spring:message code='invoice.pay' />">
				</jstl:when>
				<jstl:when test="${costumeReserve != null}">
					<input type="button"
						onclick="location.href='./costumeInvoice/brother/pay.do?costumeInvoiceId=${invoice.id}'"
						value="<spring:message code='invoice.pay' />">
				</jstl:when>
			</jstl:choose>
		</jstl:if>
		<br />
	</security:authorize>

	<security:authorize access="hasRole('VIEWER')">
		<br />
		<input type="button" onclick="javascript:history.back();"
			value="<spring:message code='invoice.cancel' />" />
		<jstl:if test="${invoice.paidMoment == null}">
			<input type="button"
				onclick="location.href='./boxInvoice/viewer/pay.do?boxInvoiceId=${invoice.id}'"
				value="<spring:message code='invoice.pay' />">
		</jstl:if>
		<br />
	</security:authorize>
</div>