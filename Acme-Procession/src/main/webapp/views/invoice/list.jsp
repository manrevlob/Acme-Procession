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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="registrationInvoices" pagesize="5" class="displaytag"
	requestURI="${requestURI}" id="row">

	<spring:message code="invoice.createMoment" var="createMomentHeader" />
	<display:column property="createMoment" title="${createMomentHeader}" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" />

	<spring:message code="invoice.paidMoment" var="paidMomentHeader" />
	<display:column property="paidMoment" title="${paidMomentHeader}" format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="invoice.totalCost" var="totalCostHeader" />
	<display:column property="totalCost" title="${totalCostHeader}" />


	<security:authorize access="hasRole('BROTHER')">
		<display:column>
			<a href="registrationInvoice/brother/details.do?registrationInvoiceId=${row.id}">
				<jstl:choose>
					<jstl:when test="${row.paidMoment == null}">
						<spring:message	code="invoice.detailsPay" />
					</jstl:when>
					<jstl:otherwise>
						<spring:message	code="invoice.details" />
					</jstl:otherwise>
				</jstl:choose>
			</a>
		</display:column>
	</security:authorize>

</display:table>


