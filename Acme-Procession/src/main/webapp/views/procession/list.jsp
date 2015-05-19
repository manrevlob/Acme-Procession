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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('BROTHER')">
	<jstl:if test="${isBigBrother}">
		<div>
			<a href="procession/brother/create.do"> <spring:message
					code="procession.create" />
			</a>
		</div>
	</jstl:if>
</security:authorize>

<display:table name="processions" pagesize="5" class="displaytag"
	requestURI="${requestURI}" id="row">

	<security:authorize access="hasRole('BROTHER')">
		<spring:message code="procession.edit" var="editHeader" />
		<display:column>
			<jstl:if test="${row.brotherhood.userIsOwner}">
				<a href="procession/brother/edit.do?processionId=${row.id}"> [<jstl:out
						value="${editHeader}" />]
				</a>
			</jstl:if>
		</display:column>
		
		<spring:message code="procession.closeOpen" var="closeOpenHeader" />
		<display:column>
			<jstl:if test="${row.brotherhood.userIsOwner}">
				<jstl:choose>
					<jstl:when test="${row.isClosedByTime}">
						-
					</jstl:when>
					<jstl:otherwise>
						<jstl:choose>
							<jstl:when test="${row.isClosedManually != true}">
								<spring:message code="procession.close" var="closeHeader" />
								<a href="procession/brother/close.do?processionId=${row.id}">
									[<jstl:out value="${closeHeader}" />]
								</a>
							</jstl:when>
	
							<jstl:when test="${row.isClosedManually == true}">
								<spring:message code="procession.open" var="openHeader" />
								<a href="procession/brother/open.do?processionId=${row.id}">
									[<jstl:out value="${openHeader}" />]
								</a>
							</jstl:when>
						</jstl:choose>
					</jstl:otherwise>
				</jstl:choose>
			</jstl:if>
		</display:column>
	</security:authorize>

	<spring:message code="procession.isClosed" var="isClosedHeader" />
	<display:column property="isClosed" title="${isClosedHeader}" />

	<spring:message code="procession.startMoment" var="startMomentHeader" />
	<display:column property="startMoment" title="${startMomentHeader}"
		sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="procession.endMoment" var="endMomentHeader" />
	<display:column property="endMoment" title="${endMomentHeader}"
		sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="procession.locality" var="localityHeader" />
	<display:column property="locality" title="${localityHeader}"
		sortable="true" />

	<spring:message code="procession.associatedCost"
		var="associatedCostHeader" />
	<display:column property="associatedCost"
		title="${associatedCostHeader}" />

	<spring:message code="procession.numberOfRegistrations"
		var="numberOfRegistrationsHeader" />
	<display:column property="numberOfRegistrations"
		title="${numberOfRegistrationsHeader}" sortable="true" />

	<spring:message code="procession.stretches"
			var="stretchesOrRegisterHeader" />
	<security:authorize access="hasRole('BROTHER')">
		<spring:message code="procession.stretchesOrRegister"
			var="stretchesOrRegisterHeader" />
	</security:authorize>
	<display:column title="${stretchesOrRegisterHeader}">
		<a href="stretchOrder/list.do?processionId=${row.id}"> [<jstl:out
				value="${stretchesOrRegisterHeader}" />]
		</a>
	</display:column>

	<spring:message code="procession.details" var="detailsHeader" />
	<display:column title="${detailsHeader}">
		<a href="procession/details.do?processionId=${row.id}"> [<jstl:out
				value="${detailsHeader}" />]
		</a>
	</display:column>

	<security:authorize access="hasRole('VIEWER')">
		<spring:message code="procession.assessments" var="assessmentHeader" />
		<display:column title="${assessmentHeader}">
			<a href="assessment/viewer/assessById.do?processionId=${row.id}">
				[<jstl:out value="${assessmentHeader}" />]
			</a>
		</display:column>
	</security:authorize>

</display:table>

<div>
	<acme:cancel url="welcome/index.do" code="brotherhood.cancel" />
</div>