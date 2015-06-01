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

<nav class="navbar navbar-inverse navbar-static-top">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			
			<a class="navbar-brand" href="#"><span display="inline"><img alt="Brand" src="images/minilogo.png">Acme-Procession</span></a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<security:authorize access="hasRole('ADMINISTRATOR')">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><spring:message	code="master.page.administrator" /><span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="request/administrator/list.do"><spring:message code="master.page.administrator.request" /></a></li>
							<li><a href="box/administrator/list.do"><spring:message code="master.page.administrator.boxes" /></a></li>
							<li><a href="box/administrator/listOwns.do"><spring:message code="master.page.administrator.boxesOwns" /></a></li>
							<li><a href="dashboard/administrator/list.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
							<li class="divider"></li>
							<li><a href="administrator/administrator/registerAdministrator.do"><spring:message code="master.page.registerAdministrator" /></a></li>
						</ul>
					</li>
				</security:authorize>
				<security:authorize access="hasRole('BROTHER')">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><spring:message	code="master.page.brother" /><span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="brotherhood/brother/list.do"><spring:message code="master.page.brother.listMyBrotherhoods" /></a></li>
							<li><a href="procession/brother/listAvailables.do"><spring:message code="master.page.brother.processionAvailable" /></a></li>
							<li><a href="registration/brother/list.do"><spring:message code="master.page.brother.listOwnRegistrations" /></a></li>
							<li><a href="registrationInvoice/brother/list.do"><spring:message code="master.page.brother.listInvoicesReg" /></a></li>
							<li><a href="costumeInvoice/brother/list.do"><spring:message code="master.page.brother.listInvoicesCostume" /></a></li>
							<li><a href="costume/brother/findBySize.do"><spring:message code="master.page.brother.searchCostumes" /></a></li>
							<li><a href="costumeReserve/brother/list.do"><spring:message code="master.page.brother.listCostumeReserves" /></a></li>
							<li><a href="request/brother/list.do"><spring:message code="master.page.brother.requests" /></a></li>
						</ul>
					</li>
				</security:authorize>
				<security:authorize access="hasRole('BIGBROTHER')">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><spring:message	code="master.page.bigBrother" /><span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="brotherhood/bigBrother/listOwns.do"><spring:message code="master.page.brother.listOwnBrotherhoods" /></a></li>
							<li><a href="stretch/bigBrother/findByBrotherhood.do"><spring:message code="master.page.brother.findStretches" /></a></li>
							<li><a href="costume/bigBrother/findByBrotherhood.do"><spring:message code="master.page.brother.findCostumes" /></a></li>
						</ul>
					</li>
				</security:authorize>
				<security:authorize access="hasRole('VIEWER')">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><spring:message	code="master.page.viewer" /><span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="assessment/viewer/list.do"><spring:message code="master.page.viewer.assessByViewer" /></a></li>
							<li><a href="boxReserve/viewer/list.do"><spring:message code="master.page.viewer.boxReserveByViewer" /></a></li>
							<li><a href="boxInvoice/viewer/list.do"><spring:message code="master.page.viewer.boxInvoice.list" /></a></li>
							<li><a href="box/viewer/list.do"><spring:message code="master.page.viewer.box.list" /></a></li>
						</ul>
					</li>
				</security:authorize>
				<li><a href="brotherhood/list.do"><spring:message code="master.page.listBrotherhoods" /></a></li>
				<security:authorize access="isAuthenticated()">
					<li><a href="messageFolder/actor/list.do"><spring:message code="master.page.privateMessages" /></a></li>
				</security:authorize>
				<security:authorize access="isAnonymous()">
					<li><a href="customer/registerBrother.do"><spring:message code="master.page.registerAsBrother" /></a></li>
					<li><a href="customer/registerViewer.do"><spring:message code="master.page.registerAsViewer" /></a></li>
				</security:authorize>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<security:authorize access="isAnonymous()">
					<li><a href="security/login.do"><spring:message code="master.page.login" /></a></li>
				</security:authorize>
				<security:authorize access="isAuthenticated()">
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> - (<security:authentication property="principal.username" />)</a></li>
				</security:authorize>
			</ul>
		</div><!-- /.navbar-collapse -->
	</div><!-- /.container-fluid -->
</nav>

<div>
	<img src="images/logo.png" alt="Acme-Procession Co., Inc." />
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

