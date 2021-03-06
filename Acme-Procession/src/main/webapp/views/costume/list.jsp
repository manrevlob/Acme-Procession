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

<security:authorize access="hasRole('BIGBROTHER')">
	<jstl:if test="${isBigBrother}">
		<div>
			<a href="costume/bigBrother/create.do?brotherhoodId=${param.brotherhoodId}"> <spring:message
					code="costume.create" />
			</a>
		</div>
	</jstl:if>
</security:authorize>

<display:table name="costumes" pagesize="5" class="displaytag"
	requestURI="${requestURI}" id="row">

	<security:authorize access="hasRole('BIGBROTHER')">
		<spring:message code="costume.edit" var="editHeader" />
		<display:column>
			<jstl:choose>
				<jstl:when test="${row.brotherhood.userIsOwner}">
					<a href="costume/bigBrother/edit.do?costumeId=${row.id}"> [<jstl:out
							value="${editHeader}" />]
					</a>
				</jstl:when>
				<jstl:otherwise>
					-
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
		
		<spring:message code="costume.markAsAvailable" var="maskAsAvailableHeader" />
		<display:column>
			<jstl:if test="${row.situation == 'rented'}">
				<a href="costume/bigBrother/markAsAvailable.do?costumeId=${row.id}"> [<jstl:out
						value="${maskAsAvailableHeader}" />]
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('BROTHER')">
		<spring:message code="costume.buy" var="buyHeader" />
		<display:column title="${buyHeader}">
			<jstl:if test="${row.salePrice ne null && row.situation == 'available'}">
				<a href="costumeReserve/brother/buy.do?costumeId=${row.id}"> [<jstl:out
						value="${buyHeader}" />]
				</a>
			</jstl:if>
		</display:column>
		
		<spring:message code="costume.rent" var="rentHeader" />
		<display:column title="${rentHeader}">
			<jstl:if test="${row.rentalPrice ne null && row.situation == 'available'}">
				<a href="costumeReserve/brother/rent.do?costumeId=${row.id}"> [<jstl:out
						value="${rentHeader}" />]
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>
	
	<spring:message code="costume.brotherhood" var="brotherhoodHeader" />
	<display:column property="brotherhood.name" title="${brotherhoodHeader}" />
	
	<security:authorize access="hasRole('BIGBROTHER')">
		<spring:message code="costume.isAvailable" var="isAvailableHeader" />
		<display:column title="${isAvailableHeader}">
			<jstl:choose>
				<jstl:when test="${row.situation == 'sold'}">
					<spring:message code="costume.sold"/>
				</jstl:when>
				<jstl:when test="${row.situation == 'rented'}">
					<spring:message code="costume.rented"/>
				</jstl:when>
				<jstl:when test="${row.situation == 'available'}">
					<spring:message code="costume.available"/>
				</jstl:when>
				<jstl:otherwise>
					ERROR
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</security:authorize>

	<spring:message code="costume.size" var="sizeHeader" />
	<display:column property="size" title="${sizeHeader}" sortable="true"/>

	<spring:message code="costume.status" var="statusHeader" />
	<display:column title="${statusHeader}" sortable="true">
		<jstl:choose>
			<jstl:when test="${row.status == 'new'}">
				<spring:message code="costume.new"/>
			</jstl:when>
			<jstl:when test="${row.status == 'used'}">
				<spring:message code="costume.used"/>
			</jstl:when>
			<jstl:when test="${row.status == 'old'}">
				<spring:message code="costume.old"/>
			</jstl:when>
			<jstl:otherwise>
				ERROR
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<spring:message code="costume.salePrice" var="salePriceHeader" />
	<display:column property="salePrice" title="${salePriceHeader}"  />
	
	<spring:message code="costume.rentalPrice" var="rentalPriceHeader" />
	<display:column property="rentalPrice" title="${rentalPriceHeader}" />

	<spring:message code="costume.details" var="detailsHeader" />
	<display:column title="${detailsHeader}">
		<jstl:choose>
			<jstl:when test="${requestURI == 'costume/bigBrother/list.do'}">
				<a href="costume/bigBrother/details.do?costumeId=${row.id}"> [<jstl:out
						value="${detailsHeader}" />]
				</a>
			</jstl:when>
			<jstl:otherwise>
				<a href="costume/brother/details.do?costumeId=${row.id}"> [<jstl:out
						value="${detailsHeader}" />]
				</a>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>

</display:table>

<div>
	<acme:cancel url="welcome/index.do" code="costume.cancel" />
</div>