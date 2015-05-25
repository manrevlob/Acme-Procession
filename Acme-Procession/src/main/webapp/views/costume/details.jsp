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

	<b><spring:message code="costume.size" />:</b>
	<jstl:out value="${costume.size}" />
	<br />
	
	<b><spring:message code="costume.status" />:</b>
	<jstl:choose>
		<jstl:when test="${costume.status == 'new'}">
			<spring:message code="costume.new"/>
		</jstl:when>
		<jstl:when test="${costume.status == 'used'}">
			<spring:message code="costume.used"/>
		</jstl:when>
		<jstl:when test="${costume.status == 'old'}">
			<spring:message code="costume.old"/>
		</jstl:when>
		<jstl:otherwise>
			ERROR
		</jstl:otherwise>
	</jstl:choose>
	<br />
 	
 	<b><spring:message code="costume.salePrice" />:</b>
 	<jstl:out value="${costume.salePrice}"/>
 	<br />
 	
	<b><spring:message code="costume.rentalPrice" />:</b>
	<jstl:out value="${costume.rentalPrice}" />
	<br />
	
	<b><spring:message code="costume.comments" />:</b>
	<jstl:choose>
		<jstl:when test="${costume.comments ne ''}">
			<jstl:out value="${costume.comments}" />
		</jstl:when>
		<jstl:otherwise>
			-
		</jstl:otherwise>
	</jstl:choose>
	
</div>

<div>
	<input type="button" onclick="javascript:history.back();" value="<spring:message code='costume.cancel' />" />
</div>