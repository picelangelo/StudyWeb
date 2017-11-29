package at.pichlerlehner.studyweb.presentation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BaseServlet extends HttpServlet{
    @Override
    public abstract void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    @Override
    public abstract void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
