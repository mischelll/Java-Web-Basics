package app.web.servlets;

import app.domain.models.view.CarViewModel;
import app.service.CarService;
import app.util.FileUtil;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/all")
public class AllServlet extends HttpServlet {
    private static final String FILE_PATH = "C:\\Users\\User\\Desktop\\New folder\\src\\main\\webapp\\views\\all.html";

    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final CarService carService;

    @Inject
    public AllServlet(FileUtil fileUtil, ModelMapper modelMapper, CarService carService) {
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.carService = carService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        String html = this.fileUtil.readFile(FILE_PATH);

        StringBuilder stringBuilder = new StringBuilder();
        List<CarViewModel> carViewModels = this.carService.viewCars();
        carViewModels.forEach(car -> {
            stringBuilder.append(String.format("<li class=\"d-flex justify-content-around\">\n" +
                            "<div class=\"col-md-4 d-flex flex-column text-center mb-3\">\n" +
                            "<h2 class=\"text-white text-center\">Brand: %s</h2>\n" +
                            "<h4 class=\"text-white text-center\">Model: %s</h4>\n" +
                            "<h4 class=\"text-white text-center\">Year: %d</h4>\n" +
                            "<h4 class=\"text-white text-center\">Engine: %s</h4>\n" +
                            "</div>\n" +
                            "</li>"
                    , car.getBrand()
                    , car.getModel()
                    , car.getYear()
                    , car.getEngine()));
        });
        html = html.replace("{{replace}}", stringBuilder.toString());
        writer.println(html);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
