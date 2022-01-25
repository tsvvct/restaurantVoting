package com.github.tsvvct.restaurantvoting.web.menuitem;

import com.github.tsvvct.restaurantvoting.to.MenuItemTo;
import com.github.tsvvct.restaurantvoting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

public class MenuItemTestData {
    public static final MatcherFactory.Matcher<MenuItemTo> MENU_ITEM_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MenuItemTo.class, "restaurantId");

    public static final int MENU_ITEM1_ID = 1;

    public static final LocalDate testDate = LocalDate.now();

    private static final Integer restaurantId_1 = 1;
    private static final Integer restaurantId_2 = 2;
    private static final Integer restaurantId_3 = 3;

    public static final MenuItemTo restaurant1_menuitem1 = new MenuItemTo(MENU_ITEM1_ID,
            "item_1 restaurant_1", restaurantId_1, 1000, testDate.minusDays(1));
    public static final MenuItemTo restaurant1_menuitem2 = new MenuItemTo(MENU_ITEM1_ID + 1,
            "item_2 restaurant_1", restaurantId_1, 1200, testDate.minusDays(1));
    public static final MenuItemTo restaurant1_menuitem3 = new MenuItemTo(MENU_ITEM1_ID + 2,
            "item_3 restaurant_1", restaurantId_1, 300, testDate);
    public static final MenuItemTo restaurant1_menuitem4 = new MenuItemTo(MENU_ITEM1_ID + 3,
            "item_4 restaurant_1", restaurantId_1, 1300, testDate);

    public static final MenuItemTo restaurant2_menuitem1 = new MenuItemTo(MENU_ITEM1_ID + 4,
            "item_1 restaurant_2", restaurantId_2, 1000, testDate.minusDays(1));
    public static final MenuItemTo restaurant2_menuitem2 = new MenuItemTo(MENU_ITEM1_ID + 5,
            "item_2 restaurant_2", restaurantId_2, 1200, testDate.minusDays(1));
    public static final MenuItemTo restaurant2_menuitem3 = new MenuItemTo(MENU_ITEM1_ID + 6,
            "item_3 restaurant_2", restaurantId_2, 1500, testDate);
    public static final MenuItemTo restaurant2_menuitem4 = new MenuItemTo(MENU_ITEM1_ID + 7,
            "item_4 restaurant_2", restaurantId_2, 200, testDate);

    public static final MenuItemTo restaurant3_menuitem1 = new MenuItemTo(MENU_ITEM1_ID + 8,
            "item_1 restaurant_3", restaurantId_3, 1000, testDate.minusDays(1));
    public static final MenuItemTo restaurant3_menuitem2 = new MenuItemTo(MENU_ITEM1_ID + 9,
            "item_2 restaurant_3", restaurantId_3, 1600, testDate.minusDays(1));

    public static final List<MenuItemTo> restaurant1MenuItems = List.of(
            restaurant1_menuitem1,
            restaurant1_menuitem2,
            restaurant1_menuitem3,
            restaurant1_menuitem4
    );

    public static final List<MenuItemTo> restaurant2MenuItems = List.of(
            restaurant2_menuitem1,
            restaurant2_menuitem2,
            restaurant2_menuitem3,
            restaurant2_menuitem4
    );

    public static final List<MenuItemTo> restaurant3MenuItems = List.of(
            restaurant3_menuitem1,
            restaurant3_menuitem2
    );

    public static MenuItemTo getNew() {
        return new MenuItemTo(null, "item_new", restaurantId_1, 1000, testDate.minusDays(1));
    }

    public static MenuItemTo getUpdated() {
        return new MenuItemTo(MENU_ITEM1_ID, "item_1 restaurant_1 updated", restaurantId_1, 1001, restaurant1_menuitem1.getMenuDate());
    }
}
