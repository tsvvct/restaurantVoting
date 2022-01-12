package ru.javaops.topjava.web.menuitem;

import ru.javaops.topjava.model.MenuItem;
import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

public class MenuItemTestData {
    public static final MatcherFactory.Matcher<MenuItem> MENU_ITEM_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MenuItem.class, "restaurant");

    public static final int MENU_ITEM1_ID = 1;

    public static final LocalDate testDate = LocalDate.now();

    private static final Restaurant restaurant1 = new Restaurant(1, null);
    private static final Restaurant restaurant2 = new Restaurant(2, null);
    private static final Restaurant restaurant3 = new Restaurant(3, null);

    public static final MenuItem restaurant1_menuitem1 = new MenuItem(MENU_ITEM1_ID, "item_1 restaurant_1", restaurant1, 1000, testDate.minusDays(1));
    public static final MenuItem restaurant1_menuitem2 = new MenuItem(MENU_ITEM1_ID + 1, "item_2 restaurant_1", restaurant1, 1200, testDate.minusDays(1));
    public static final MenuItem restaurant1_menuitem3 = new MenuItem(MENU_ITEM1_ID + 2, "item_3 restaurant_1", restaurant1, 300, testDate);
    public static final MenuItem restaurant1_menuitem4 = new MenuItem(MENU_ITEM1_ID + 3, "item_4 restaurant_1", restaurant1, 1300, testDate);

    public static final MenuItem restaurant2_menuitem1 = new MenuItem(5, "item_1 restaurant_2", restaurant2, 1000, testDate.minusDays(1));
    public static final MenuItem restaurant2_menuitem2 = new MenuItem(6, "item_2 restaurant_2", restaurant2, 1200, testDate.minusDays(1));
    public static final MenuItem restaurant2_menuitem3 = new MenuItem(7, "item_3 restaurant_2", restaurant2, 1500, testDate);
    public static final MenuItem restaurant2_menuitem4 = new MenuItem(8, "item_4 restaurant_2", restaurant2, 200, testDate);

    public static final MenuItem restaurant3_menuitem1 = new MenuItem(9, "item_1 restaurant_3", restaurant3, 1000, testDate.minusDays(1));
    public static final MenuItem restaurant3_menuitem2 = new MenuItem(10, "item_2 restaurant_3", restaurant3, 1600, testDate.minusDays(1));

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

    public static MenuItem getNew() {
        return new MenuItem(null, "item_new", restaurant1, 1000, testDate.minusDays(1));
    }

    public static MenuItem getUpdated() {
        return new MenuItem(MENU_ITEM1_ID, "item_1 restaurant_1 updated", restaurant1, 1001, restaurant1_menuitem1.getMenuDate());
    }
}
