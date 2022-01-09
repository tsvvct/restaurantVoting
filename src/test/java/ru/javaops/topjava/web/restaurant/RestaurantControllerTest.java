package ru.javaops.topjava.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava.web.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava.web.menuitem.MenuItemTestData.testDate;
import static ru.javaops.topjava.web.menuitem.MenuItemTestData.*;
import static ru.javaops.topjava.web.restaurant.RestaurantTestData.*;
import static ru.javaops.topjava.web.user.UserTestData.ADMIN_MAIL;

class RestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantController.REST_URL + '/';

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant1));
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getWithMenuItems() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + "/with-menu"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_WITH_MENU_MATCHER.contentJson(restaurant1Filtered));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getRestaurantMenuItems() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + "/menu-items"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_MATCHER.contentJson(restaurant1MenuItems));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getRestaurantMenuItemsForDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + "/menu-items")
                .param("menuDate", testDate.toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_MATCHER.contentJson(restaurant1_menuitem3, restaurant1_menuitem4));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getWithMenuItemsForDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + "/with-menu")
                .param("menuDate", testDate.toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_WITH_MENU_MATCHER.contentJson(restaurant1Filtered));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant1, restaurant2, restaurant3));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllWithMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/with-menu"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_WITH_MENU_MATCHER.contentJson(List.of(restaurant1Filtered, restaurant2Filtered,
                        restaurant3Filtered)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllWithMenuForDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/with-menu")
                .param("menuDate", testDate.toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_WITH_MENU_MATCHER.contentJson(restaurant1Filtered, restaurant2Filtered, restaurant3Filtered));
    }
}