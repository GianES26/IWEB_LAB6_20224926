package com.example.iweb_lab8.Servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "CrearActorServlet", value = "/CrearActorServlet")
public class CrearActorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idPelicula = Integer.parseInt(request.getParameter("idPelicula"));

        request.setAttribute("idPelicula", idPelicula);
        request.getRequestDispatcher("crearActor.jsp").forward(request, response);
    }
}
