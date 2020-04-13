package app.web.servlets;

import app.domain.models.binding.CarCreateBindingModel;
import app.domain.models.service.CarServiceModel;
import app.domain.models.view.CarViewModel;
import app.service.CarService;
import app.util.FileUtil;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/create")
public class CreateServlet extends HttpServlet {
    private static final String FILE_PATH = "C:\\Users\\User\\Desktop\\New folder\\src\\main\\webapp\\views\\create.html";

    private final FileUtil fileUtil;
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;
    private final CarService carService;


    @Inject
    public CreateServlet(FileUtil fileUtil,
                         EntityManager entityManager, ModelMapper modelMapper, CarService carService) {
        this.fileUtil = fileUtil;
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.carService = carService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        String html = this.fileUtil.readFile(FILE_PATH);
        writer.println(html);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CarCreateBindingModel carCreateBindingModel = new CarCreateBindingModel();
        carCreateBindingModel.setBrand(req.getParameter("brand"));
        carCreateBindingModel.setModel(req.getParameter("model"));
        carCreateBindingModel.setEngine(req.getParameter("engine"));
        carCreateBindingModel.setYear(Integer.parseInt(req.getParameter("year")));

        CarServiceModel carServiceModel = this.modelMapper.map(carCreateBindingModel, CarServiceModel.class);
        carService.createCar(carServiceModel);

        resp.sendRedirect("/all");
    }
}
