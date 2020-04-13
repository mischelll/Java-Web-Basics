package app.service;

import app.domain.entities.Car;
import app.domain.models.service.CarServiceModel;
import app.domain.models.view.CarViewModel;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class CarServiceImpl implements CarService {
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;

    @Inject
    public CarServiceImpl(EntityManager entityManager, ModelMapper modelMapper) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createCar(CarServiceModel car) {
        Car car1 = this.modelMapper.map(car, Car.class);

        entityManager.getTransaction().begin();
        entityManager.persist(car1);
        entityManager.getTransaction().commit();


    }

    @Override
    public List<CarViewModel> viewCars() {

        List<Car> query = this.entityManager.createQuery("select c from Car c " +
                "order by c.year desc ,c.brand", Car.class)
                .getResultList();

        List<CarViewModel> cars = new ArrayList<>();
        query.forEach(c -> {
            CarViewModel car = this.modelMapper.map(c, CarViewModel.class);
            cars.add(car);
        });



        return cars;
    }
}
