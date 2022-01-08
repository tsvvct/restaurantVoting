package ru.javaops.topjava.util;

import lombok.experimental.UtilityClass;
import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.to.RestaurantTo;

@UtilityClass
public class RestaurantUtil {

    public static Restaurant createNewFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getName());
    }
}
