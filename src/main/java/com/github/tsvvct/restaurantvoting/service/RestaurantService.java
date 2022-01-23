package com.github.tsvvct.restaurantvoting.service;

import com.github.tsvvct.restaurantvoting.model.MenuItem;
import com.github.tsvvct.restaurantvoting.model.Restaurant;
import com.github.tsvvct.restaurantvoting.repository.RestaurantRepository;
import com.github.tsvvct.restaurantvoting.to.RestaurantTo;
import com.github.tsvvct.restaurantvoting.util.RestaurantUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.github.tsvvct.restaurantvoting.util.validation.ValidationUtil.checkNew;
import static com.github.tsvvct.restaurantvoting.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;

    @Transactional
    public Restaurant update(RestaurantTo restaurantTo, int id) {
        Restaurant restaurant = RestaurantUtil.createNewFromTo(restaurantTo);
        restaurant.setId(id);
        Optional<Restaurant> restaurantFromDb = repository.findById(id);
        checkNotFoundWithId(restaurantFromDb, id);
        return repository.save(restaurant);
    }

    @Transactional
    public Restaurant create(RestaurantTo restaurantTo) {
        checkNew(restaurantTo);
        Restaurant restaurant = repository.save(RestaurantUtil.createNewFromTo(restaurantTo));
        return repository.save(restaurant);
    }
}
