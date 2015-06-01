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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="floatStretch/bigBrother/edit.do" modelAttribute="floatStretch">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="brotherhood" />
	
	<acme:textbox code="stretch.brotherhood" path="brotherhood.name" readonly="true" disabled="true" />

	<acme:textbox code="stretch.name" path="name" />

	<acme:textbox code="stretch.description" path="description" />
	
	<spring:message code="stretch.carvings" />
	
	<jstl:choose>
		<jstl:when test="${fn:length(carvings) ne 0}">
			<form:select multiple="true" path="carvings">
			    <form:options items="${carvings}" itemValue="id" itemLabel="name"/>
			</form:select>
			
			<form:errors path="carvings" cssClass="error" />
		</jstl:when>
		<jstl:otherwise>
			<span class="error"><spring:message code="stretch.noCarvings.error" /></span>
		</jstl:otherwise>
	</jstl:choose>
	
	<br/>

	<acme:submit name="save" code="stretch.save" /> 
	
	<jstl:if test="${floatStretch.id != 0}">
		<acme:submit name="delete" code="stretch.delete" />
	</jstl:if> 
	
	<acme:cancel url="brotherhood/bigBrother/listOwns.do"
		code="stretch.cancel" />

</form:form>