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

<security:authorize access="hasRole('ADMINISTRATOR')">

	<div>
		<a href="box/administrator/create.do">
			<spring:message	code="box.create" />
		</a>
	</div>
</security:authorize>

<display:table name="boxes" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">
	
	<security:authorize  access="hasRole('ADMINISTRATOR')">
	
		<spring:message code="boxInstance.create" var="createHeader" />
		<display:column title="${createHeader}">
			<a href="boxInstance/administrator/create.do?boxId=${row.id}">
				[<jstl:out value="${createHeader}"/>]
			</a>
		</display:column>
		
		<spring:message code="box.edit" var="editHeader" />
		<display:column title="${editHeader}">
			<a href="box/administrator/edit.do?boxId=${row.id}">
				[<jstl:out value="${editHeader}"/>]
			</a>
		</display:column>
		
	</security:authorize>
	
	<spring:message code="box.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="box.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"/>
	
	<spring:message code="box.locality" var="localityHeader" />
	<display:column property="locality" title="${localityHeader}" sortable="true" />

	<spring:message code="box.location" var="locationHeader" />
	<display:column property="location" title="${locationHeader}" />
	
	<spring:message code="box.amount" var="amountHeader" />
	<display:column property="price.amount" title="${amountHeader}" />

	<spring:message code="box.currency" var="currencyHeader" />
	<display:column property="price.currency" title="${currencyHeader}" />

	<spring:message code="box.details" var="detailsHeader" />
	<display:column title="${detailsHeader}">
		<a href="box/administrator/details.do?boxId=${row.id}">
			[<jstl:out value="${detailsHeader}"/>]
		</a>
	</display:column>
	
	<security:authorize  access="hasRole('ADMINISTRATOR')">
		
		<spring:message code="box.listInstance" var="listInstanceHeader" />
		<display:column title="${listInstanceHeader}">
			<a href="boxInstance/administrator/list.do?boxId=${row.id}">
				[<jstl:out value="${listInstanceHeader}"/>]
			</a>
		</display:column>
		
	</security:authorize>

</display:table>

<div>
	<acme:cancel url="welcome/index.do" code="box.cancel"/>
</div>