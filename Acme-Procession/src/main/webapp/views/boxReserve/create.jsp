<%--
 * edit.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="boxReserve/viewer/create.do" modelAttribute="boxReserve">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="viewer" />
	<form:hidden path="boxInstance" />
	<form:hidden path="reserveCode" />
	<form:hidden path="createMoment" />

	<acme:textbox code="boxReserve.reserveCode" path="reserveCode" readonly="true"/>
	
	<acme:textarea code="boxReserve.date" path="date" readonly="true"/>
	
	<acme:textarea code="boxReserve.numberOfChair" path="numbersOfchairs" readonly="true"/>
	
	<fieldset>
		<legend>
			<spring:message code="boxReserve.totalCost" />
		</legend>	

		<acme:textarea code="boxReserve.amount" path="totalCost.amount" readonly="true"/>
		
		<acme:textarea code="boxReserve.currency" path="totalCost.currency" readonly="true"/>
		
	</fieldset>
	
	<acme:submit name="save" code="boxReserve.save"/>
	
	<acme:cancel url="boxReserve/viewer/list.do" code="boxReserve.cancel"/>
	
</form:form>