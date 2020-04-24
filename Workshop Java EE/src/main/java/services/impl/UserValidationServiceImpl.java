package services.impl;

import entities.User;
import services.UserValidationService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidationServiceImpl implements UserValidationService {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    private final EntityManager entityManager;

    @Inject
    public UserValidationServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean canUserBeRegistered(String username, String password, String confirmPassword, String email) {
        return
                isEmailValid(email) &&
                        arePasswordsMatching(password, confirmPassword) &&
                        isUsernameUnique(username);
    }

    private boolean arePasswordsMatching(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private boolean isEmailValid(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    private boolean isUsernameUnique(String username) {
        List<User> users = this.entityManager
                .createQuery("select u from User  u where u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();

        if (!users.isEmpty()) {
            return false;
        }

        return true;

    }


}
