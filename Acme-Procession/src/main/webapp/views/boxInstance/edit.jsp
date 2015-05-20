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

<form:form action="boxInstance/administrator/edit.do" modelAttribute="boxInstance">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="box" />
	<form:hidden path="boxReserves" />
	
	<b><spring:message code="boxInstance.box.name" />:</b>
	<jstl:out value="${boxInstance.box.name}" />
	<br />
	
	<acme:textbox code="boxInstance.date" path="date" />
	
	<fieldset>
	<legend><spring:message code="boxInstance.Price" /></legend>
	
		<acme:textbox code="boxInstance.amount" path="price.amount" />
		
		<acme:textbox code="boxInstance.currency" path="price.currency" />
	
	</fieldset>
	
	<acme:submit name="save" code="boxInstance.save"/>
	
	<acme:cancel url="box/administrator/list.do" code="boxInstance.cancel"/>
	
</form:form>


	
