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
	<form:hidden path="procession" />

	<acme:textbox code="stretch.name" path="name" />

	<acme:textbox code="stretch.description" path="description" />

	<acme:submit name="save" code="procession.save" />
	
	<jstl:if test="${procession.id != 0}">
		<acme:submit name="delete" code="procession.delete" />
	</jstl:if>

</form:form>

<div>
	<acme:cancel url="brotherhood/brother/listOwns.do"
		code="stretch.cancel" />
</div>