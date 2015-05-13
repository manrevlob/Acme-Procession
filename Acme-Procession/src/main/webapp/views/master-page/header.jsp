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

<div>
	<img src="images/logo.png" alt="Acme-Procession Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" />[+] - (<security:authentication property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="request/administrator/list.do"><spring:message code="master.page.administrator.request" /></a></li>
					<li><a href="administrator/administrator/registerAdministrator.do"><spring:message code="master.page.registerAdministrator" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('BROTHER')">
			<li><a class="fNiv"><spring:message	code="master.page.brother" />[+] - (<security:authentication property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="brotherhood/brother/list.do"><spring:message code="master.page.brother.listMyBrotherhoods" /></a></li>
					<li><a href="brotherhood/brother/listOwns.do"><spring:message code="master.page.brother.listOwnBrotherhoods" /></a></li>
					<li><a href="request/brother/list.do"><spring:message code="master.page.brother.requests" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('VIEWER')">
			<li><a class="fNiv"><spring:message	code="master.page.viewer" />[+] - (<security:authentication property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="customer/registerViewer.do"><spring:message code="master.page.registerAsViewer" /></a></li>
			<li><a class="fNiv" href="customer/registerBrother.do"><spring:message code="master.page.registerAsBrother" /></a></li>
		</security:authorize>
		
		<li><a class="fNiv" href="brotherhood/list.do"><spring:message code="master.page.listBrotherhoods" /></a></li>
		
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="j_spring_security_logout"><spring:message code="master.page.logout" /></a></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

