<%--
  Created by IntelliJ IDEA.
  User: GIANFRANCO
  Date: 7/11/2024
  Time: 00:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.example.iweb_lab8.Beans.Pelicula" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Películas</title>
</head>
<body>
<h1>Lista de películas</h1>

<form action="PeliculaServlet" method="get">
    <input type="text" name="buscar" placeholder="Buscar película..." value="<%= request.getParameter("buscar") %>">
    <button type="submit">Buscar</button>
</form>

<table border="1">
    <tr>
        <th>Título</th>
        <th>Director</th>
        <th>Año Publicación</th>
        <th>Rating</th>
        <th>BoxOffice</th>
        <th>Género</th>
        <th>Actores</th>
        <th>Accionable</th>
    </tr>

    <%
        List<Pelicula> peliculas = (List<Pelicula>) request.getAttribute("peliculas");
        if (peliculas != null) {
            for (Pelicula pelicula : peliculas) {
    %>
    <tr>
        <td><a href="DetallesServlet?id=<%= pelicula.getIdPelicula() %>"><%= pelicula.getTitulo() %></a></td>
        <td><%= pelicula.getDirector() %></td>
        <td><%= pelicula.getAnoPublicacion() %></td>
        <td><%= pelicula.getRating() %>/10</td>
        <td>$<%= pelicula.getBoxOffice() %></td>
        <td><%= pelicula.getGenero().getNombre() %></td>
        <td><a href="ActorServlet?id=<%= pelicula.getIdPelicula() %>">Ver actores</a></td>
        <td>
            <a href="javascript:void(0);" onclick="confirmDelete(<%= pelicula.getIdPelicula() %>)">Eliminar</a>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>

<script>
    function confirmDelete(id) {
        if (confirm("¿Estás seguro de eliminar esta película?")) {
            window.location.href = "PeliculaServlet?action=delete&id=" + id;
        }
    }
</script>
</body>
</html>
