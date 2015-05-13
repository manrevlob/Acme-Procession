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

<form:form action="request/brother/create.do" modelAttribute="request">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="brother" />
	<form:hidden path="code" />
	<form:hidden path="creationMoment" />
	<form:hidden path="status" />

	<acme:textbox code="request.title" path="title"/>
	
	<acme:textarea code="request.description" path="description"/>
	
	<acme:submit name="save" code="request.save"/>
	
	<acme:cancel url="request/brother/list.do" code="request.cancel"/>
	
</form:form>