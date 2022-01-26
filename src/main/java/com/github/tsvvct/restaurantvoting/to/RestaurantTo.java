package com.github.tsvvct.restaurantvoting.to;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RestaurantTo extends NamedTo {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<MenuItemTo> menuItems;

    public RestaurantTo() {
        super(null, null);
    }
}
