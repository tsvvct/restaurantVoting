package ru.javaops.topjava.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.model.User;
import ru.javaops.topjava.repository.RestaurantRepository;
import ru.javaops.topjava.repository.UserRepository;

import javax.persistence.EntityNotFoundException;

@Component
public class JsonHelper {

    private static RestaurantRepository restaurantRepository;

    private static UserRepository userRepository;

    @Autowired
    public void setRestaurantRepository(RestaurantRepository repository) {
        JsonHelper.restaurantRepository = repository;
    }

    @Autowired
    public void setUserRepository(UserRepository repository) {
        JsonHelper.userRepository = repository;
    }

    public static Restaurant getRestaurantFromId(Integer id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Restaurant with id=" + id + " not found"));
    }

    public static User getUserFromId(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id=" + id + " not found"));
    }

}
