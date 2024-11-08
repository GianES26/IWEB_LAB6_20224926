package com.example.iweb_lab8.Daos;

import com.example.iweb_lab8.Beans.Pelicula;
import com.example.iweb_lab8.Beans.Genero;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoPelicula extends DaoBase {

    public List<Pelicula> obtenerPeliculasOrdenadas(String criterioBusqueda) {
        List<Pelicula> peliculas = new ArrayList<>();
        String sql = "SELECT Pelicula.*, Genero.idGenero, Genero.nombre AS generoNombre " +
                "FROM Pelicula " +
                "INNER JOIN Genero ON Pelicula.idGenero = Genero.idGenero " +
                (criterioBusqueda != null && !criterioBusqueda.isEmpty() ? "WHERE titulo LIKE ? " : "") +
                "ORDER BY rating DESC, boxOffice DESC";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            if (criterioBusqueda != null && !criterioBusqueda.isEmpty()) {
                stmt.setString(1, "%" + criterioBusqueda + "%");
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pelicula pelicula = new Pelicula();
                pelicula.setIdPelicula(rs.getInt("idPelicula"));
                pelicula.setTitulo(rs.getString("titulo"));
                pelicula.setDirector(rs.getString("director"));
                pelicula.setAnoPublicacion(rs.getInt("anoPublicacion"));
                pelicula.setRating(rs.getDouble("rating"));
                pelicula.setBoxOffice(rs.getDouble("boxOffice"));

                //Modifiqué el beans Usuario para que soporte beans Genero y así
                //realizar modificaciones más efectivas en base a los queries hechos.
                Genero genero = new Genero();
                genero.setIdGenero(rs.getInt("idGenero"));
                genero.setNombre(rs.getString("generoNombre"));
                pelicula.setGenero(genero);

                peliculas.add(pelicula);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peliculas;
    }

    public void eliminarPelicula(int idPelicula) {
        //Para evitar problemas con los queries, primero eliminaremos de la tabla protagonistas
        //a todos los elementos que estén relacionados con la pelicula que deseamos borrar.
        //De esta forma evitaremos cualquier error posible.
        String deleteProtagonistasSql = "DELETE FROM Protagonistas WHERE idPelicula = ?";
        String deletePeliculaSql = "DELETE FROM Pelicula WHERE idPelicula = ?";

        try (Connection connection = getConnection()) {
            try (PreparedStatement stmtProtagonistas = connection.prepareStatement(deleteProtagonistasSql)) {
                stmtProtagonistas.setInt(1, idPelicula);
                stmtProtagonistas.executeUpdate();
            }

            try (PreparedStatement stmtPelicula = connection.prepareStatement(deletePeliculaSql)) {
                stmtPelicula.setInt(1, idPelicula);
                stmtPelicula.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Pelicula obtenerPeliculaPorId(int idPelicula) {
        Pelicula pelicula = null;
        String sql = "SELECT Pelicula.*, Genero.nombre AS generoNombre " +
                "FROM Pelicula " +
                "INNER JOIN Genero ON Pelicula.idGenero = Genero.idGenero " +
                "WHERE Pelicula.idPelicula = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idPelicula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                pelicula = new Pelicula();
                pelicula.setIdPelicula(rs.getInt("idPelicula"));
                pelicula.setTitulo(rs.getString("titulo"));
                pelicula.setDirector(rs.getString("director"));
                pelicula.setAnoPublicacion(rs.getInt("anoPublicacion"));
                pelicula.setRating(rs.getDouble("rating"));
                pelicula.setBoxOffice(rs.getDouble("boxOffice"));

                Genero genero = new Genero();
                genero.setIdGenero(rs.getInt("idGenero"));
                genero.setNombre(rs.getString("generoNombre"));
                pelicula.setGenero(genero);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pelicula;
    }

    public void actualizarPelicula(Pelicula pelicula) {
        String sql = "UPDATE Pelicula SET titulo = ?, director = ?, anoPublicacion = ?, rating = ?, boxOffice = ? WHERE idPelicula = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, pelicula.getTitulo());
            stmt.setString(2, pelicula.getDirector());
            stmt.setInt(3, pelicula.getAnoPublicacion());
            stmt.setDouble(4, pelicula.getRating());
            stmt.setDouble(5, pelicula.getBoxOffice());
            stmt.setInt(6, pelicula.getIdPelicula());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
