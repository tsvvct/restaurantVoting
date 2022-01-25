package com.github.tsvvct.restaurantvoting.util;

import com.github.tsvvct.restaurantvoting.model.MenuItem;
import com.github.tsvvct.restaurantvoting.model.Restaurant;
import com.github.tsvvct.restaurantvoting.to.MenuItemTo;

import java.util.Collection;
import java.util.List;

public class MenuItemUtil {
    public static MenuItem createItemFromTo(MenuItemTo itemTo, Restaurant restaurant) {
        return new MenuItem(itemTo.getId(), itemTo.getName(), restaurant, itemTo.getPrice(), itemTo.getMenuDate());
    }

    public static MenuItemTo createToFromItem(MenuItem item) {
        if (item == null) {
            return null;
        } else {
            return new MenuItemTo(item.getId(), item.getName(), item.getRestaurant().getId(), item.getPrice(), item.getMenuDate());
        }
    }

    public static List<MenuItemTo> getListOfTosFromListOfItems(Collection<MenuItem> menuItems) {
        return menuItems.stream().map(MenuItemUtil::createToFromItem).toList();
    }
}
