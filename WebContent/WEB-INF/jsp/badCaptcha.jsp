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

        </div>
        
        <!-- main content -->
        <div class="mainContent">
        	The text you entered didn't matched the text in the image, please try again.
        	<input type="button" onclick="history.back(1);" value="Back"/> 
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