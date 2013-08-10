<%-- 
    Document   : admin
    Created on : 25.7.2013, 12:40:02
    Author     : lehtimik
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Admin sivu</title>
    </head>
    <body>
        <h1>Admin sivu</h1>
        <c:url var="logout" value="http://localhost:8080/drinkkiarkisto/app/logout">   
        </c:url>

        <a href="<c:out value="${logout}"/>">logout</a>

        
        <c:url var="luoDrinkki" value="http://localhost:8080/drinkkiarkisto/app/luo-drinkki">   
        </c:url>

        <a href="<c:out value="${luoDrinkki}"/>">Luo Drinkki</a>
           

        <form method="POST" action="http://localhost:8080/drinkkiarkisto/app/hae-admin">
            <label>Hae drinkkej�: <input type="text" name="hae-admin" id="hae-admin" /></label>
            <input type="submit" value ="Hae drinkkej�"/>
        </form>

    
        <form method="POST" action="http://localhost:8080/drinkkiarkisto/app/hae-tyyppi-admin">
            <label>Hae tyypill�: <input type="text" name="hae-tyyppi" id="hae-tyyppi" /></label>
            <input type="submit" value ="Hae tyypill�"/>
        </form>

        <form method="POST" action="http://localhost:8080/drinkkiarkisto/app/hae-aakkoset-admin">
            <label>Hae aakkosj�rjestyksess�: </label>
            <input type="submit" value ="Hae aakkosilla"/>
        </form>

      
 <p>Drinkkej� haun tuloksella (muutettavaksi):</P>

      <p>${eiLoydy}</P>

        <pre>
            <c:forEach var="alkio" items="${admin_osoitteita}">
    <a href="${alkio.value}">${alkio.key}</a>
            </c:forEach>
        </pre>
        
        
            <form method="POST" action="http://localhost:8080/drinkkiarkisto/app/hae-ehdotuksia-admin">
            <input type="submit" value ="Hae ehdotuksia"/>
        </form>
  <p>Ehdotuksia hyv�ksytt�v�ksi:</P>
  
  <p>${eiEhdotuksia}</P>
        <pre>
            <c:forEach var="ehdotus" items="${ehdotuksia}">
    <a href="${ehdotus.value}">${ehdotus.key}</a>
            </c:forEach>
        </pre>

        

        <form method="POST" action="http://localhost:8080/drinkkiarkisto/app/hae-kayttaja">
            <label>Hae kaikki k�ytt�j�t: </label>
            <input type="submit" value ="Hae"/>
        </form>  
        
  <p>K�ytt�j�t:</P>
<pre>
            <c:forEach var="kayttaja" items="${kayttajia}">
    <a href="${kayttaja.value}">${kayttaja.key}</a>
            </c:forEach>
        </pre>
  
    <c:url var="takaisin" value="http://localhost:8080/drinkkiarkisto/app/haku">   
        </c:url>
        <a href="<c:out value="${takaisin}"/>"><-takaisin hakusivulle</a>
<p>${onnistunutViesti}</P>
            
    </body>
</html>
