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

<form:form action="carving/brother/edit.do" modelAttribute="carving">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="brotherhood" />
	
	<acme:textbox code="carving.brotherhood" path="brotherhood.name" readonly="true" />

	<acme:textbox code="carving.name" path="name"/>

	<acme:textbox code="carving.description" path="description"/>

	<acme:textbox code="carving.author" path="author"/>

	<acme:textbox code="carving.year" path="year"/>

	<acme:textbox code="carving.comments" path="comments"/>
	
	<acme:submit name="save" code="carving.save"/>
	
	<acme:cancel url="carving/list.do" code="carving.cancel"/>
	
</form:form>