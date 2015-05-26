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

<form:form action="costume/bigBrother/edit.do" modelAttribute="costume">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="brotherhood" />
	<form:hidden path="isAvailable" />
	
	<acme:textbox code="costume.size" path="size" />
	
	<form:radiobutton path="status" value="new"/><spring:message code="costume.new" />
	<form:radiobutton path="status" value="used"/><spring:message code="costume.used" />
	<form:radiobutton path="status" value="old"/><spring:message code="costume.old" />
	<span class="error"><form:errors path="status"/></span>
	
	<fieldset>
		<legend><spring:message code="costume.salePrice"/></legend>
		<acme:textbox code="costume.amount" path="salePrice.amount" />
		<acme:textbox code="costume.currency" path="salePrice.currency" />
	</fieldset>
	
	<fieldset>
		<legend><spring:message code="costume.rentalPrice"/></legend>
		<acme:textbox code="costume.amount" path="rentalPrice.amount" />
		<acme:textbox code="costume.currency" path="rentalPrice.currency" />
	</fieldset>
	
	<acme:textarea code="costume.comments" path="comments" />
	
	<acme:submit name="save" code="costume.save" />
	
	<acme:cancel url="brotherhood/bigBrother/listOwns.do" code="costume.cancel"/>
	
</form:form>