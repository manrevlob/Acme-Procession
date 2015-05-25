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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:choose>
	<jstl:when test="${image.photo ne null}">
		<img src="data:image/png;base64,<jstl:out value='${image.photo}'/>" />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="brotherhood.noImage" />
	</jstl:otherwise>
</jstl:choose>

<br />

<div>
	<input type="button" onclick="javascript:history.back();" value="<spring:message code='brotherhood.cancel' />" />
</div>
