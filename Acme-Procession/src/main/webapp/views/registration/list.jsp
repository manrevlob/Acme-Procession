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

<display:table name="registrations" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">
	
	<spring:message code="registration.nameBrotherhood" var="nameBrotherhoodHeader" />
	<display:column value="${row.procession.brotherhood.name}" title="${nameBrotherhoodHeader}" />
	
	<spring:message code="registration.nameStretch" var="nameStretchHeader" />
	<display:column value="${row.stretch.name}" title="${nameStretchHeader}" />
	
	<spring:message code="registration.startMoment" var="startMomentHeader" />
	<display:column value="${row.procession.startMoment}" title="${startMomentHeader}" format="{0,date,dd/MM/yyyy HH:mm}" />
	
	<spring:message code="registration.endMoment" var="endMomentHeader" />
	<display:column value="${row.procession.endMoment}" title="${endMomentHeader}" format="{0,date,dd/MM/yyyy HH:mm}" />
	
	<spring:message code="registration.moment" var="momentHeader" />
	<display:column value="${row.moment}" title="${momentHeader}" format="{0,date,dd/MM/yyyy HH:mm}" />
	
</display:table>

<div>
	<acme:cancel url="welcome/index.do" code="registration.cancel"/>
</div>