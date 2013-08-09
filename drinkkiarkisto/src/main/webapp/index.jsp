<%-- 
    Document   : Login
    Created on : 23.7.2013, 15:08:14
    Author     : lehtimik
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Drinkkiarkisto Login</title>
    </head>
   <body>
        <h1>Login</h1>
        <form method="POST" action="http://localhost:8080/authentication/app/login">
  <label>Käyttäjätunnus: <input type="text" name="username" id="username" /></label>
  <label>Salasana: <input type="password" name="password" id ="password"/></label>
  <input type="submit" />
</form>
    </body>
</html>
