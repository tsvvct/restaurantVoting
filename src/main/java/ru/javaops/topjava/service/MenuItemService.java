package ru.javaops.topjava.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.error.DataConflictException;
import ru.javaops.topjava.model.MenuItem;
import ru.javaops.topjava.repository.MenuItemRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MenuItemService {
    private final MenuItemRepository repository;

    @Transactional
    public MenuItem save(MenuItem menuItem, int itemId) {
        Optional<MenuItem> mi = repository.findById(itemId);
        MenuItem dbMi = mi.orElseThrow(() -> new EntityNotFoundException("Menu item with id=" + itemId + " not found"));
        if (!dbMi.getRestaurant().getId().equals(menuItem.getRestaurant().getId())) {
            throw new DataConflictException("Can't change menu item restaurant");
        }
        return repository.save(menuItem);
    }
}