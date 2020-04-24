package web.servlets;

import services.CarsService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/create")
public class CarCreateServlet extends HttpServlet {
    private final CarsService carsService;

    @Inject
    public CarCreateServlet(CarsService carsService) {
        this.carsService = carsService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/cars-create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        Integer year = Integer.parseInt(req.getParameter("year"));
        String engine = req.getParameter("engine");

        String username = req.getSession().getAttribute("username").toString();

        try {
            this.carsService.createCar(brand, model, year, engine, username);
            resp.sendRedirect("/home");
        } catch (Exception e) {
            resp.sendRedirect("/cars/create");
        }
    }
}
