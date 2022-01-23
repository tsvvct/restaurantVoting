package com.github.tsvvct.restaurantvoting.service;

import com.github.tsvvct.restaurantvoting.model.MenuItem;
import com.github.tsvvct.restaurantvoting.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.github.tsvvct.restaurantvoting.util.validation.ValidationUtil.checkNew;
import static com.github.tsvvct.restaurantvoting.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class MenuItemService {
    private final MenuItemRepository repository;

    @Transactional
    public MenuItem update(MenuItem menuItem, int itemId) {
        menuItem.setId(itemId);
        Optional<MenuItem> itemFromDb = repository.findById(itemId);
        checkNotFoundWithId(itemFromDb, itemId);
        return repository.save(menuItem);
    }

    @Transactional
    public MenuItem create(MenuItem menuItem) {
        checkNew(menuItem);
        return repository.save(menuItem);
    }
}
