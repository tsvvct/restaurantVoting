package com.github.tsvvct.restaurantvoting.web.restaurant;

import com.github.tsvvct.restaurantvoting.model.Restaurant;
import com.github.tsvvct.restaurantvoting.repository.RestaurantRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "restaurants")
public class RestaurantController {

    static final String REST_URL = "/api/restaurants";

    @Autowired
    private RestaurantRepository repository;

    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant for id={}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @GetMapping("/{id}/with-menu")
    @Operation(
            summary = "Return restaurant with menu",
            description = "Returns restaurant with menu on the specified date, if date is empty, the menu for today will be returned."
    )
    public ResponseEntity<Restaurant> getWithMenuItems(@PathVariable int id,
                                                       @Parameter(description = "Date to get menu for. If empty current date is used.") @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate menuDate) {
        LocalDate menuDateForQuery = Objects.requireNonNullElse(menuDate, LocalDate.now());
        log.info("get restaurant with menu items for date={} for id={}",
                menuDateForQuery, id);
        return ResponseEntity.of(repository.findByIdWithMenuForDateOptional(id, menuDateForQuery));
    }

    @GetMapping
    @Cacheable
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return repository.findAll();
    }

    @GetMapping("/with-menu")
    @Cacheable
    @Operation(
            summary = "Return all restaurants with menu",
            description = "Returns all restaurants with menu on the specified date, if date is empty, the menu for today will be returned."
    )
    public List<Restaurant> getAllWithMenu(@Parameter(description = "Date to get menu for. If empty current date is used.") @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate menuDate) {
        log.info("get all restaurants with menu items for date={}", Objects.requireNonNullElse(menuDate, LocalDate.now()));
        return repository.findAllWithMenuForDate(Objects.requireNonNullElse(menuDate, LocalDate.now()));
    }
}
