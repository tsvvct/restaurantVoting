package com.github.tsvvct.restaurantvoting.web.restaurant;

import com.github.tsvvct.restaurantvoting.model.MenuItem;
import com.github.tsvvct.restaurantvoting.model.Restaurant;
import com.github.tsvvct.restaurantvoting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menuItems");
    public static MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menuItems.restaurant");

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

    private static final int MENU_ITEM1_ID = 1;

    public static final MenuItem restaurant1_menuitem1 = new MenuItem(MENU_ITEM1_ID,
            "item_1 restaurant_1", restaurant1, 1000, testDate.minusDays(1));
    public static final MenuItem restaurant1_menuitem2 = new MenuItem(MENU_ITEM1_ID + 1,
            "item_2 restaurant_1", restaurant1, 1200, testDate.minusDays(1));
    public static final MenuItem restaurant1_menuitem3 = new MenuItem(MENU_ITEM1_ID + 2,
            "item_3 restaurant_1", restaurant1, 300, testDate);
    public static final MenuItem restaurant1_menuitem4 = new MenuItem(MENU_ITEM1_ID + 3,
            "item_4 restaurant_1", restaurant1, 1300, testDate);

    public static final MenuItem restaurant2_menuitem1 = new MenuItem(MENU_ITEM1_ID + 4,
            "item_1 restaurant_2", restaurant2, 1000, testDate.minusDays(1));
    public static final MenuItem restaurant2_menuitem2 = new MenuItem(MENU_ITEM1_ID + 5,
            "item_2 restaurant_2", restaurant2, 1200, testDate.minusDays(1));
    public static final MenuItem restaurant2_menuitem3 = new MenuItem(MENU_ITEM1_ID + 6,
            "item_3 restaurant_2", restaurant2, 1500, testDate);
    public static final MenuItem restaurant2_menuitem4 = new MenuItem(MENU_ITEM1_ID + 7,
            "item_4 restaurant_2", restaurant2, 200, testDate);

    public static final MenuItem restaurant3_menuitem1 = new MenuItem(MENU_ITEM1_ID + 8,
            "item_1 restaurant_3", restaurant3, 1000, testDate.minusDays(1));
    public static final MenuItem restaurant3_menuitem2 = new MenuItem(MENU_ITEM1_ID + 9,
            "item_2 restaurant_3", restaurant3, 1600, testDate.minusDays(1));

    public static final List<MenuItem> restaurant1MenuItems = List.of(
            restaurant1_menuitem1,
            restaurant1_menuitem2,
            restaurant1_menuitem3,
            restaurant1_menuitem4
    );

    public static final List<MenuItem> restaurant2MenuItems = List.of(
            restaurant2_menuitem1,
            restaurant2_menuitem2,
            restaurant2_menuitem3,
            restaurant2_menuitem4
    );

    public static final List<MenuItem> restaurant3MenuItems = List.of(
            restaurant3_menuitem1,
            restaurant3_menuitem2
    );

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
