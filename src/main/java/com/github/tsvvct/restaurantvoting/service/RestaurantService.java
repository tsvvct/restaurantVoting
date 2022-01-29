package com.github.tsvvct.restaurantvoting.service;

import com.github.tsvvct.restaurantvoting.model.Restaurant;
import com.github.tsvvct.restaurantvoting.repository.RestaurantRepository;
import com.github.tsvvct.restaurantvoting.to.RestaurantTo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.github.tsvvct.restaurantvoting.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;

    @Transactional
    public Restaurant patch(Integer id, RestaurantTo restaurantTo) {
        Restaurant restaurant = repository.findById(id).orElse(null);
        checkNotFoundWithId(restaurant, id);
        restaurant.setName(restaurantTo.getName());
        return repository.save(restaurant);
    }
}
