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

<form:form action="assessment/viewer/assessById.do" modelAttribute="assessment">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="procession" />
	<form:hidden path="viewer" />
	
	<acme:textbox code="assessment.valoration" path="valoration"/>
	<acme:textbox code="assessment.street" path="street"/>

	<acme:submit name="save" code="assessment.save"/>
	
	<acme:cancel url="assessment/viewer/list.do" code="assessment.cancel"/>
	
</form:form>