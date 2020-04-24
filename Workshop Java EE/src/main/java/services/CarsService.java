package services;

import entities.service.CarServiceModel;

import java.util.List;

public interface CarsService {

    void createCar(String brand, String model, Integer year, String engine, String username) throws Exception;

    List<CarServiceModel> getAll();
}
