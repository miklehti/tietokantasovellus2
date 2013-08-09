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
        <title>Käyttäjän tiedot</title>
    </head>
    <body>
        <h1>Käyttäjän tiedot</h1>
      
          <p>Käyttäjänimi: ${usernameKanta}</P>
          <p> Käyttäjän taso:${authorityKanta}</P>
          <p> Käyttäjän id:  ${idKanta}</P>
          <p>Käyttäjän status: ${statusKanta}</P>
          <p>Käyttäjän saloasana: ${passwordKanta}</P>
          <p>Käyttäjän email: ${emailKanta}</P>
         <c:url var="logout" value="http://localhost:8080/drinkkiarkisto/app/logout">   
        </c:url>
        <a href="<c:out value="${logout}"/>">logout</a>
        
         <form method="POST" action="http://localhost:8080/drinkkiarkisto/app/poista-kayttaja">        
            <input type="submit" value ="Deletoi käyttäjä"/>
        </form>  
        <P>${nameError}</P>
        
          <form method="POST" action="http://localhost:8080/drinkkiarkisto/app/ylenna-kayttaja">        
            <input type="submit" value ="Ylennä käyttäjä super useriksi (voi luoda drinkkejä)"/>
        </form> 
         <P>${nameError}</P>
          <c:url var="takaisin" value="http://localhost:8080/drinkkiarkisto/app/admin">   
        </c:url>
        <a href="<c:out value="${takaisin}"/>"><-takaisin admin sivulle</a>
    </body>
</html>

