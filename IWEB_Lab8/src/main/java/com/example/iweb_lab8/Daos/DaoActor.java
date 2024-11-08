package com.example.iweb_lab8.Daos;

import com.example.iweb_lab8.Beans.Actor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoActor extends DaoBase {

    public List<Actor> obtenerActoresPorPelicula(int idPelicula) {
        List<Actor> actores = new ArrayList<>();
        String sql = "SELECT Actor.idActor, Actor.nombre, Actor.apellido, Actor.anoNacimiento, Actor.premioOscar " +
                "FROM Protagonistas " +
                "INNER JOIN Actor ON Protagonistas.idActor = Actor.idActor " +
                "WHERE Protagonistas.idPelicula = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idPelicula);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Actor actor = new Actor();
                actor.setIdActor(rs.getInt("idActor"));
                actor.setNombre(rs.getString("nombre"));
                actor.setApellido(rs.getString("apellido"));
                actor.setAnoNacimiento(rs.getInt("anoNacimiento"));
                actor.setPremioOscar(rs.getBoolean("premioOscar"));
                actores.add(actor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actores;
    }

    public void guardarActor(Actor actor, int idPelicula) {
        String insertActorSql = "INSERT INTO Actor (nombre, apellido, anoNacimiento, premioOscar) VALUES (?, ?, ?, ?)";
        String insertProtagonistaSql = "INSERT INTO Protagonistas (idPelicula, idActor) VALUES (?, ?)";

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement stmtActor = connection.prepareStatement(insertActorSql, Statement.RETURN_GENERATED_KEYS)) {
                stmtActor.setString(1, actor.getNombre());
                stmtActor.setString(2, actor.getApellido());
                stmtActor.setInt(3, actor.getAnoNacimiento());
                stmtActor.setBoolean(4, actor.isPremioOscar());
                stmtActor.executeUpdate();

                ResultSet generatedKeys = stmtActor.getGeneratedKeys();
                int idActor = 0;
                if (generatedKeys.next()) {
                    idActor = generatedKeys.getInt(1);
                }

                try (PreparedStatement stmtProtagonista = connection.prepareStatement(insertProtagonistaSql)) {
                    stmtProtagonista.setInt(1, idPelicula);
                    stmtProtagonista.setInt(2, idActor);
                    stmtProtagonista.executeUpdate();
                }

                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
