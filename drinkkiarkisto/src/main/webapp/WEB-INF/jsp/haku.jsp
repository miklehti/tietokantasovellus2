<%-- 
    Document   : haku
    Created on : 23.7.2013, 15:08:51
    Author     : lehtimik
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Drinkkien haku</title>
    </head>
    <body>
        <h1>Drinkkiarkisto</h1>
        
      
        <c:url var="thisURL" value="http://localhost:8080/drinkkiarkisto/app/logout">   
        </c:url>
        
        <a href="<c:out value="${thisURL}"/>">logout</a>
        
        <c:url var="tulostus" value="http://localhost:8080/drinkkiarkisto/app/tulostus">   
        </c:url>
        <a href="<c:out value="${tulostus}"/>">tulostus</a>
        
        <c:url var="ehdota" value="http://localhost:8080/drinkkiarkisto/app/ehdota">   
        </c:url>
        <a href="<c:out value="${ehdota}"/>">Ehdota drinkkiä</a>

        <form method="POST" action="http://localhost:8080/drinkkiarkisto/app/hae">
            <label>Hae drinkkejä: <input type="text" name="hae" id="hae" /></label>
            <input type="submit" value ="Hae"/>
        </form>
 <form method="POST" action="http://localhost:8080/drinkkiarkisto/app/hae-tyyppi">
            <label>Hae tyypillä: <input type="text" name="hae-tyyppi" id="hae-tyyppi" /></label>
            <input type="submit" value ="Hae tyypillä"/>
        </form>
        
              <form method="POST" action="http://localhost:8080/drinkkiarkisto/app/hae-aakkoset">
            <label>Hae aakkosjärjestyksessä: </label>
            <input type="submit" value ="Hae aakkosilla"/>
        </form>



        <p>${sana}</P>
        <p>${eiLoydy}</P>
         <p>${eiLoydyAakkoset}</P>

        <pre>
            <c:forEach var="alkio" items="${osoitteita}">
    <a href="${alkio.value}">${alkio.key}</a>
            </c:forEach>
        </pre>
                <pre>
            <c:forEach var="alkio" items="${yllapitolinkki}">
    <a href="${alkio.value}">${alkio.key}</a>
            </c:forEach>
        </pre>
                 <pre>
            <c:forEach var="alkio" items="${luoDrinkki}">
    <a href="${alkio.value}">${alkio.key}</a>
            </c:forEach>
        </pre>
        

    </body>
</html>
