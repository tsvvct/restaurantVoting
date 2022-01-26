package com.github.tsvvct.restaurantvoting.web.menuitem;

import com.github.tsvvct.restaurantvoting.repository.MenuItemRepository;
import com.github.tsvvct.restaurantvoting.service.MenuItemService;
import com.github.tsvvct.restaurantvoting.to.MenuItemTo;
import com.github.tsvvct.restaurantvoting.web.validation.RestaurantValidator;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    private RestaurantValidator restaurantValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(restaurantValidator);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemTo> get(@PathVariable int id) {
        log.info("get menu item with id={}", id);
        return ResponseEntity.of(service.get(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete menu item with id={}", id);
        service.delete(id);
    }

    @GetMapping
    @Operation(
            summary = "Return all menu items by specified filter",
            description = "Returns all menu items filtered with the specified date, restaurant."
    )
    public List<MenuItemTo> getAllFiltered(@RequestParam @Nullable Integer restaurantId,
            @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate menuDate) {
        log.info("get menu items for restaurant with id={} for date={}", restaurantId, menuDate);
        return service.getAllFiltered(restaurantId, menuDate);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MenuItemTo> createWithLocation(@Valid @RequestBody MenuItemTo menuItemTo) {
        log.info("create menu item {}", menuItemTo);
        MenuItemTo created = service.create(menuItemTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MenuItemTo update(@PathVariable int id, @Valid @RequestBody MenuItemTo menuItemTo) {
        log.info("update menu item with id={}", id);
        return service.update(menuItemTo, id);
    }
}
