<%--
 * pilgrim.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<security:authorize access="hasRole('PILGRIM') or hasRole('INNKEEPER')">
	<display:table name="invoices" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">
	
		<spring:message code="invoice.issuedMoment" var="issuedMomentHeader" />
		<display:column property="issuedMoment" title="${issuedMomentHeader}" format="{0,date,dd/MM/yyyy HH:mm}" />
	
		<spring:message code="invoice.paidMoment" var="paidMomentHeader" />
		<display:column property="paidMoment" title="${paidMomentHeader}" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" />
			
		<spring:message code="invoice.totalCost" var="totalCostHeader" />
		<display:column property="totalCost" title="${totalCostHeader}" />
	
		<spring:message code="invoice.description" var="descriptionHeader" />
		<display:column property="description" title="${descriptionHeader}" />
		
		<security:authorize access="hasRole('PILGRIM')">
			<jstl:if test="${payable == true}">
				<spring:message code="invoice.pay" var="payHeader" />
				<display:column title="${payHeader}">
					<jstl:if test="${row.paidMoment == null}">
						<a href="invoice/pilgrim/pay.do?invoiceId=${row.id}" 
							onclick="return confirm('<spring:message code="invoice.confirm.pay"/>')">
							<spring:message code="invoice.pay" />
						</a>
					</jstl:if>
				</display:column>
			</jstl:if>
		</security:authorize>
	
	</display:table>
</security:authorize>

<security:authorize access="hasRole('ADMINISTRATOR')">

	<display:table name="invoices2" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">
		
		<spring:message code="invoice.issuedMoment" var="issuedMomentHeader" />
		<display:column title="${issuedMomentHeader}" value="${row[0].issuedMoment}" />

		<spring:message code="invoice.description" var="descriptionHeader" />
		<display:column title="${descriptionHeader}" value="${row[0].description}" />

		<spring:message code="invoice.totalCost" var="totalCostHeader" />
		<display:column title="${totalCostHeader}" value="${row[0].totalCost}" />
		
		<spring:message code="invoice.pilgrim.name" var="nameHeader" />
		<display:column title="${nameHeader}" value="${row[1].name}" />
		
	</display:table>

</security:authorize>