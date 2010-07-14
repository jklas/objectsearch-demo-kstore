<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<html>
    <head>
        <title>Kstore Checkout</title>
        <link rel="stylesheet" href="css/checkout.css" />
        <link rel="shortcut icon" href="css/favicon.ico" type="image/x-icon" />        
    </head>
    <body>
   	
   		<!-- top bar -->
        <div class="topBar">
	       	<a href="index.jsp"><img style="float:left;" src="images/kstore-logo-mini.png" alt="Kstore" border="0" /></a>
	        
	       	<form class="resultPageSearchForm" name="search" method="get" action="query.htm">
		       	<c:choose>
			       	<c:when test="${! empty searchResults['search.query']}">	       	
				       	<input type="text" class="resultsPageSearch" name="q" value="<c:out value="${searchResults['search.query']}"/>" />
			       	</c:when>
			       	<c:otherwise>
				       	<input type="text" class="resultsPageSearch" name="q" value="(enter search here)" />
			       	</c:otherwise>
		       	</c:choose>
	 	        <input type="submit" class="resultPageSubmitButton" value="Search" />
		     </form>
	    </div>
       
        <hr style="clear:both;" class="searchResultsBar"/>

     	<!-- left bar -->
        <div class="leftBar">
			<c:if test="${! empty checkout['messages']}">
				<span style="color:red; font-weight:bold;">
					There's been an error processing your request:
					<c:out value="${checkout['messages']}"/>
				</span>
   			</c:if>
        </div>
        
        <!-- main content -->
        <div class="mainContent">
				Please confirm your order or use browser's back button to change items in cart:<br><br>

	        <form method="POST" action="confirm.htm">
		            <label>Full Name: </label><input type="text" size="30" name="name">
	                <label>Security Code: </label><input type="text" size="12" name="code"><br>
		            <label>Credit Card Number: </label><input type="text" name="number">
		            <label>Expiration Date (MM/YY): </label><input type="text" name="expiration" size="3"><br>
		        
		        <br><br><img src="images/accepted_cc.png">
	                <br><br><br>
		        Please enter the text on this image in the box below:<br><br>
		        <img src="captcha.jpg">
		        <br><input name="answer">
		        <br><br><br>
		        <input type="submit" value="Confirm" style="color: green; font-weight: bold; width: 100px; height: 30px;">
		        <input type="button" value="Cancel" onclick="history.back(1);" style="color: black; font-weight: bold; width: 100px; height: 30px;">
	        </form>
			<br/><br/>(By clicking the confirm button, your credit card will be charged)
	        <br>
        
        	<br>
			<table class="resultList">
				<th colspan="3" style="font-size: 1.1em; text-align: center; height: 50px; border-bottom: medium solid; vertical-align:middle;">
					Total charges: U$S  <fmt:formatNumber type="number" maxFractionDigits="2" value="${checkout['totalCharges']}" />					
				</th>
				</thead>
	        	<c:forEach var="rowData" items="${checkout['cartItems']}">
	        	<tr>	        	
				    <td class="cartSign" class="resultTitle">
				    	<img  id="cart_img_<c:out value="${rowData.id}"/>" alt="" src="images/remove_cart.png"/>
					</td>
					<td name="result" id="item_<c:out value="${rowData.id}"/>">
						<c:out value="${rowData.title}"/>
					</td>
					<td class="resultPrice">
						U$S <c:out value="${rowData.price}"/>
					</td>					
	        	</tr>
	        	</c:forEach>
			</table>  
        </div>

		<!-- right bar -->
        <div class="rightBar">		    
        </div>

        <hr style="clear:both;" class="searchResultsBar"/>

		<!-- footer -->
    	<div class="footer">
    	        <h1>Kstore - The place to buy</h1>
    	</div>
    </body>    
</html>