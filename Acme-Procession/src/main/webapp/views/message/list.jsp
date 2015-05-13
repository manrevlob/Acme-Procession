<%--
 * message.jsp
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

<div>
	<a href="message/actor/create.do">
		<spring:message	code="message.sendAMessage" />
	</a>
</div>

<display:table name="messages" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">
	
	<spring:message code="message.sender" var="senderHeader" />
	<display:column property="sender.surname" title="${senderHeader}" sortable="true" />
	
	<spring:message code="message.recipient" var="recipientHeader" />
	<display:column property="recipient.surname" title="${recipientHeader}" sortable="true" />
	
	<spring:message code="message.subject" var="subjectHeader" />
	<display:column property="subject" title="${subjectHeader}" />
	
	<spring:message code="message.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
	
	<spring:message code="message.attachment" var="attachmentHeader" />
	<jstl:choose>
		<jstl:when test="${row.attachment != null}">
			<display:column property="attachment" title="${attachmentHeader}" />
		</jstl:when>
		<jstl:otherwise>
			<display:column title="${attachmentHeader}">
				-
			</display:column>
		</jstl:otherwise>
	</jstl:choose>
	
	<display:column>
		<a href="message/actor/details.do?messageId=${row.id}">
			<spring:message code="message.view" />
		</a>
	</display:column>
	
</display:table>

<div>
	<acme:cancel url="messageFolder/actor/list.do" code="message.cancel"/>
	<br />
</div>