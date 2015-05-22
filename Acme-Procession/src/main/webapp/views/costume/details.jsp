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
	<fmt:formatDate value="${costume.status}"/>
	<br />
 	
 	<b><spring:message code="costume.salePrice" />:</b>
 	<fmt:formatDate value="${costume.salePrice}"/>
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
	<acme:cancel url="brotherhood/brother/listOwns.do" code="costume.cancel"/>
</div>