<%--
 * create.jsp
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

<form:form action="invoice/innkeeper/create.do" modelAttribute="createInvoiceForm">

	<form:hidden path="booking" />
	
	<acme:textbox code="invoice.totalCost" path="totalCost" readonly="true"/>

	<acme:textarea code="invoice.description" path="description"/>
	
	<acme:textarea code="invoice.comment" path="comment"/>
	
	<acme:submit name="save" code="invoice.save" />

	<acme:cancel url="booking/innkeeper/list.do" code="invoice.cancel" />

</form:form>