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

<form:form action="floatStretch/brother/edit.do" modelAttribute="floatStretch">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<jstl:choose>
		<jstl:when test="${floatStretch.id != 0}">
			<form:hidden path="brotherhood" />
			<spring:message code="stretch.brotherhood"/>: 
			<jstl:out value="${floatStretch.brotherhood.name}"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:select items="${brotherhoods}" itemLabel="name" code="stretch.brotherhood" path="brotherhood"/>
		</jstl:otherwise>
	</jstl:choose>

	<acme:textbox code="stretch.name" path="name" />

	<acme:textbox code="stretch.description" path="description" />

	<acme:submit name="save" code="stretch.save" /> 
	
	<jstl:if test="${floatStretch.id != 0}">
		<acme:submit name="delete" code="stretch.delete" />
	</jstl:if> 
	
	<acme:cancel url="floatStretch/brother/listOwns.do"
		code="stretch.cancel" />

</form:form>