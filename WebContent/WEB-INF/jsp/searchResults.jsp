<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<html>
    <head>
        <title>Kstore</title>
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="Stylesheet" />	
		<script type="text/javascript" src="scripts/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="scripts/jquery-ui-1.8.2.custom.min.js"></script>
        <link rel="stylesheet" href="css/main.css" />        
        <link rel="shortcut icon" href="css/favicon.ico" type="image/x-icon" />        
    </head>

	<script type="text/javascript">
		var titles = [];
	</script>

    <body onload="paintItemsInCart();">
   		<!-- top bar -->
        <div class="topBar">
	       	<a href="index.jsp"><img style="float:left; margin-left:15px;" src="images/kstore-logo-mini.png" alt="Kstore" border="0" /></a>
	        
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

		<!-- left left bar -->
        <div class="leftBar">
		    <p style="text-align: center; margin-top: 20px;">Sponsored Ads</p>
		    <br>
		     <c:forEach var="rowData" items="${searchResults['ads']}">
			    <div class="ads">
			        <span style="font-weight:bold; font-size:1.2em;"><c:out value="${rowData.result.title}"/></span><br/>
			        <span><c:out value="${rowData.result.subtitle}"/></span><br/>
			        <a style="color:green; font-weight: normal;" href="<c:out value="${rowData.result.link}"/>"><c:out value="${rowData.result.linkTitle}"/></a>
			    </div>
		     </c:forEach>
        </div>

        <!-- main content -->
        <div class="mainContent">
        Showing results <c:out value="${searchResults['results.start']}"/> to <c:out value="${searchResults['results.end']}"/>:
        <br/>
			<table class="resultList">
	        	<c:forEach var="rowData" items="${searchResults['results']}">
	        	<tr>	        	
				    <td class="cartSign" class="resultTitle" onclick="javascript:cartToggle('<c:out value="${rowData.result.site.siteID}"/>','<c:out value="${rowData.result.id}"/>')">
				    	<img  id="cart_img_<c:out value="${rowData.result.id}"/>" alt="" src="images/add_cart.jpg"/>
					</td>
					<td name="result" id="item_<c:out value="${rowData.result.id}"/>">
						<c:out value="${rowData.result.title}"/>
					</td>
					<td class="resultPrice">
						U$S <c:out value="${rowData.result.price}"/>
					</td>					
	        	</tr>
	        	</c:forEach>
			</table>  
        </div>

		<!-- rightBar -->
        <div class="rightBar">
	        <p style="text-align: center;">
			       	<a href="checkout.htm">
			        	<img id="img_checkout" src="images/checkout_disabled.png" border="0" onclick="javascript:checkout()"
			        		style="display: inline;"/>
		        	</a>
					<img id="img_clearcart" src="images/clear_cart.png" border="0" onclick="javascript:clearCart()" style="display: inline;"/>
	        </p>
			<h1 style="text-align: center;">Shopping Cart</h1>
			<ul id="cart" class="cart"></ul>
        </div>

		<div style="clear:both;">
			<c:if test="${searchResults['search.more_pages'] eq 'true'}">
				<br/>
					<p style="text-align: center">		
					<c:if test="${(searchResults['results.start'] != '1')}">
						<a href="query.htm?q=<c:out value="${searchResults['search.query']}"/>&page=<c:out value="${searchResults['search.next_page']-2}"/>&pageSize=<c:out value="${searchResults['search.page_size']}"/>">&lt; Previous Results</a>
						&nbsp; - &nbsp;
					</c:if>				
						<a href="query.htm?q=<c:out value="${searchResults['search.query']}"/>&page=<c:out value="${searchResults['search.next_page']}"/>&pageSize=<c:out value="${searchResults['search.page_size']}"/>">Next Results &gt;</a>
					</p>
				<br/>
			</c:if>
		</div>

        <hr style="clear:both;" class="searchResultsBar"/>

		<!-- footer -->
    	<div class="footer">
    	        <h1>Kstore - The place to buy</h1>
    	</div>
    </body>
    
  	<script type="text/javascript" src="scripts/search_scripts.js"/></script>
    
    <script type="text/javascript">
		<c:forEach var="rowData" items="${searchResults['cartItemsAtLoad']}">
	        titles[<c:out value="${rowData.id}"/>]="<c:out value="${rowData.title}"/>"
		</c:forEach>
    </script>
    
</html>