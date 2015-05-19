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
	<jstl:if test="${isAutorized}">
		<div>
			<a href="brotherhood/brother/create.do">
				<spring:message	code="brotherhood.create" />
			</a>
		</div>
	</jstl:if>
</security:authorize>

<display:table name="brotherhoods" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">
	
	<security:authorize  access="hasRole('BROTHER')">
			<spring:message code="brotherhood.edit" var="editHeader" />
			<display:column>
				<jstl:if test="${row.userIsOwner}">
					<a href="brotherhood/brother/edit.do?brotherhoodId=${row.id}">
						[<jstl:out value="${editHeader}"/>]
					</a>
				</jstl:if>
			</display:column>
			
			<spring:message code="brotherhood.addBigBrother" var="addBigBrotherHeader" />
			<display:column>
				<jstl:if test="${row.userIsOwner}">
					<a href="brotherhood/brother/addBigBrother.do?brotherhoodId=${row.id}">
						[<jstl:out value="${addBigBrotherHeader}"/>]
					</a>
				</jstl:if>
			</display:column>
	</security:authorize>
	
	<spring:message code="brotherhood.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="brotherhood.foundationYear" var="foundationYearHeader" />
	<display:column property="foundationYear" title="${foundationYearHeader}" sortable="true" />
	
	<spring:message code="brotherhood.numberOfBrothers" var="numberOfBrothersHeader" />
	<display:column property="numberOfBrothers" title="${numberOfBrothersHeader}" sortable="true" />

	<spring:message code="brotherhood.processions" var="processionsHeader" />
	<display:column title="${processionsHeader}">
		<a href="procession/list.do?brotherhoodId=${row.id}">
			[<jstl:out value="${processionsHeader}"/>]
		</a>
	</display:column>

	<spring:message code="brotherhood.carvings" var="carvingsHeader" />
	<display:column title="${carvingsHeader}">
		<a href="carving/list.do?brotherhoodId=${row.id}">
			[<jstl:out value="${carvingsHeader}"/>]
		</a>
	</display:column>

	<spring:message code="brotherhood.details" var="detailsHeader" />
	<display:column title="${detailsHeader}">
		<a href="brotherhood/details.do?brotherhoodId=${row.id}">
			[<jstl:out value="${detailsHeader}"/>]
		</a>
	</display:column>

</display:table>

<div>
	<acme:cancel url="welcome/index.do" code="brotherhood.cancel"/>
</div>