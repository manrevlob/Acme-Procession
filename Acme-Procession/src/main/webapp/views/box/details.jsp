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
	
	<b><spring:message code="box.name" />:</b>
	<jstl:out value="${box.name}" />
	<br />
 	
 	<b><spring:message code="box.description" />:</b>
 	<jstl:out value="${box.description}" />
 	<br />
 	
	<b><spring:message code="box.locality" />:</b>
	<jstl:out value="${box.locality}" />
	<br />

	<b><spring:message code="box.location" />:</b>
	<jstl:out value="${box.location}" />
	<br />
	
	<b><spring:message code="box.numberOfChairs" />:</b>
	<jstl:out value="${box.numberOfChairs}" />
	<br />
	
</div>

<security:authorize  access="hasRole('ADMINISTRATOR')">

	<div>
		<acme:cancel url="box/administrator/list.do" code="box.cancel"/>
	</div>
	
</security:authorize>

<security:authorize  access="hasRole('VIEWER')">

	<div>
		<acme:cancel url="box/viewer/list.do" code="box.cancel"/>
	</div>
	
</security:authorize>