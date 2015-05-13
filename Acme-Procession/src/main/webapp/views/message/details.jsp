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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="message/actor/reply.do" modelAttribute="msg">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="message.recipient" path="recipient.userAccount.username" readonly="true"/>
	
	<acme:textbox code="message.sender" path="sender.userAccount.username" readonly="true"/>
	
	<acme:textbox code="message.subject" path="subject" readonly="true"/>
	
	<acme:textarea code="message.body" path="body" readonly="true"/>
	
	<acme:textbox code="message.moment" path="moment" readonly="true"/>
	
	<acme:textbox code="message.attachment" path="attachment" readonly="true"/>
	
	<div>
		<jstl:if test="${canReply}">
			<acme:submit name="reply" code="message.reply"/>
		</jstl:if>
		<input type="button" onclick="javascript:history.back();" value="<spring:message code='message.cancel' />" />
		<br />
	</div>
	
</form:form>

<form:form action="message/actor/details.do" modelAttribute="msg">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="recipient" />
	<form:hidden path="sender" />
	<form:hidden path="messageFolder" />
	<form:hidden path="subject" />
	<form:hidden path="body" />
	<form:hidden path="moment" />
	<form:hidden path="attachment" />
	
	<jstl:choose>
		<jstl:when test="${msg.messageFolder.name == 'Trashbox'}">
			<input type="submit" name="delete"
			value="<spring:message code="message.delete" />"
			onclick="return confirm('<spring:message code="message.deletePermanently" />')" />&nbsp;
		</jstl:when>
		<jstl:otherwise>
			<input type="submit" name="delete"
			value="<spring:message code="message.delete" />"
			onclick="return confirm('<spring:message code="message.deleteMessage" />')" />&nbsp;
		</jstl:otherwise>
	</jstl:choose>

</form:form>