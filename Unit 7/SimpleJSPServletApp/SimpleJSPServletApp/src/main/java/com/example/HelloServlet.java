package com.example;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {

    public void init() {
        System.out.println("Servlet Initialized");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>Hello from Servlet (GET Request)</h1>");
        out.println("<p>Servlet Lifecycle Example</p>");
        out.println("<br><a href='index.jsp'>Back to Home</a>");
    }

    public void destroy() {
        System.out.println("Servlet Destroyed");
    }
}
