<%-- 
    Document   : haku
    Created on : 23.7.2013, 15:08:51
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
        <title>Ehdotettu drinkki</title>
    </head>
    <body>
        <h1>Ehdotetun drinkin resepti, voit tehdä muutoksia ja hyväksyä/hylkää. Muista antaa tyyppi.</h1>
         <c:url var="logout" value="http://localhost:8080/drinkkiarkisto/app/logout">   
        </c:url>
        <a href="<c:out value="${logout}"/>">logout</a>
   
        <form>
            <label>Ehdotuksen nimi (pakollinen): <input type="text" name="drinkki_name" id="drinkki_name" value="${drinkki_name}"/></label><br>
            <br>
            <br>
            <label>Drinkkisi tyyppi (cocktail, alkujuoma...): <input type="text" name="tyyppi_name" id="tyyppi_name" value="${tyyppi_name}" /></label><br>
            <br>
            <label>Pääainesosa (pakollinen):<input type="text" name="ainesosa_name" id ="ainesosa_name" value="${ainesosa_name}"/></label><br>
            <label>määrä: <input type="text" name="maara" id ="maara" value="${maara}"/></label><br>
            <br>
            <label>Ainesosa 2:<input type="text" name="ainesosa2" id ="ainesosa2" value="${ainesosa2}"/></label><br>
            <label>määrä: <input type="text" name="maara2" id ="maara2" value="${maara2}"/></label><br>
            <br>
            <label>Ainesosa 3:<input type="text" name="ainesosa3" id ="ainesosa3" value="${ainesosa3}"/></label><br>
            <label>määrä: <input type="text" name="maara3" id ="maara3" value="${maara3}"/></label><br>
            <br>
            <label>Ainesosa 4:<input type="text" name="ainesosa4" id ="ainesosa4" value="${ainesosa4}"/></label><br>
            <label>määrä: <input type="text" name="maara4" id ="maara4" value="${maara4}"/></label><br>
            <br>
            <label>Ainesosa 5:<input type="text" name="ainesosa5" id ="ainesosa5" value="${ainesosa5}"/></label><br>
            <label>määrä: <input type="text" name="maara5" id ="maara5" value="${maara5}"/></label><br>
            <br>
            <input type="submit" value ="Hyväksy" method ="POST" action ="http://localhost:8080/drinkkiarkisto/app/luo-drinkki-ehdotuksesta"/>
            <input type="submit" value ="Hylkää" method ="POST" action ="${poista}"/>
        </form>
        
              <p>${nameError}</P>
                  <p>${onnistunutViesti}</P>
            
      
            
              <c:url var="takaisin" value="http://localhost:8080/drinkkiarkisto/app/admin">   
        </c:url>
        <a href="<c:out value="${takaisin}"/>"><-takaisin</a>
        
    </body>
</html>
