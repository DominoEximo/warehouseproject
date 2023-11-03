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

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    // doPost() method
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set the content type of response to "text/html"
        response.setContentType("text/html");

        // Get the print writer object to write into the response
        PrintWriter out = response.getWriter();

        // Get the session object
        HttpSession session = request.getSession();

        // Get User entered details from the request using request parameter.
        String user = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate the password - If password is correct,
        // set the user in this session
        // and redirect to welcome page
        if (password.equals("admin")) {
            session.setAttribute("user", user);
            response.sendRedirect("secured/profile.html?name=" + user);
        }
        // If the password is wrong, display the error message on the login page.
        else {
            RequestDispatcher rd = request.getRequestDispatcher("login.html");
            out.println("<font color=red>Password is wrong.</font>");
            rd.include(request, response);
        }
        // Close the print writer object.
        out.close();
    }
}
