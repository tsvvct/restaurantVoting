package ru.javaops.topjava.util;

import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.to.RestaurantTo;

public class RestaurantUtil {

    public static Restaurant createNewFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getName());
    }
}
