<%-- 
    Document   : kirjaudu
    Created on : 24.7.2013, 15:15:19
    Author     : lehtimik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Rekisteröidy käyttäjäksi</h1>
         <br>
        <form method="POST" action="http://localhost:8080/drinkkiarkisto/app/rekisteroidy">
            <label>Anna haluamasi käyttäjätunnus(5-15 merkkiä): <input type="text" name="name" id="name" value="${name}" /></label> <br>
             <br>
            <label>Anna salasanasi(5-15 merkkiä): <input type="password" name="password" id ="password"/></label> <br>
             <br>
            <label>Anna salasanasi uudestaan: <input type="password" name="password2" id ="password2"/></label> <br>
             <br>
            <label>Anna sähköpostisi (pakollinen): <input type="email" name="email" id ="email" value="${email}"/></label> <br>
             <br>
            <input type="submit" value ="Rekisteröidy" />
        </form>

        <p>${nameError}</P>
    </body>
</html>
