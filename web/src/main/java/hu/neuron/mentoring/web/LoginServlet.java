package hu.neuron.mentoring.web;

import hu.neuron.mentoring.client_api.Product;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

            List<Product> mockedData = new ArrayList<>();

            mockedData.add(new Product("Alma","Gyümölcs",3,"kg",new BigDecimal(100),new BigDecimal(200),"Magyar alma"));
            mockedData.add(new Product("Körte","Gyümölcs",5,"kg",new BigDecimal(40),new BigDecimal(300),"Lengyel körte"));
            mockedData.add(new Product("Barack","Gyümölcs",2,"kg",new BigDecimal(50),new BigDecimal(120),"Holland barack"));
            mockedData.add(new Product("Csirke","Hús",10,"kg",new BigDecimal(25),new BigDecimal(30),"Magyar csirke"));
            mockedData.add(new Product("Sertés","Hús",15,"kg",new BigDecimal(30),new BigDecimal(110),"Magyar sertés"));

            session.setAttribute("products",mockedData);
            session.setAttribute("username", user);

            response.sendRedirect("/secured/profile.html");
        }
        else {
            request.setAttribute("errorMessage","Érvénytelen belépési adatok!");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request,response);
        }
    }
}
