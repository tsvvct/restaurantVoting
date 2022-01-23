package com.github.tsvvct.restaurantvoting.web.validation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import com.github.tsvvct.restaurantvoting.HasId;
import com.github.tsvvct.restaurantvoting.HasIdAndRestaurant;
import com.github.tsvvct.restaurantvoting.repository.RestaurantRepository;

@Component
@AllArgsConstructor
@Slf4j
public class RestaurantNotNullValidator implements org.springframework.validation.Validator {
    public static final String EXCEPTION_RESTAURANT_NOTNULL = "restaurant with such id does not exist";

    private final RestaurantRepository repository;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return HasIdAndRestaurant.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        HasIdAndRestaurant value = ((HasIdAndRestaurant) target);

        repository.findById(value.getRestaurant().getId())
                .ifPresentOrElse(dbRest -> log.info("restaurant is present in DB")
                        , () -> {
                            log.info("rest is NOT present");
                            errors.rejectValue("restaurant", "", EXCEPTION_RESTAURANT_NOTNULL);
                        });
    }
}
