package com.example.iweb_lab8.Servlets;

import com.example.iweb_lab8.Beans.Pelicula;
import com.example.iweb_lab8.Daos.DaoPelicula;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "DetallesServlet", value = "/DetallesServlet")
public class DetallesServlet extends HttpServlet {
    private DaoPelicula peliculaDAO;

    @Override
    public void init() {
        peliculaDAO = new DaoPelicula();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idPelicula = Integer.parseInt(request.getParameter("id"));

            Pelicula pelicula = peliculaDAO.obtenerPeliculaPorId(idPelicula);

            if (pelicula != null) {
                request.setAttribute("pelicula", pelicula);
                request.getRequestDispatcher("viewPelicula.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Película no encontrada");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de película inválido");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idPelicula = Integer.parseInt(request.getParameter("idPelicula"));
        String titulo = request.getParameter("titulo");
        String director = request.getParameter("director");
        int anoPublicacion = Integer.parseInt(request.getParameter("anoPublicacion"));
        double rating = Double.parseDouble(request.getParameter("rating"));
        double boxOffice = Double.parseDouble(request.getParameter("boxOffice"));

        Pelicula pelicula = new Pelicula();
        pelicula.setIdPelicula(idPelicula);
        pelicula.setTitulo(titulo);
        pelicula.setDirector(director);
        pelicula.setAnoPublicacion(anoPublicacion);
        pelicula.setRating(rating);
        pelicula.setBoxOffice(boxOffice);

        peliculaDAO.actualizarPelicula(pelicula);

        response.sendRedirect("PeliculaServlet");
    }
}
