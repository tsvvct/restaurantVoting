package ru.javaops.topjava.web.restaurant;

import ru.javaops.topjava.model.MenuItem;
import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.web.MatcherFactory;

import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javaops.topjava.web.menuitem.MenuItemTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menuItems");
    public static MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menuItems.restaurants");

    public static final int RESTAURANT1_ID = 1;
    public static final int RESTAURANT2_ID = 2;
    public static final int RESTAURANT3_ID = 3;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "restaurant_1");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "restaurant_2");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, "restaurant_3");
    public static final Restaurant restaurant1Filtered = new Restaurant(RESTAURANT1_ID, "restaurant_1");
    public static final Restaurant restaurant2Filtered = new Restaurant(RESTAURANT2_ID, "restaurant_2");
    public static final Restaurant restaurant3Filtered = new Restaurant(RESTAURANT3_ID, "restaurant_3");

    public static final LocalDate testDate = LocalDate.now();

    static {
        restaurant1.setMenuItems(restaurant1MenuItems);
        restaurant2.setMenuItems(restaurant2MenuItems);
        restaurant3.setMenuItems(restaurant3MenuItems);

        Predicate<MenuItem> filter = item -> item.getMenuDate().equals(testDate);
        restaurant1Filtered.setMenuItems(restaurant1MenuItems.stream()
                .filter(filter)
                .collect(Collectors.toList()));
        restaurant2Filtered.setMenuItems(restaurant2MenuItems.stream()
                .filter(filter)
                .collect(Collectors.toList()));
        restaurant3Filtered.setMenuItems(restaurant3MenuItems.stream()
                .filter(filter)
                .collect(Collectors.toList()));
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "restaurant_new");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "restaurant_updated");
    }

}
