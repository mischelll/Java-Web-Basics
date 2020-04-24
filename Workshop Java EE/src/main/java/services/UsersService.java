package services;

import entities.service.UserLoginServiceModel;

public interface UsersService {
    void registerUser(String username, String password,String confirmPassword, String email) throws Exception;

    UserLoginServiceModel login(String username, String password);
}
