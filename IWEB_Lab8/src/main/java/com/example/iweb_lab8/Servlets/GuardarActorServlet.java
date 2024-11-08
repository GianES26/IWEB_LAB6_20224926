package com.example.iweb_lab8.Servlets;

import com.example.iweb_lab8.Beans.Actor;
import com.example.iweb_lab8.Daos.DaoActor;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "GuardarActorServlet", value = "/GuardarActorServlet")
public class GuardarActorServlet extends HttpServlet {
    private DaoActor actorDAO;

    @Override
    public void init() {
        actorDAO = new DaoActor();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idPelicula = Integer.parseInt(request.getParameter("idPelicula"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        int anoNacimiento = Integer.parseInt(request.getParameter("anoNacimiento"));
        boolean premioOscar = Boolean.parseBoolean(request.getParameter("premioOscar"));

        Actor actor = new Actor();
        actor.setNombre(nombre);
        actor.setApellido(apellido);
        actor.setAnoNacimiento(anoNacimiento);
        actor.setPremioOscar(premioOscar);

        actorDAO.guardarActor(actor, idPelicula);

        response.sendRedirect("ActorServlet?id=" + idPelicula);
    }
}
