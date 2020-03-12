package ru.itis.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ru.itis.services.SignUpService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private EmailConfirmServlet emailConfirmServlet;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        ApplicationContext context = (ApplicationContext) servletContext.getAttribute("context");
        signUpService = context.getBean(SignUpService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("signUp.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> params = new LinkedHashMap<>();

        params.put("name", (String) req.getParameter("name"));
        params.put("email", (String) req.getParameter("email"));
        params.put("password", (String) req.getParameter("password"));
        signUpService.signUp(params);
    }
}
