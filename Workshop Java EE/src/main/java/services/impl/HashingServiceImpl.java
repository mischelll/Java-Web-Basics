package services.impl;

import services.HashingService;

public class HashingServiceImpl implements HashingService {
    @Override
    public String hashPassword(String password) {
        return "*@" + password + "@*";
    }
}
