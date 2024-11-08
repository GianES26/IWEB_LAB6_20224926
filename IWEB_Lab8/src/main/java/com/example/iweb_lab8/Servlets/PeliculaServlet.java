package com.example.iweb_lab8.Servlets;

import com.example.iweb_lab8.Daos.DaoPelicula;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "PeliculaServlet", value = "/PeliculaServlet")
public class PeliculaServlet extends HttpServlet {
    private DaoPelicula peliculaDAO;

    @Override
    public void init() {
        peliculaDAO = new DaoPelicula();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            eliminarPelicula(request, response);
        } else {
            listarPeliculas(request, response);
        }
    }

    private void listarPeliculas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String criterioBusqueda = request.getParameter("buscar");
        var peliculas = peliculaDAO.obtenerPeliculasOrdenadas(criterioBusqueda);

        request.setAttribute("peliculas", peliculas);
        request.getRequestDispatcher("listaPeliculas.jsp").forward(request, response);
    }

    private void eliminarPelicula(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int idPelicula = Integer.parseInt(request.getParameter("id"));
            peliculaDAO.eliminarPelicula(idPelicula);
            response.sendRedirect("PeliculaServlet"); // Redirigir a la lista después de eliminar
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de película inválido");
        }
    }
}
