<%--
 * header.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
	<img src="images/logo.png" alt="Acme-Procession Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" />[+]</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="request/administrator/list.do"><spring:message code="master.page.administrator.request" /></a></li>
					<li><a href="administrator/administrator/registerAdministrator.do"><spring:message code="master.page.registerAdministrator" /></a></li>
					<li><a href="box/administrator/list.do"><spring:message code="master.page.administrator.boxes" /></a></li>
					<li><a href="box/administrator/listOwns.do"><spring:message code="master.page.administrator.boxesOwns" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('BROTHER')">
			<li><a class="fNiv"><spring:message	code="master.page.brother" />[+]</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="brotherhood/brother/list.do"><spring:message code="master.page.brother.listMyBrotherhoods" /></a></li>
					<li><a href="procession/brother/listAvailables.do"><spring:message code="master.page.brother.processionAvailable" /></a></li>
					<li><a href="registration/brother/list.do"><spring:message code="master.page.brother.listOwnRegistrations" /></a></li>
					<li><a href="request/brother/list.do"><spring:message code="master.page.brother.requests" /></a></li>
					<li><a href="registrationInvoice/brother/list.do"><spring:message code="master.page.brother.listInvoices" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('BIGBROTHER')">
			<li><a class="fNiv"><spring:message	code="master.page.bigBrother" />[+]</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="brotherhood/bigBrother/listOwns.do"><spring:message code="master.page.brother.listOwnBrotherhoods" /></a></li>
					<li><a href="stretch/bigBrother/findByBrotherhood.do"><spring:message code="master.page.brother.findStretches" /></a></li>
					<li><a href="costume/bigBrother/findByBrotherhood.do"><spring:message code="master.page.brother.findCostumes" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('VIEWER')">
			<li><a class="fNiv"><spring:message	code="master.page.viewer" />[+]</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="assessment/viewer/list.do"><spring:message code="master.page.viewer.assessByViewer" /></a></li>
					<li><a href="boxReserve/viewer/list.do"><spring:message code="master.page.viewer.boxReserveByViewer" /></a></li>
					<li><a href="boxInvoice/viewer/list.do"><spring:message code="master.page.viewer.boxInvoice.list" /></a></li>
					<li><a href="box/viewer/list.do"><spring:message code="master.page.viewer.box.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="customer/registerBrother.do"><spring:message code="master.page.registerAsBrother" /></a></li>
			<li><a class="fNiv" href="customer/registerViewer.do"><spring:message code="master.page.registerAsViewer" /></a></li>
		</security:authorize>
		
		<li><a class="fNiv" href="brotherhood/list.do"><spring:message code="master.page.listBrotherhoods" /></a></li>
		
		<security:authorize access="isAuthenticated()">
			<li><a href="messageFolder/actor/list.do"><spring:message code="master.page.privateMessages" /></a></li>
		
			<li><a class="fNiv" href="j_spring_security_logout"><spring:message code="master.page.logout" /> - (<security:authentication property="principal.username" />)</a></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

