package services.impl;

import entities.User;
import entities.service.UserLoginServiceModel;
import org.modelmapper.ModelMapper;
import services.HashingService;
import services.UserValidationService;
import services.UsersService;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class UsersServiceImpl implements UsersService {
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;
    private final HashingService hashingService;
    private final UserValidationService userValidationService;

    @Inject
    public UsersServiceImpl(ModelMapper modelMapper, EntityManager entityManager, HashingService hashingService, UserValidationService userValidationService) {
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
        this.hashingService = hashingService;
        this.userValidationService = userValidationService;
    }

    @Override
    public void registerUser(String username, String password,String confirmPassword, String email) throws Exception {
        if (!this.userValidationService.canUserBeRegistered(username,password,confirmPassword,email)){
            throw new Exception("User cannot be created!");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(this.hashingService.hashPassword(password));
        user.setEmail(email);

        this.entityManager.getTransaction().begin();
        this.entityManager.persist(user);
        this.entityManager.getTransaction().commit();

    }

    @Override
    public UserLoginServiceModel login(String username, String password) {
        User user = this.entityManager
                .createQuery("select u from User u " +
                        "where u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();

        if (user == null){
            return null;
        }

        if (!user.getPassword().equals(this.hashingService.hashPassword(password))){
            return null;
        }

        return this.modelMapper.map(user,UserLoginServiceModel.class);
    }
}
