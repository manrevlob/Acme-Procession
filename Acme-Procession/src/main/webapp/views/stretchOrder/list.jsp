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

<jstl:if test="${param.error ne null}">
	<span class="error">
		<spring:message code="${param.error}"/>
	</span>
</jstl:if>

<security:authorize access="hasRole('BIGBROTHER')">
	<div>
		<a href="procession/bigBrother/addStretch.do?processionId=${param.processionId}">
			<spring:message	code="stretch.create" />
		</a>
	</div>
</security:authorize>

<display:table name="stretchOrders" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">
	
	<security:authorize  access="hasRole('BIGBROTHER')">
		<spring:message code="stretch.delete" var="deleteHeader" />
		<display:column>
			<jstl:if test="${row.stretch.brotherhood.userIsOwner}">
				<a href="stretchOrder/bigBrother/delete.do?stretchOrderId=${row.id}">
					[<jstl:out value="${deleteHeader}"/>]
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<security:authorize  access="hasRole('BROTHER')">
		<spring:message code="stretch.register" var="registerHeader" />
		<display:column title="${registerHeader}">
			<jstl:choose>
				<jstl:when test="${row.stretch.brotherhood.userIsRegiter eq true and row.procession.isClosed ne true and row.stretch.canRegister}">
					<a href="stretchOrder/brother/register.do?stretchOrderId=${row.id}">
						[<jstl:out value="${registerHeader}"/>]
					</a>
				</jstl:when>
				<jstl:otherwise>
					-
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</security:authorize>

	<spring:message code="stretch.orderNumber" var="orderNumberHeader" />
	<display:column property="orderNumber" title="${orderNumberHeader}" sortable="true" />

	<spring:message code="stretch.name" var="nameHeader" />
	<display:column property="stretch.name" title="${nameHeader}" sortable="true" />

	<spring:message code="stretch.description" var="descriptionHeader" />
	<display:column property="stretch.description" title="${descriptionHeader}" />
	
	<security:authorize  access="hasRole('BIGBROTHER')">
		<spring:message code="stretch.moveToUp" var="moveUpHeader" />
		<display:column>
			<jstl:if test="${row.stretch.brotherhood.userIsOwner && row.orderNumber > 1}">
				<a href="stretchOrder/bigBrother/moveToUp.do?stretchOrderId=${row.id}">
					[<jstl:out value="${moveUpHeader}"/>]
				</a>
			</jstl:if>
		</display:column>

		<spring:message code="stretch.moveToDown" var="moveToDownHeader" />
		<display:column>
			<jstl:if test="${row.stretch.brotherhood.userIsOwner && row.orderNumber != stretchOrders.size()}">
				<a href="stretchOrder/bigBrother/moveToDown.do?stretchOrderId=${row.id}">
					[<jstl:out value="${moveToDownHeader}"/>]
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>

</display:table>

<div>
	<acme:cancel url="welcome/index.do" code="stretch.cancel"/>
</div>