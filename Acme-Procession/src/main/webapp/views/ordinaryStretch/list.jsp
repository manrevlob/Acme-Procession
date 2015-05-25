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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('BROTHER')">
	<div>
		<a href="ordinaryStretch/bigBrother/create.do?brotherhoodId=${param.brotherhoodId}">
			<spring:message	code="oStretch.create" />
		</a>
	</div>
</security:authorize>

<display:table name="ordinaryStretches" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">
	
	<security:authorize  access="hasRole('BROTHER')">
		<spring:message code="stretch.edit" var="editHeader" />
		<display:column title="${editHeader}">
			<a href="ordinaryStretch/bigBrother/edit.do?ordinaryStretchId=${row.id}">
				[<jstl:out value="${editHeader}"/>]
			</a>
		</display:column>
	</security:authorize>

	<spring:message code="stretch.brotherhood" var="brotherhoodHeader" />
	<display:column property="brotherhood.name" title="${brotherhoodHeader}" sortable="true" />

	<spring:message code="stretch.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="stretch.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />

	<spring:message code="stretch.maxNumberOfBrothers" var="maxNumberOfBrothersHeader" />
	<display:column property="maxNumberOfBrothers" title="${maxNumberOfBrothersHeader}" />

</display:table>

<div>
	<acme:cancel url="welcome/index.do" code="stretch.cancel"/>
</div>