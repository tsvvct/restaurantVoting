package com.github.tsvvct.restaurantvoting.service;

import com.github.tsvvct.restaurantvoting.model.MenuItem;
import com.github.tsvvct.restaurantvoting.repository.MenuItemRepository;
import com.github.tsvvct.restaurantvoting.repository.RestaurantRepository;
import com.github.tsvvct.restaurantvoting.to.MenuItemTo;
import com.github.tsvvct.restaurantvoting.util.MenuItemUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.github.tsvvct.restaurantvoting.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "restaurants")
public class MenuItemService {
    private final MenuItemRepository repository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    @CacheEvict(allEntries = true)
    public MenuItemTo update(MenuItemTo menuItemTo, int itemId) {
        menuItemTo.setId(itemId);
        MenuItem itemFromDb = repository.get(itemId, menuItemTo.getRestaurantId());
        checkNotFoundWithId(itemFromDb, itemId);
        MenuItem menuItem = MenuItemUtil.createItemFromTo(menuItemTo, itemFromDb.getRestaurant());
        return MenuItemUtil.createToFromItem(repository.save(menuItem));
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public MenuItemTo create(MenuItemTo menuItemTo) {
        return MenuItemUtil.createToFromItem(repository.save(
                MenuItemUtil.createItemFromTo(menuItemTo, restaurantRepository.getById(menuItemTo.getRestaurantId()))));
    }

    public List<MenuItemTo> getAllFiltered(Integer restaurantId, LocalDate menuDate) {
        return MenuItemUtil.getListOfTosFromListOfItems(repository.findAllByRestaurantId(restaurantId, menuDate));
    }

    public Optional<MenuItemTo> get(Integer id) {
        return Optional.ofNullable(MenuItemUtil.createToFromItem(repository.findById(id).orElse(null)));
    }

    @CacheEvict(allEntries = true)
    public void delete(Integer id) {
        repository.deleteExisted(id);
    }
}
