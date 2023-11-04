package hu.neuron.mentoring.web;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet(urlPatterns = "loginAsync")
public class LoginServletAsync extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        boolean result;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String user = req.getParameter("username");
        String password = req.getParameter("password");
        JsonObject json = new JsonObject();
        json.addProperty("username",user);
        json.addProperty("password",password);

        if (password.equals("password") && user.equals("admin")) {
            result = true;
            json.addProperty("result",result);
            String resultJson = gson.toJson(json);
            PrintWriter out = resp.getWriter();
            resp.setStatus(200);
            resp.setCharacterEncoding("UTF-8");
            out.write(resultJson);
            out.flush();
        }
        else {
            result = false;
            json.addProperty("result",result);
            String resultJson = gson.toJson(json);
            PrintWriter out = resp.getWriter();
            resp.setStatus(400);
            resp.setCharacterEncoding("UTF-8");
            out.write(resultJson);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("username");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();

        session.setAttribute("authenticated", true);
        session.setAttribute("username", user);
        session.setAttribute("password",password);

        resp.sendRedirect("/secured/profile.html");


    }
}
