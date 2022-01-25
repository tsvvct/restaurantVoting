package com.github.tsvvct.restaurantvoting.web.validation;

import com.github.tsvvct.restaurantvoting.HasRestaurantId;
import com.github.tsvvct.restaurantvoting.repository.RestaurantRepository;
import com.github.tsvvct.restaurantvoting.to.UserVoteTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
@AllArgsConstructor
@Slf4j
public class RestaurantValidator implements Validator {
    public static final String EXCEPTION_RESTAURANT_NOTNULL = "restaurant with such id does not exist";
    public static final String EXCEPTION_RESTAURANT_HAS_NO_MENU = "you can't vote for that restaurant because it doesn't share menu";

    private final RestaurantRepository repository;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return HasRestaurantId.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        HasRestaurantId value = ((HasRestaurantId) target);
        if (value instanceof UserVoteTo) {
            repository.findByIdWithMenuForDateOptional(value.getRestaurantId(), LocalDate.now())
                    .ifPresentOrElse(dbRest -> {
                                if (dbRest.getMenuItems().size() == 0) {
                                    log.info("restaurant {} doesn't share menu", dbRest.getId());
                                    errors.rejectValue("restaurantId", "", EXCEPTION_RESTAURANT_HAS_NO_MENU);
                                }
                            }
                            , () -> {
                                log.info("restaurant is NOT exist");
                                errors.rejectValue("restaurantId", "", EXCEPTION_RESTAURANT_NOTNULL);
                            });
        } else {
            repository.findById(value.getRestaurantId()).ifPresentOrElse(dbRest -> log.info("restaurant is present in DB")
                    , () -> {
                        log.info("rest is NOT present");
                        errors.rejectValue("restaurantId", "", EXCEPTION_RESTAURANT_NOTNULL);
                    });

        }
    }
}
