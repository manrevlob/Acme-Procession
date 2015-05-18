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
	
	<b><spring:message code="procession.startMoment" />:</b>
	<fmt:formatDate value="${procession.startMoment}" pattern="dd-MM-yyyy HH:mm"/>
	<br />
 	
 	<b><spring:message code="procession.endMoment" />:</b>
 	<fmt:formatDate value="${procession.endMoment}" pattern="dd-MM-yyyy HH:mm"/>
 	<br />
 	
	<b><spring:message code="procession.locality" />:</b>
	<jstl:out value="${procession.locality}" />
	<br />

	<b><spring:message code="procession.itinerary" />:</b>
	<jstl:out value="${procession.itinerary}" />
	<br />
	
	<b><spring:message code="procession.associatedCost" />:</b>
	<jstl:out value="${procession.associatedCost}" />
	<br />
	
	<b><spring:message code="procession.comments" />:</b>
	<jstl:choose>
		<jstl:when test="${procession.comments ne ''}">
			<jstl:out value="${procession.comments}" />
		</jstl:when>
		<jstl:otherwise>
			-
		</jstl:otherwise>
	</jstl:choose>
	<br />
	
	<b><spring:message code="procession.isClosed" />:</b>
	<jstl:out value="${procession.isClosed}" />
	<br />
	
</div>

<div>
	<acme:cancel url="brotherhood/list.do" code="brotherhood.cancel"/>
</div>