package app.service;

import app.domain.models.service.CarServiceModel;
import app.domain.models.view.CarViewModel;

import java.util.List;

public interface CarService {

    void createCar(CarServiceModel car);

    List<CarViewModel> viewCars();
}
