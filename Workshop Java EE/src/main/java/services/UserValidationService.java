package services;

public interface UserValidationService {

    boolean canUserBeRegistered(String username, String password, String confirmPassword, String email);
}
