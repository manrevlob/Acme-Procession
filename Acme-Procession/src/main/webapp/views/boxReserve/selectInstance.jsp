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

<form:form action="${action}" modelAttribute="CreateBoxReserveForm">
	

	<acme:select items="${boxInstanceDates}" itemLabel="date" onchange="javascript: reloadChairsAndPrice()" code="boxReserve.date" path="CreateBoxReserveForm"/>
	
	
	
	<jstl:choose>
		<jstl:when test="${fn:length(chairsAvaiblables) ne 0}">
			<form:select multiple="true" path="chairs">
			    <form:options items="${chairsAvaiblables}" itemValue="id" itemLabel=""/>
			</form:select>
			
			<form:errors path="essays" cssClass="error" />
		</jstl:when>
		<jstl:otherwise>
			<span class="error"><spring:message code="publicSession.essays.error" /></span>
		</jstl:otherwise>
	</jstl:choose>
	
	<br/>
	
	<acme:submit name="save" code="boxReserve.save"/>
	
	<acme:cancel url="box/viewer/list.do" code="boxReserve.cancel"/>
	
	<script type="text/javascript">
		function reloadChairsAndPrice() {
			var boxInstanceId = $('select#boxInstanceDates').val();
			var placeholder = $('select#chairs'); 

			placeholder.load("boxInstance/findByBoxInstanceId.do?boxInstanceId=" + boxInstanceId);			
		}
	</script>
	
</form:form>