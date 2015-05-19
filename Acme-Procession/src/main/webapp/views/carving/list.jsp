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

<security:authorize access="hasRole('BROTHER')">
	<div>
		<jstl:if test="${isBigBrother}">
			<a href="carving/brother/create.do"><spring:message
					code="carving.create" /></a>
		</jstl:if>
	</div>
</security:authorize>

<display:table name="carvings" pagesize="5" class="displaytag"
	requestURI="${requestURI}" id="row">

	<security:authorize access="hasRole('BROTHER')">
		<spring:message code="carving.edit" var="editHeader" />
		<display:column title="${editHeader}">
			<jstl:if test="${row.brotherhood.userIsOwner}">
				<a href="carving/brother/edit.do?carvingId=${row.id}"> [<jstl:out
						value="${editHeader}" />]
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<spring:message code="carving.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="carving.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />

	<spring:message code="carving.author" var="authorHeader" />
	<display:column property="author" title="${authorHeader}" />

	<spring:message code="carving.year" var="yearHeader" />
	<display:column property="year" title="${yearHeader}" />

	<spring:message code="carving.comments" var="commentsHeader" />
	<display:column property="comments" title="${commentsHeader}" />

</display:table>