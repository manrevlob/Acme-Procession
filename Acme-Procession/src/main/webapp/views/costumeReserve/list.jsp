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

<display:table name="costumeReserves" pagesize="5" class="displaytag"
	requestURI="${requestURI}" id="row">
	
	<spring:message code="costumeReserve.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" />
	
	<spring:message code="costumeReserve.type" var="typeHeader" />
	<display:column title="${typeHeader}">
		<jstl:choose>
			<jstl:when test="${row.type eq 'purchase'}">
				<spring:message code="costumeReserve.purchase"/>
			</jstl:when>
			<jstl:when test="${row.type eq 'rental'}">
				<spring:message code="costumeReserve.rental"/>
			</jstl:when>
			<jstl:otherwise>
				ERROR
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<spring:message code="costumeReserve.brotherhood" var="brotherhoodHeader" />
	<display:column property="costume.brotherhood.name" title="${brotherhoodHeader}" />
	
	<spring:message code="costumeReserve.costume" var="costumeHeader" />
	<display:column property="costume.size" title="${costumeHeader}" />

</display:table>

<div>
	<acme:cancel url="welcome/index.do" code="costumeReserve.cancel" />
</div>