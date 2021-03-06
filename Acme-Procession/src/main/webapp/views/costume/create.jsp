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

<script>
	var salePriceAmountLoadValue;
	var salePriceCurrencyLoadValue;
	var rentalPriceAmountLoadValue;
	var rentalPriceCurrencyLoadValue;
	
	jQuery(document).ready(function() {
		salePriceAmountLoadValue = document.getElementById('salePriceAmount').value;
		salePriceCurrencyLoadValue = document.getElementById('salePriceCurrency').value;
		rentalPriceAmountLoadValue = document.getElementById('rentalPriceAmount').value;
		rentalPriceCurrencyLoadValue = document.getElementById('rentalPriceCurrency').value;
		
		disableSale();
		disableRental();
	});
	
    function disableSale() {
    	document.getElementById('salePriceAmount').disabled = noToSaleCheck.checked;
    	document.getElementById('salePriceCurrency').disabled = noToSaleCheck.checked;
    	
    	if(document.getElementById('salePriceAmount').disabled) {
    		document.getElementById('salePriceAmount').value = 0;
    	} else {
    		document.getElementById('salePriceAmount').value = salePriceAmountLoadValue;
    	}
    	if(document.getElementById('salePriceCurrency').disabled) {
    		document.getElementById('salePriceCurrency').value = '-';
    	} else {
    		document.getElementById('salePriceCurrency').value = salePriceCurrencyLoadValue;
    	}
	}
    
    function disableRental() {
    	document.getElementById('rentalPriceAmount').disabled = noToRentalCheck.checked;
    	document.getElementById('rentalPriceCurrency').disabled = noToRentalCheck.checked;
    	if(document.getElementById('rentalPriceAmount').disabled) {
    		document.getElementById('rentalPriceAmount').value = 0;
    	} else {
    		document.getElementById('rentalPriceAmount').value = rentalPriceAmountLoadValue;
    	}
    	if(document.getElementById('rentalPriceCurrency').disabled) {
    		document.getElementById('rentalPriceCurrency').value = '-';
    	} else {
    		document.getElementById('rentalPriceCurrency').value = rentalPriceCurrencyLoadValue;
    	}
    }
</script>

<form:form action="costume/bigBrother/create.do" modelAttribute="createCostumesForm">

	<form:hidden path="brotherhood" />
	
	<acme:textbox code="costume.numberOfCostumes" path="numberOfCostumes" />
	
	<acme:textbox code="costume.size" path="size" />
	
	<form:radiobutton path="status" value="new"/><spring:message code="costume.new" />
	<form:radiobutton path="status" value="used"/><spring:message code="costume.used" />
	<form:radiobutton path="status" value="old"/><spring:message code="costume.old" />
	<span class="error"><form:errors path="status"/></span>
	
	<fieldset>
		<legend><spring:message code="costume.salePrice"/></legend>
		
		<form:checkbox id="noToSaleCheck" path="noToSale" onclick="disableSale()"/>
		<spring:message code="costume.noToSale"/>
		
		<br/>
		
		<form:label path="salePrice.amount">
			<spring:message code="costume.amount" />:
		</form:label>
		<form:input id="salePriceAmount" path="salePrice.amount"/>
		<form:errors cssClass="error" path="salePrice.amount" />
		
		<br/>
		
		<form:label path="salePrice.currency">
			<spring:message code="costume.currency" />:
		</form:label>
		<form:input id="salePriceCurrency" path="salePrice.currency"/>
		<form:errors cssClass="error" path="salePrice.currency" />
	</fieldset>
	
	<fieldset>
		<legend><spring:message code="costume.rentalPrice"/></legend>
		
		<form:checkbox id="noToRentalCheck" path="noToRental" onclick="disableRental()"/>
		<spring:message code="costume.noToRental"/>
		
		<br/>
		
		<form:label path="rentalPrice.amount">
			<spring:message code="costume.amount" />:
		</form:label>
		<form:input id="rentalPriceAmount" path="rentalPrice.amount"/>
		<form:errors cssClass="error" path="rentalPrice.amount" />
		
		<br/>
		
		<form:label path="rentalPrice.currency">
			<spring:message code="costume.currency" />:
		</form:label>
		<form:input id="rentalPriceCurrency" path="rentalPrice.currency"/>
		<form:errors cssClass="error" path="rentalPrice.currency" />
	</fieldset>
	
	<hr />
	
	<acme:textarea code="costume.comments" path="comments" />
	
	<acme:submit name="save" code="costume.save" />
	
	<acme:cancel url="brotherhood/bigBrother/listOwns.do" code="costume.cancel"/>
	
</form:form>