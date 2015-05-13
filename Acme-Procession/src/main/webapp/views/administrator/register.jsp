<%--
 * edit.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="${action}" modelAttribute="registrationAdminForm">
	
	
	<acme:textbox code="administrator.username" path="username"/>

	<acme:password code="administrator.password" path="password"/>
	
	<acme:password code="administrator.password2" path="secondPassword"/>

	<acme:textbox code="administrator.name" path="name"/>

	<acme:textbox code="administrator.surname" path="surname"/>

	<acme:textbox code="administrator.email" path="email"/>
	
	<acme:textbox code="administrator.phone" path="phone"/>
	
	<acme:textbox code="administrator.homePage" path="homePage"/>
	
	<input type="submit" name="save"
			value="<spring:message code="administrator.save" />"
			onclick="return confirm('<spring:message code="administrator.confirm.register" />')" />

	<input type="button" onclick="javascript: window.location.replace('welcome/index.do')" value="<spring:message code='administrator.cancel' />" />
	<br />

</form:form>