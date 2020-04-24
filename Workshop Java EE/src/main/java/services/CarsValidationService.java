package services;

public interface CarsValidationService {

    boolean isCarValid(String brand, String model, Integer year, String engine);
}
