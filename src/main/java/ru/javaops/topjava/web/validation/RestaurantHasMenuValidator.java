package ru.javaops.topjava.web.validation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.javaops.topjava.HasIdAndRestaurant;
import ru.javaops.topjava.repository.RestaurantRepository;

import java.time.LocalDate;

@Component
@AllArgsConstructor
@Slf4j
public class RestaurantHasMenuValidator implements org.springframework.validation.Validator {
    public static final String EXCEPTION_RESTAURANT_NOTNULL = "restaurant with such id does not exist";
    public static final String EXCEPTION_RESTAURANT_HAS_NO_MENU = "you can't vote for that restaurant because it doesn't share menu";

    private final RestaurantRepository repository;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return HasIdAndRestaurant.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        HasIdAndRestaurant value = ((HasIdAndRestaurant) target);

        repository.findByIdWithMenuForDateOptional(value.getRestaurant().getId(), LocalDate.now())
                .ifPresentOrElse(dbRest -> {
                            if (dbRest.getMenuItems().size() == 0) {
                                log.info("restaurant {} doesn't share menu", dbRest.getId());
                                errors.rejectValue("restaurant", "", EXCEPTION_RESTAURANT_HAS_NO_MENU);
                            }
                        }
                        , () -> {
                            log.info("restaurant is NOT exist");
                            errors.rejectValue("restaurant", "", EXCEPTION_RESTAURANT_NOTNULL);
                        });
    }
}
