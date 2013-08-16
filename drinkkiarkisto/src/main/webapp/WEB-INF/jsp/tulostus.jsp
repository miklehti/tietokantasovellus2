<%-- 
    Document   : tulostus
    Created on : 31.7.2013, 10:54:49
    Author     : lehtimik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Tietokannan tietoja</h1>
        
        <P>Drinkki:<P></br>
   
            <c:forEach var="alkio" items="${drinkki_id}">
                <p>numero: ${alkio.key}  arvo: ${alkio.value}</p>
            </c:forEach>
   
            
        
            <c:forEach var="alkio2" items="${drinkki_name}">
     <p>numero: ${alkio2.key} arvo: ${alkio2.value}</p>
            </c:forEach>

            
            
        
         <P>DrinkkiAinesosa:<P></br>
        
            <c:forEach var="alkio4" items="${drinkkiAinesosa_maara}">
    <p>numero: ${alkio4.key} arvo:${alkio4.value}</p>
            </c:forEach>
    
         
                 <P>Ainesosa:<P></br>
            
            <c:forEach var="alkio5" items="${ainesosa_id}">
    <p>Numero: ${alkio5.key} arvo: ${alkio5.value}</p>
            </c:forEach>
       
             
            <c:forEach var="alkio6" items="${ainesosa_name}">
    <p>Numero: ${alkio6.key} arvo: ${alkio6.value}</p>
            </c:forEach>
    
              <P>Tyyppi:<P></br>
            
            <c:forEach var="alkio7" items="${tyyppi_id}">
    <p>numero: ${alkio7.key} arvo: ${alkio7.value}</p>
            </c:forEach>
       
             
            <c:forEach var="alkio8" items="${tyyppi_name}">
    <p>Numero: ${alkio8.key} arvo: ${alkio8.value}</p>
            </c:forEach>
       
    </body>
</html>
