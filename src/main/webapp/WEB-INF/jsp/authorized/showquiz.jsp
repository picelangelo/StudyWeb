<%@ page import="at.pichlerlehner.studyweb.domain.Benutzer" %>
<%@ page import="at.pichlerlehner.studyweb.service.FragebogenService" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Fragebogen" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Rechte" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Benutzer benutzer = (Benutzer) request.getSession().getAttribute("USER");

    FragebogenService fragebogenService = new FragebogenService();
    List<Fragebogen> fragebogenList = fragebogenService.findQuizByUserAccess(benutzer);
%>
<html>
<head>
    <title>StudyWeb | Show</title>
    <link rel="shortcut icon" type="image/x-icon" href="../../../images/logo.PNG">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../../../css/materialize.css">
    <link rel="stylesheet" type="text/css" href="../../../css/index.css">
</head>
<body>
<div class="container">
    <h4 class="indigo-text">
        Welcome to Studyweb, <%= benutzer.getVorname()%>
    </h4>
    <table>
        <tr>
            <th>Definition</th>
            <th>Creator</th>
        </tr>
        <% for (Fragebogen fragebogen : fragebogenList) { %>
        <tr>
            <td><a href=<%="do?quiz=" + fragebogen.getPrimaryKey()%>>
                <%=fragebogen.getBezeichnung()%>
            </a>
            </td>
            <td><%=fragebogen.getErsteller().getVorname()%> <%=fragebogen.getErsteller().getNachname()%>
            </td>
        </tr>
        <% } %>
    </table>

</div>
</body>
</html>
