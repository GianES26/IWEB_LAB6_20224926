<%--
  Created by IntelliJ IDEA.
  User: GIANFRANCO
  Date: 7/11/2024
  Time: 01:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Crear Actor</title>
</head>
<body>
<h1>Crear Actor para Película ID: <%= request.getAttribute("idPelicula") %></h1>

<form action="GuardarActorServlet" method="post">
    <input type="hidden" name="idPelicula" value="<%= request.getAttribute("idPelicula") %>">

    <label>Nombre: <input type="text" name="nombre" required></label><br>
    <label>Apellido: <input type="text" name="apellido" required></label><br>
    <label>Año de Nacimiento: <input type="number" name="anoNacimiento" required></label><br>
    <label>Premio Oscar:
        <select name="premioOscar">
            <option value="true">Sí</option>
            <option value="false">No</option>
        </select>
    </label><br>

    <button type="submit">Guardar Actor</button>
</form>

</body>
</html>
