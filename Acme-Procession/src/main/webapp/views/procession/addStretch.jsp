<%--
 * addStage.jsp
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

<form:form action="procession/bigBrother/addStretch.do" modelAttribute="addStretchToProcessionForm">
	
	<form:hidden path="procession" />
	
	<acme:select items="${availableStretches}" itemLabel="name" code="procession.addStretch" path="stretch"/>
	
	<acme:submit name="addStretch" code="procession.addStretch.add"/> 
	
	<acme:cancel url="brotherhood/bigBrother/listOwns.do" code="procession.cancel"/>
	
</form:form>