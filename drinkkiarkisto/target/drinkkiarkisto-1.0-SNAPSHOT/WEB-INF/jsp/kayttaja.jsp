<%-- 
    Document   : haku
    Created on : 23.7.2013, 15:08:51
    Author     : lehtimik
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>K�ytt�j�n tiedot</title>
    </head>
    <body>
        <h1>K�ytt�j�n tiedot</h1>
      
          <p>K�ytt�j�nimi: ${usernameKanta}</P>
          <p> K�ytt�j�n taso:${authorityKanta}</P>
          <p> K�ytt�j�n id:  ${idKanta}</P>
          <p>K�ytt�j�n status: ${statusKanta}</P>
          <p>K�ytt�j�n saloasana: ${passwordKanta}</P>
          <p>K�ytt�j�n email: ${emailKanta}</P>
         <c:url var="logout" value="http://localhost:8080/drinkkiarkisto/app/logout">   
        </c:url>
        <a href="<c:out value="${logout}"/>">logout</a>
        
         <form method="POST" action="http://localhost:8080/drinkkiarkisto/app/poista-kayttaja">        
            <input type="submit" value ="Deletoi k�ytt�j�"/>
        </form>  
        <P>${nameError}</P>
        
          <form method="POST" action="http://localhost:8080/drinkkiarkisto/app/ylenna-kayttaja">        
            <input type="submit" value ="Ylenn� k�ytt�j� super useriksi (voi luoda drinkkej�)"/>
        </form> 
         <P>${nameError}</P>
          <c:url var="takaisin" value="http://localhost:8080/drinkkiarkisto/app/admin">   
        </c:url>
        <a href="<c:out value="${takaisin}"/>"><-takaisin admin sivulle</a>
    </body>
</html>

