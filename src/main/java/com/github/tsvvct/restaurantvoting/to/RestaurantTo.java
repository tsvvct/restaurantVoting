package com.github.tsvvct.restaurantvoting.to;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RestaurantTo extends NamedTo {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<MenuItemTo> menuItems;

    public RestaurantTo() {
        super(null, null);
        this.menuItems = null;
    }

    public RestaurantTo(String name) {
        super(null, name);
        this.menuItems = null;
    }

    public RestaurantTo(Integer id) {
        super(id, null);
        this.menuItems = null;
    }

    public RestaurantTo(Integer id, String name) {
        super(id, name);
        this.menuItems = null;
    }

    public RestaurantTo(Integer id, String name, List<MenuItemTo> menuItems) {
        super(id, name);
        this.menuItems = menuItems;
    }
}
