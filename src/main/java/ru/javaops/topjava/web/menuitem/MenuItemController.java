package ru.javaops.topjava.web.menuitem;

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
import ru.javaops.topjava.model.MenuItem;
import ru.javaops.topjava.repository.MenuItemRepository;
import ru.javaops.topjava.service.MenuItemService;
import ru.javaops.topjava.web.validation.RestaurantNotNullValidator;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.javaops.topjava.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = MenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class MenuItemController {

    static final String REST_URL = "/api/admin/menu-items";

    @Autowired
    protected MenuItemRepository repository;

    @Autowired
    protected MenuItemService service;

    @Autowired
    private RestaurantNotNullValidator restValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(restValidator);
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
        checkNew(menuItem);
        MenuItem created = repository.save(menuItem);
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
        assureIdConsistent(menuItem, id);
        service.save(menuItem, id);
    }
}
