package com.github.tsvvct.restaurantvoting.web.menuitem;

import com.github.tsvvct.restaurantvoting.repository.MenuItemRepository;
import com.github.tsvvct.restaurantvoting.to.MenuItemTo;
import com.github.tsvvct.restaurantvoting.util.JsonUtil;
import com.github.tsvvct.restaurantvoting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.tsvvct.restaurantvoting.util.MenuItemUtil.createToFromItem;
import static com.github.tsvvct.restaurantvoting.web.menuitem.MenuItemTestData.*;
import static com.github.tsvvct.restaurantvoting.web.user.UserTestData.ADMIN_MAIL;
import static com.github.tsvvct.restaurantvoting.web.user.UserTestData.USER_MAIL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuItemControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MenuItemController.REST_URL + '/';

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MENU_ITEM1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_TO_MATCHER.contentJson(restaurant1_menuitem1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MENU_ITEM1_ID))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MENU_ITEM1_ID))
                .andExpect(status().isNoContent());
        assertFalse(menuItemRepository.findById(MENU_ITEM1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MENU_ITEM1_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllForRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .param("restaurantId", "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_TO_MATCHER.contentJson(restaurant1_menuitem1, restaurant1_menuitem2,
                        restaurant1_menuitem3, restaurant1_menuitem4));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllForRestaurantForDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .param("restaurantId", "1")
                .param("menuDate", MenuItemTestData.testDate.toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_TO_MATCHER.contentJson(restaurant1_menuitem3, restaurant1_menuitem4));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        MenuItemTo newItem = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newItem)))
                .andExpect(status().isCreated());

        MenuItemTo created = MENU_ITEM_TO_MATCHER.readFromJson(action);
        int newId = created.id();
        newItem.setId(newId);
        MENU_ITEM_TO_MATCHER.assertMatch(created, newItem);
        MENU_ITEM_TO_MATCHER.assertMatch(createToFromItem(menuItemRepository.getById(newId)), newItem);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createForbidden() throws Exception {
        MenuItemTo newItem = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newItem)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        MenuItemTo updated = getUpdated();
        ResultActions action = perform(MockMvcRequestBuilders.put(REST_URL + MENU_ITEM1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MenuItemTo patched = MENU_ITEM_TO_MATCHER.readFromJson(action);
        MENU_ITEM_TO_MATCHER.assertMatch(patched, updated);
        MENU_ITEM_TO_MATCHER.assertMatch(createToFromItem(menuItemRepository.getById(MENU_ITEM1_ID)), updated);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateForbidden() throws Exception {
        MenuItemTo updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + MENU_ITEM1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isForbidden());
    }
}