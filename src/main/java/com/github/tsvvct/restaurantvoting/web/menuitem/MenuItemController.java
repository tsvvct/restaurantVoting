package com.github.tsvvct.restaurantvoting.web.menuitem;

import com.github.tsvvct.restaurantvoting.model.MenuItem;
import com.github.tsvvct.restaurantvoting.util.validation.ValidationUtil;
import com.github.tsvvct.restaurantvoting.web.validation.RestaurantNotNullValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.github.tsvvct.restaurantvoting.repository.MenuItemRepository;
import com.github.tsvvct.restaurantvoting.service.MenuItemService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = MenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class MenuItemController {

    static final String REST_URL = "/api/admin/menu-items";

    @Autowired
    private MenuItemRepository repository;

    @Autowired
    private MenuItemService service;

    @Autowired
    private RestaurantNotNullValidator restaurantValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(restaurantValidator);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> get(@PathVariable int id) {
        log.info("get menu item with id={}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    public void delete(@PathVariable int id) {
        log.info("delete menu item with id={}", id);
        repository.deleteExisted(id);
    }

    @GetMapping
    public List<MenuItem> getAllForRestaurant(@RequestParam @Nullable Integer restaurantId,
                                              @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate menuDate) {
        log.info("get menu items for restaurant with id={} for date={}", restaurantId, menuDate);
        return repository.findAllByRestaurantId(restaurantId, menuDate);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = "restaurants", allEntries = true)
    public ResponseEntity<MenuItem> createWithLocation(@Valid @RequestBody MenuItem menuItem) {
        log.info("create menu item {}", menuItem);
        MenuItem created = service.create(menuItem);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(@Valid @RequestBody MenuItem menuItem, @PathVariable int id) {
        log.info("update menu item {} with id={}", menuItem, id);
        service.update(menuItem, id);
    }
}
