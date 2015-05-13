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

<security:authorize access="hasRole('BROTHER')">
	<jstl:if test="${status == 'rejected' || status == null}">

		<div>
			<a href="request/brother/create.do"> <spring:message
					code="request.create" />
			</a>
		</div>
	</jstl:if>
</security:authorize>

<display:table name="requests" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">

	<spring:message code="request.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="request.creationMoment" var="creationMomentHeader" />
	<display:column property="creationMoment" title="${creationMomentHeader}" />

	<spring:message code="request.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />
	
	<spring:message code="request.comments" var="commentsHeader" />
	<display:column property="comments" title="${commentsHeader}" />

	<security:authorize access="hasRole('BROTHER')">
		<spring:message code="request.status" var="statusHeader" />
		<display:column property="status" title="${statusHeader}" />
	</security:authorize>

	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column>
			<a href="request/administrator/accept.do?requestId=${row.id}">
				<spring:message code="request.accepted" />
			</a>
		</display:column>

		<display:column>
			<a href="request/administrator/reject.do?requestId=${row.id}">
				<spring:message code="request.rejected" />
			</a>
		</display:column>
	</security:authorize>

</display:table>


