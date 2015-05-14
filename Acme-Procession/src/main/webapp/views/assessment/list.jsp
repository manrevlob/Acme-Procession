<%--
 * list.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('VIEWER')">
	<div>
		<a href="assessment/viewer/create.do"> <spring:message
				code="assessment.create" />
		</a>
	</div>
</security:authorize>

<display:table name="assessments" pagesize="5" class="displaytag"
	requestURI="${requestURI}" id="row">

	<security:authorize access="hasRole('VIEWER')">
		<spring:message code="assessment.edit" var="editHeader" />
		<display:column title="${editHeader}">
			<a href="assessment/viewer/edit.do?assessmentId=${row.id}"> [<jstl:out
					value="${editHeader}" />]
			</a>
		</display:column>
	</security:authorize>

	<spring:message code="assessment.valoration" var="valorationHeader" />
	<display:column property="valoration" title="${valorationHeader}"
		sortable="true" />

	<spring:message code="assessment.street" var="streetHeader" />
	<display:column property="street" title="${streetHeader}" />

</display:table>