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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="box/administrator/edit.do" modelAttribute="box">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="administrator" />
	
	<acme:textbox code="box.name" path="name" />
	
	<acme:textarea code="box.description" path="description" />
	
	<acme:textbox code="box.locality" path="locality" />
	
	<acme:textbox code="box.location" path="location" />
	
	<acme:textbox code="box.numberOfChairs" path="numberOfChairs" />
	
	<fieldset>
	<legend><spring:message code="box.price" /></legend>
	
		<acme:textbox code="box.amount" path="price.amount" />
		
		<acme:textbox code="box.currency" path="price.currency" />
	
	</fieldset>
	
	<acme:submit name="save" code="box.save"/>
	
	<acme:cancel url="box/administrator/list.do" code="box.cancel"/>
	
</form:form>


	
