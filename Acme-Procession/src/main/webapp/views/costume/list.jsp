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
			<a href="costume/brother/create.do?brotherhoodId=${param.brotherhoodId}"> <spring:message
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
					<a href="costume/brother/edit.do?costumeId=${row.id}"> [<jstl:out
							value="${editHeader}" />]
					</a>
				</jstl:when>
				<jstl:otherwise>
					-
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</security:authorize>
	
	<spring:message code="costume.brotherhood" var="brotherhoodHeader" />
	<display:column property="brotherhood" title="${brotherhoodHeader}" />
	
	<spring:message code="costume.isAvailable" var="isAvailableHeader" />
	<display:column property="isAvailable" title="${isAvailableHeader}" />

	<spring:message code="costume.size" var="sizeHeader" />
	<display:column property="size" title="${sizeHeader}" />

	<spring:message code="costume.status" var="statusHeader" />
	<display:column property="status" title="${statusHeader}" />
	
	<spring:message code="costume.salePrice" var="salePriceHeader" />
	<display:column property="salePrice" title="${salePriceHeader}" />
	
	<spring:message code="costume.rentalPrice" var="rentalPriceHeader" />
	<display:column property="rentalPrice" title="${rentalPriceHeader}" />

	<spring:message code="costume.details" var="detailsHeader" />
	<display:column title="${detailsHeader}">
		<a href="costume/details.do?costumeId=${row.id}"> [<jstl:out
				value="${detailsHeader}" />]
		</a>
	</display:column>

</display:table>

<div>
	<acme:cancel url="welcome/index.do" code="brotherhood.cancel" />
</div>