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

<form:form action="${formAction}" modelAttribute="sendMessage">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sender" />
	<form:hidden path="moment" />
	
	<acme:textbox code="message.subject" path="subject"/>
	
	<acme:textarea code="message.body" path="body"/>
	
	<acme:textbox code="message.attachment" path="attachment"/>
	
	<jstl:choose>
		<jstl:when test="${isReply == false}">
			
			<acme:select items="${recipients}" itemLabel="email" code="message.recipient" path="recipient"/>
			
			<acme:submit name="send" code="message.send"/>
			
		</jstl:when>
		<jstl:otherwise>
			
			<form:hidden path="recipient" />
			
			<acme:submit name="sendReply" code="message.sendReply"/>
			
		</jstl:otherwise>
	</jstl:choose>
	
	<acme:cancel url="messageFolder/actor/list.do" code="message.cancel"/>
	
</form:form>