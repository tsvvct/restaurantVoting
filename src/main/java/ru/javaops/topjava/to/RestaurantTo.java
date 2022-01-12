package ru.javaops.topjava.to;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Hidden
@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo {

    public RestaurantTo(String name) {
        super(null, name);
    }

    public RestaurantTo(Integer id) {
        super(id, null);
    }

    public RestaurantTo(Integer id, String name) {
        super(id, name);
    }
}
