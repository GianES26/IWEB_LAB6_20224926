<%--
  Created by IntelliJ IDEA.
  User: GIANFRANCO
  Date: 7/11/2024
  Time: 00:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.example.iweb_lab8.Beans.Pelicula" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Película Numero <%= request.getAttribute("pelicula") != null ? ((Pelicula) request.getAttribute("pelicula")).getIdPelicula() : "Desconocido" %></title>
</head>
<body>
<%
  Pelicula pelicula = (Pelicula) request.getAttribute("pelicula");
  if (pelicula != null) {
%>
<h1>Película Numero <%= pelicula.getIdPelicula() %></h1>

<form action="DetallesServlet" method="post">
  <input type="hidden" name="idPelicula" value="<%= pelicula.getIdPelicula() %>">

  <table border="1">
    <tr>
      <th>Título</th>
      <td><input type="text" name="titulo" value="<%= pelicula.getTitulo() %>"></td>
    </tr>
    <tr>
      <th>Director</th>
      <td><input type="text" name="director" value="<%= pelicula.getDirector() %>"></td>
    </tr>
    <tr>
      <th>Año Publicación</th>
      <td><input type="text" name="anoPublicacion" value="<%= pelicula.getAnoPublicacion() %>"></td>
    </tr>
    <tr>
      <th>Rating</th>
      <td><input type="text" name="rating" value="<%= pelicula.getRating() %>"></td>
    </tr>
    <tr>
      <th>BoxOffice</th>
      <td><input type="text" name="boxOffice" value="<%= pelicula.getBoxOffice() %>"></td>
    </tr>
    <tr>
      <th>Actores</th>
      <td><a href="ActorServlet?id=<%= pelicula.getIdPelicula() %>">Ver actores</a></td>
    </tr>
  </table>
  <button type="submit">Guardar Película</button>
</form>
<%
} else {
%>
<h1>Error: Película no encontrada</h1>
<p>La película solicitada no existe.</p>
<%
  }
%>
</body>
</html>
