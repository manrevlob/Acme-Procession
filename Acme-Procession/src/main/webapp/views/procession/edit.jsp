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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="procession/bigBrother/edit.do"
	modelAttribute="procession">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="brotherhood" />
	<form:hidden path="registrations" />
	<form:hidden path="stretchOrders" />
	<form:hidden path="isClosedManually" />
	
	<jstl:if test="${procession.image ne null}">
		<form:hidden path="image" />
	</jstl:if>

	<acme:textbox code="procession.brotherhood" path="brotherhood.name" readonly="true"/>

	<acme:textbox code="procession.name" path="name" />

	<acme:textbox code="procession.startMomentWithAdvice" path="startMoment" />

	<acme:textbox code="procession.endMomentWithAdvice" path="endMoment" />

	<acme:textbox code="procession.locality" path="locality" />

	<acme:textarea code="procession.itinerary" path="itinerary" />

	<fieldset>
		<legend>
			<spring:message code="procession.associatedCost" />
		</legend>

		<acme:textbox code="procession.associatedCost.amount"
			path="associatedCost.amount" />

		<acme:textbox code="procession.associatedCost.currency"
			path="associatedCost.currency" />
	</fieldset>

	<acme:textarea code="procession.comments" path="comments" />

	<acme:submit name="save" code="procession.save" />
	
	<jstl:if test="${procession.id != 0}">
		<acme:submit name="delete" code="procession.delete" />
	</jstl:if> 
	
	<acme:cancel url="brotherhood/bigBrother/listOwns.do"
		code="procession.cancel" />

</form:form>