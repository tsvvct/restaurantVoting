package com.github.tsvvct.restaurantvoting.web.restaurant;

import com.github.tsvvct.restaurantvoting.web.AbstractControllerTest;
import com.github.tsvvct.restaurantvoting.web.menuitem.MenuItemTestData;
import com.github.tsvvct.restaurantvoting.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantController.REST_URL + '/';

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.RESTAURANT1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_MATCHER.contentJson(RestaurantTestData.restaurant1));
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.RESTAURANT1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getWithMenuItems() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.RESTAURANT1_ID + "/with-menu"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_WITH_MENU_MATCHER.contentJson(RestaurantTestData.restaurant1Filtered));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getRestaurantMenuItems() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.RESTAURANT1_ID + "/menu-items"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuItemTestData.MENU_ITEM_MATCHER.contentJson(MenuItemTestData.restaurant1MenuItems));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getRestaurantMenuItemsForDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.RESTAURANT1_ID + "/menu-items")
                .param("menuDate", MenuItemTestData.testDate.toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuItemTestData.MENU_ITEM_MATCHER.contentJson(MenuItemTestData.restaurant1_menuitem3, MenuItemTestData.restaurant1_menuitem4));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getWithMenuItemsForDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.RESTAURANT1_ID + "/with-menu")
                .param("menuDate", MenuItemTestData.testDate.toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_WITH_MENU_MATCHER.contentJson(RestaurantTestData.restaurant1Filtered));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_MATCHER.contentJson(RestaurantTestData.restaurant1, RestaurantTestData.restaurant2, RestaurantTestData.restaurant3));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getAllWithMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/with-menu"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_WITH_MENU_MATCHER.contentJson(List.of(RestaurantTestData.restaurant1Filtered, RestaurantTestData.restaurant2Filtered,
                        RestaurantTestData.restaurant3Filtered)));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getAllWithMenuForDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/with-menu")
                .param("menuDate", MenuItemTestData.testDate.toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_WITH_MENU_MATCHER.contentJson(RestaurantTestData.restaurant1Filtered, RestaurantTestData.restaurant2Filtered, RestaurantTestData.restaurant3Filtered));
    }
}