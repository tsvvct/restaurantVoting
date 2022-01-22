package com.github.tsvvct.restaurantvoting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import com.github.tsvvct.restaurantvoting.model.MenuItem;
import com.github.tsvvct.restaurantvoting.model.Restaurant;
import com.github.tsvvct.restaurantvoting.repository.MenuItemRepository;
import com.github.tsvvct.restaurantvoting.repository.RestaurantRepository;

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
    protected RestaurantRepository repository;

    @Autowired
    protected MenuItemRepository menuItemRepository;

    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant for id={}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @GetMapping("/{id}/with-menu")
    public ResponseEntity<Restaurant> getWithMenuItems(@PathVariable int id,
                                                       @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate menuDate) {
        log.info("get restaurant with menu items for date={} for id={}",
                Objects.requireNonNullElse(menuDate, LocalDate.now()), id);
        return ResponseEntity.of(repository.findByIdWithMenuForDateOptional(id,
                Objects.requireNonNullElse(menuDate, LocalDate.now())));
    }

    @GetMapping("/{id}/menu-items")
    public List<MenuItem> getRestaurantMenuItems(@PathVariable int id,
                                                 @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate menuDate) {
        log.info("get menu items for date={} for restaurant with id={}", menuDate, id);
        return menuItemRepository.findAllByRestaurantId(id, menuDate);
    }

    @GetMapping
    @Cacheable
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return repository.findAll();
    }

    @GetMapping("/with-menu")
    @Cacheable
    public List<Restaurant> getAllWithMenu(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate menuDate) {
        log.info("get all restaurants with menu items for date={}", Objects.requireNonNullElse(menuDate, LocalDate.now()));
        return repository.findAllWithMenuForDate(Objects.requireNonNullElse(menuDate, LocalDate.now()));
    }
}
