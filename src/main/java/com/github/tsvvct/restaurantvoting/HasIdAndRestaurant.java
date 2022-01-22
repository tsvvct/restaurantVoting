package com.github.tsvvct.restaurantvoting;

import com.github.tsvvct.restaurantvoting.model.Restaurant;

public interface HasIdAndRestaurant extends HasId {
    Restaurant getRestaurant();
}