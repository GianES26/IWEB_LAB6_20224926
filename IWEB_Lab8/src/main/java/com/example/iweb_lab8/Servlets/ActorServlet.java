package com.example.iweb_lab8.Servlets;

import com.example.iweb_lab8.Beans.Actor;
import com.example.iweb_lab8.Beans.Pelicula;
import com.example.iweb_lab8.Daos.DaoActor;
import com.example.iweb_lab8.Daos.DaoPelicula;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ActorServlet", value = "/ActorServlet")
public class ActorServlet extends HttpServlet {
   private DaoPelicula peliculaDAO;
   private DaoActor actorDAO;

   @Override
   public void init() {
      peliculaDAO = new DaoPelicula();
      actorDAO = new DaoActor();
   }

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try {
         int idPelicula = Integer.parseInt(request.getParameter("id"));

         Pelicula pelicula = peliculaDAO.obtenerPeliculaPorId(idPelicula);
         if (pelicula == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Película no encontrada");
            return;
         }

         List<Actor> actores = actorDAO.obtenerActoresPorPelicula(idPelicula);

         request.setAttribute("pelicula", pelicula);
         request.setAttribute("actores", actores);
         request.getRequestDispatcher("listaActores.jsp").forward(request, response);
      } catch (NumberFormatException e) {
         response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de película inválido");
      }
   }
}
