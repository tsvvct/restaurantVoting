package com.github.tsvvct.restaurantvoting.to;

import com.github.tsvvct.restaurantvoting.HasRestaurantId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class MenuItemTo extends NamedTo implements HasRestaurantId {
    @NotNull
    private Integer restaurantId;

    @NotNull
    @Range(min = 1)
    @Schema(description = "price in cents", example = "for 10.00$ should be 1000",
            defaultValue = "1")
    private Integer price;

    @NotNull
    @Schema(defaultValue = "${LocalDate.now()}")
    private LocalDate menuDate;

    public MenuItemTo(Integer id, String name, Integer restaurantId, Integer price, LocalDate menuDate) {
        super(id, name);
        this.id = id;
        this.name = name;
        this.restaurantId = restaurantId;
        this.price = price;
        this.menuDate = menuDate;
    }

    @Override
    public String toString() {
        return "MenuItemTo:{" +
                "id:" + id + "; " +
                "name:" + name + "; " +
                "restaurantId:" + restaurantId + "; " +
                "price:" + price + "; " +
                "menuDate:" + menuDate +
                "}";
    }
}
