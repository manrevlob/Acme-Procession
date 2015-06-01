<%--
 * dashboard.jsp
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

<security:authorize access="hasRole('ADMINISTRATOR')">
	<div>
		<b><spring:message code="MfindAllBrotHOrderByNumReg" /></b>
		<br />

		<display:table pagesize="5" class="displaytag" keepStatus="true"
			name="findAllBrotHOrderByNumReg" requestURI="${requestURI}" id="row">

			<spring:message code="findAllBrotHOrderByNumReg.name" var="nameHeader" />
			<display:column property="name" title="${nameHeader}" sortable="true" />

			<spring:message code="findAllBrotHOrderByNumReg.foundationYear"	var="foundationYearHeader" />
			<display:column property="foundationYear" title="${foundationYearHeader}" sortable="true" />

			<spring:message code="findAllBrotHOrderByNumReg.history" var="historyHeader" />
			<display:column property="history" title="${historyHeader}" sortable="true" />

		</display:table>
		<br />
		
		<b><spring:message code="MfindAllProceOrderByNumReg" /></b>
		<br />

		<display:table pagesize="5" class="displaytag" keepStatus="true"
			name="findAllProceOrderByNumReg" requestURI="${requestURI}" id="row">
			
			<spring:message code="findAllProceOrderByNumReg.nameBrotherhood" var="nameHeader" />
			<display:column value="${row.brotherhood.name}" title="${nameHeader}" sortable="true" />

			<spring:message code="findAllProceOrderByNumReg.name" var="nameHeader" />
			<display:column property="name" title="${nameHeader}" sortable="true" />

			<spring:message code="findAllProceOrderByNumReg.startMoment" var="startMomentHeader" />
			<display:column property="startMoment" title="${startMomentHeader}" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" />

			<spring:message code="findAllProceOrderByNumReg.endMoment" var="endMomentHeader" />
			<display:column property="endMoment" title="${endMomentHeader}" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" />
			
			<spring:message code="findAllProceOrderByNumReg.locality" var="localityHeader" />
			<display:column property="locality" title="${localityHeader}" sortable="true" />

		</display:table>
		<br />

		<b><spring:message code="MfindByBrotherhoodAndBrother" /></b>
		<br />

		<display:table pagesize="5" class="displaytag" keepStatus="true"
			name="findByBrotherhoodAndBrother" requestURI="${requestURI}" id="row">

			<spring:message code="findByBrotherhoodAndBrother.name" var="nameHeader" />
			<display:column property="name" title="${nameHeader}" sortable="true" />

			<spring:message code="findByBrotherhoodAndBrother.surname" var="surnameHeader" />
			<display:column property="surname" title="${surnameHeader}" sortable="true" />

			<spring:message code="findByBrotherhoodAndBrother.email" var="emailHeader" />
			<display:column property="email" title="${emailHeader}" sortable="true" />

		</display:table>
		<br />

		<b><spring:message code="MfindAllReserMorBox" /></b>
		<br />

		<display:table pagesize="5" class="displaytag" keepStatus="true"
			name="findAllReserMorBox" requestURI="${requestURI}" id="row">

			<spring:message code="findAllReserMorBox.name" var="nameHeader" />
			<display:column property="name" title="${nameHeader}" sortable="true" />

			<spring:message code="findAllReserMorBox.surname" var="surnameHeader" />
			<display:column property="surname" title="${surnameHeader}" sortable="true" />

			<spring:message code="findAllReserMorBox.email" var="emailHeader" />
			<display:column property="email" title="${emailHeader}" sortable="true" />

		</display:table>
		<br />

		<b><spring:message code="MfindAllOrderByNumAssess" /></b>
		<br />

		<display:table pagesize="5" class="displaytag" keepStatus="true"
			name="findAllOrderByNumAssess" requestURI="${requestURI}" id="row">

			<spring:message code="findAllOrderByNumAssess.name" var="nameHeader" />
			<display:column property="name" title="${nameHeader}" sortable="true" />

			<spring:message code="findAllOrderByNumAssess.surname" var="surnameHeader" />
			<display:column property="surname" title="${surnameHeader}" sortable="true" />

			<spring:message code="findAllOrderByNumAssess.email" var="emailHeader" />
			<display:column property="email" title="${emailHeader}" sortable="true" />

		</display:table>
		<br />

		<b><spring:message code="MfindByNumBrotherhood" /></b>
		<br />

		<display:table pagesize="5" class="displaytag" keepStatus="true"
			name="findByNumBrotherhood" requestURI="${requestURI}" id="row">

			<spring:message code="findByNumBrotherhood.name" var="nameHeader" />
			<display:column property="name" title="${nameHeader}" sortable="true" />

			<spring:message code="findByNumBrotherhood.surname" var="surnameHeader" />
			<display:column property="surname" title="${surnameHeader}" sortable="true" />

			<spring:message code="findByNumBrotherhood.email" var="emailHeader" />
			<display:column property="email" title="${emailHeader}" sortable="true" />

		</display:table>
		<br />

		<b><spring:message code="MfindAllTotalCostOfRegistration" /></b>
		<br />

		<display:table pagesize="5" class="displaytag" keepStatus="true"
			name="findAllTotalCostOfRegistration" requestURI="${requestURI}" id="row">

			<spring:message code="findAllTotalCostOfRegistration.name" var="nameHeader" />
			<display:column title="${nameHeader}" value="${row[0].name}"
				sortable="true" />

			<spring:message code="findAllTotalCostOfRegistration.surname" var="surnameHeader" />
			<display:column title="${surnameHeader}" value="${row[0].surname}"
				sortable="true" />

			<spring:message code="findAllTotalCostOfRegistration.email" var="emailHeader" />
			<display:column title="${emailHeader}" value="${row[0].email}"
				sortable="true" />
				
			<spring:message code="findAllTotalCostOfRegistration.sum" var="sumHeader" />
			<display:column title="${sumHeader}" value="${row[1]}"
				sortable="true" />

		</display:table>
		<br />
		
		<b><spring:message code="MfindAllTotalCostOfCostume" /></b>
		<br />

		<display:table pagesize="5" class="displaytag" keepStatus="true"
			name="findAllTotalCostOfCostume" requestURI="${requestURI}" id="row">

			<spring:message code="findAllTotalCostOfCostume.name" var="nameHeader" />
			<display:column title="${nameHeader}" value="${row[0].name}"
				sortable="true" />

			<spring:message code="findAllTotalCostOfCostume.surname" var="surnameHeader" />
			<display:column title="${surnameHeader}" value="${row[0].surname}"
				sortable="true" />

			<spring:message code="findAllTotalCostOfCostume.email" var="emailHeader" />
			<display:column title="${emailHeader}" value="${row[0].email}"
				sortable="true" />
				
			<spring:message code="findAllTotalCostOfCostume.sum" var="sumHeader" />
			<display:column title="${sumHeader}" value="${row[1]}"
				sortable="true" />

		</display:table>
		<br />
		
		<b><spring:message code="MfindAllByAssess" /></b>
		<br />

		<display:table pagesize="5" class="displaytag" keepStatus="true"
			name="findAllByAssess" requestURI="${requestURI}" id="row">

			<spring:message code="findAllByAssess.name" var="nameHeader" />
			<display:column title="${nameHeader}" value="${row[0].name}"
				sortable="true" />

			<spring:message code="findAllByAssess.foundationYear" var="fundationYearHeader" />
			<display:column title="${foundationYearHeader}" value="${row[0].foundationYear}"
				sortable="true" />

			<spring:message code="findAllByAssess.history" var="historyHeader" />
			<display:column title="${historyHeader}" value="${row[0].history}"
				sortable="true" />
				
			<spring:message code="findAllByAssess.assess" var="assessHeader" />
			<display:column title="${assessHeader}" value="${row[1]}"
				sortable="true" />

		</display:table>
		<br />
		
		<b><spring:message code="MfindWithAutoAndCostumePay" /></b>
		<br />

		<display:table pagesize="5" class="displaytag" keepStatus="true"
			name="findWithAutoAndCostumePay" requestURI="${requestURI}" id="row">

			<spring:message code="findWithAutoAndCostumePay.name" var="nameHeader" />
			<display:column property="name" title="${nameHeader}" sortable="true" />

			<spring:message code="findWithAutoAndCostumePay.surname" var="surnameHeader" />
			<display:column property="surname" title="${surnameHeader}" sortable="true" />

			<spring:message code="findWithAutoAndCostumePay.email" var="emailHeader" />
			<display:column property="email" title="${emailHeader}" sortable="true" />

		</display:table>
		<br />
		
	</div>
</security:authorize>
