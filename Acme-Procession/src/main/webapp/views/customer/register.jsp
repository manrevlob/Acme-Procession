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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${action}" modelAttribute="registrationForm">
	
	<acme:textbox code="customer.username" path="username"/>
	
	<acme:password code="customer.password" path="password"/>
	
	<acme:password code="customer.secondPassword" path="secondPassword"/>
	
	<acme:textbox code="customer.name" path="name"/>
	
	<acme:textbox code="customer.surname" path="surname"/>

	<acme:textbox code="customer.email" path="email"/>

	<fieldset>
		<legend>
			<spring:message code="customer.creditCard" />
		</legend>	
		
		<acme:textbox code="customer.holderName" path="holderName"/>
		
		<acme:textbox code="customer.brandName" path="brandName"/>
		
		<acme:textbox code="customer.number" path="number"/>
		
		<acme:textbox code="customer.expirationMonth" path="expirationMonth"/>
		
		<acme:textbox code="customer.expirationYear" path="expirationYear"/>
		
		<acme:textbox code="customer.CVV" path="CVV"/>
		
	</fieldset>
	
	<acme:textbox code="customer.nationality" path="nationality"/>
	
	<spring:message code='customer.terms' />
	<br />
	
	<acme:submit name="save" code="customer.save"/>
	
	<input type="button" name="cancel"
		value="<spring:message code="customer.cancel" />"
		onclick="javascript: window.location.replace('welcome/index.do')" />

</form:form>