package services.impl;

import services.CarsValidationService;

public class CarsValidationImpl implements CarsValidationService {


    @Override
    public boolean isCarValid(String brand, String model, Integer year, String engine) {
        boolean checkBrand = brand != null && brand.length() > 1;
        boolean checkModel = model != null && model.length() > 1;
        boolean checkYear = year >= 1885;

        if (checkBrand && checkModel && checkYear){
            return true;
        }

        return false;
    }
}
