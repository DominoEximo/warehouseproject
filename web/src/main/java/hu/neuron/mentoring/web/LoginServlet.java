package hu.neuron.mentoring.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    // doPost() method
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String user = request.getParameter("username");
        String password = request.getParameter("password");

        if (password.equals("password") && user.equals("admin")) {
            HttpSession session = request.getSession();
            session.setAttribute("authenticated", true);
            session.setAttribute("username", user);
            response.sendRedirect("secured/profile.html");
        }
        else {
            response.sendRedirect("login.html?error=true");
        }
    }
}
