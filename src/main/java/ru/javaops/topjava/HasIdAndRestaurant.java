package ru.javaops.topjava;

import ru.javaops.topjava.model.Restaurant;

public interface HasIdAndRestaurant extends HasId {
    Restaurant getRestaurant();
}