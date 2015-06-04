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

<jstl:if test="${param.error ne null}">
	<span class="error">
		<spring:message code="${param.error}"/>
	</span>
</jstl:if>

<display:table name="boxReserves" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">
	
	<security:authorize  access="hasRole('VIEWER')">
		
		<spring:message code="boxReserve.cancelReserve" var="cancelReserveHeader" />
		<display:column title="${cancelReserveHeader}">
			<jstl:if test="${row.isCancelled==false}">
					<a href="boxReserve/viewer/cancel.do?boxReserveId=${row.id}" onclick="return confirm('<spring:message code="boxReserve.confirm.cancel"/>')">
					[<jstl:out value="${cancelReserveHeader}"/>]
				</a>
			</jstl:if>
		</display:column>
		
	</security:authorize>	
	
	<spring:message code="boxReserve.createMoment" var="createMomentHeader"  />
	<display:column property="createMoment" title="${createMomentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"/>
	
	<spring:message code="boxReserve.reserveCode" var="reserveCodeHeader" />
	<display:column property="reserveCode" title="${reserveCodeHeader}"  />
	
	<spring:message code="boxReserve.numberOfChair" var="numbersOfchairsHeader" />
	<display:column property="numbersOfchairs" title="${numbersOfchairsHeader}"  />
	
	<spring:message code="boxReserve.date" var="dateHeader"  />
	<display:column property="date" title="${dateHeader}" sortable="true" format="{0,date,dd/MM/yyyy}" />
	
	<spring:message code="boxReserve.amount" var="amountHeader" />
	<display:column property="totalCost.amount" title="${amountHeader}" />

	<spring:message code="boxReserve.currency" var="currencyHeader" />
	<display:column property="totalCost.currency" title="${currencyHeader}" />
	
	<display:column>
			<a href="boxReserve/viewer/details.do?boxReserveId=${row.id}">
				<spring:message	code="boxReserve.details" />
			</a>
		</display:column>

</display:table>

<div>
	<acme:cancel url="welcome/index.do" code="boxReserve.cancel"/>
</div>