<%-- 
    Document   : haku
    Created on : 23.7.2013, 15:08:51
    Author     : lehtimik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Drinkin resepti</h1>
        
         <p>${drinkinNimi}</P>
         <c:url var="logout" value="http://localhost:8080/drinkkiarkisto/app/logout">   
        </c:url>
        <a href="<c:out value="${logout}"/>">logout</a>
         <p>${sana}</P>
        <pre>
            <c:forEach var="alkio" items="${ainesosa}">
      <p>Ainesosa: ${alkio.key}  Määrä: ${alkio.value}</P> 
            </c:forEach>
        </pre>
        
          <c:url var="takaisin" value="http://localhost:8080/drinkkiarkisto/app/haku">   
        </c:url>
        <a href="<c:out value="${takaisin}"/>"><-takaisin hakusivulle</a>
        
     <pre>
            <c:forEach var="alkio" items="${admin}">
    <a href="${alkio.value}">${alkio.key}</a>
            </c:forEach>
        </pre>
    </body>
</html>
