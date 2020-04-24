package services.impl;

import entities.Car;
import entities.Engine;
import entities.User;
import entities.service.CarServiceModel;
import org.modelmapper.ModelMapper;
import services.CarsService;
import services.CarsValidationService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class CarsServiceImpl implements CarsService {
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;
    private final CarsValidationService carsValidationService;

    @Inject
    public CarsServiceImpl(EntityManager entityManager, ModelMapper modelMapper, CarsValidationService carsValidationService) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.carsValidationService = carsValidationService;
    }

    @Override
    public void createCar(String brand, String model, Integer year, String engine, String username) throws Exception {
        if (!this.carsValidationService.isCarValid(brand, model, year, engine)) {
            throw new Exception("Cannot create car!");
        }

        Car car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year);
        car.setEngine(Engine.valueOf(engine));
        car.setUser(getLoggedUser(username));

        this.entityManager.getTransaction().begin();
        this.entityManager.persist(car);
        this.entityManager.getTransaction().commit();

    }

    private User getLoggedUser(String username) {
        User username1 = this.entityManager
                .createQuery("select  u from User u where u.username = :username ", User.class)
                .setParameter("username", username)
                .getSingleResult();

        return username1;
    }

    @Override
    public List<CarServiceModel> getAll() {
        return entityManager.createQuery("select c FROM Car c", Car.class)
                .getResultList()
                .stream()
                .map(car -> this.modelMapper.map(car, CarServiceModel.class))
                .collect(Collectors.toList());
    }
}
