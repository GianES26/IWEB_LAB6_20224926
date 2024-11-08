<%--
  Created by IntelliJ IDEA.
  User: GIANFRANCO
  Date: 7/11/2024
  Time: 01:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.example.iweb_lab8.Beans.Pelicula" %>
<%@ page import="com.example.iweb_lab8.Beans.Actor" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>
    <%= request.getAttribute("pelicula") != null ? ((Pelicula) request.getAttribute("pelicula")).getTitulo() : "Película no encontrada" %>
  </title>
</head>
<body>
<%
  Pelicula pelicula = (Pelicula) request.getAttribute("pelicula");
  if (pelicula != null) {
%>
<h1><%= pelicula.getTitulo() %></h1>

<table border="1">
  <tr>
    <th>idActor</th>
    <th>Nombre</th>
    <th>Apellido</th>
    <th>Año Nacimiento</th>
    <th>Ganador Premio Oscar</th>
  </tr>
  <%
    List<Actor> actores = (List<Actor>) request.getAttribute("actores");
    if (actores != null) {
      for (Actor actor : actores) {
  %>
  <tr>
    <td><%= actor.getIdActor() %></td>
    <td><%= actor.getNombre() %></td>
    <td><%= actor.getApellido() %></td>
    <td><%= actor.getAnoNacimiento() %></td>
    <td><%= actor.isPremioOscar() ? "Sí" : "No" %></td>
  </tr>
  <%
      }
    }
  %>
</table>

<form action="CrearActorServlet" method="get">
  <input type="hidden" name="idPelicula" value="<%= pelicula.getIdPelicula() %>">
  <button type="submit">Crear Actor</button>
</form>
<%
} else {
%>
<h1>Error: Película no encontrada</h1>
<p>No se pudo encontrar la película solicitada.</p>
<%
  }
%>
</body>
</html>
