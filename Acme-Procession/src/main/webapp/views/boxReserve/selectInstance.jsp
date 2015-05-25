<%--
 * selectInstance.jsp
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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="boxReserve/viewer/selectInstance.do" modelAttribute="createBoxReserveForm">

	<form:hidden path="box" />
	
	<acme:select items="${boxInstances}" itemLabel="dateAndChairs" code="boxReserve.dateAndChairs" path="boxInstance"/>
	
	<acme:textbox code="boxReserve.chairs" path="chairs" />
	
	<acme:submit name="save" code="boxReserve.save"/>
	
	<acme:cancel url="box/viewer/list.do" code="boxReserve.cancel"/>

</form:form>