<%-- 
    Document   : Login
    Created on : 23.7.2013, 15:08:14
    Author     : lehtimik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Drinkkiarkisto Login</title>
    </head>
   <body>
        <h1>Login</h1>
        <form method="POST" action="http://localhost:8080/drinkkiarkisto/app/login" />
  <label>Käyttäjätunnus: <input type="text" name="username" id="username" /></label>
  <label>Salasana: <input type="password" name="password" id ="password"/></label>
  <input type="submit" value ="Kirjaudu Sisään" />
  
  
 
</form>                
         <p>${nameError}</P>
        <c:url var="rekisteroidy" value="http://localhost:8080/drinkkiarkisto/app/rekisteroidy">   
        </c:url>
        <a href="<c:out value="${rekisteroidy}"/>">rekisteröidy</a>
    </body>
</html>