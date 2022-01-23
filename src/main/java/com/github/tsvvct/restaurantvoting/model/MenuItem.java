package com.github.tsvvct.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.tsvvct.restaurantvoting.HasIdAndRestaurant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import com.github.tsvvct.restaurantvoting.util.ChildAsIdOnlySerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "menu_item")
@Getter
@Setter
@NoArgsConstructor
public class MenuItem extends NamedEntity implements HasIdAndRestaurant {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonSerialize(using = ChildAsIdOnlySerializer.class)
    private Restaurant restaurant;

    @NotNull
    @Range(min = 1)
    @Schema(description = "price in cents", example = "for 10.00$ shold be 1000")
    private Integer price;

    @NotNull
    @Column(name = "menu_date")
    private LocalDate menuDate = LocalDate.now();

    public MenuItem(Integer id, String name, Restaurant restaurant, Integer price, LocalDate menuDate) {
        super(id, name);
        this.restaurant = restaurant;
        this.price = price;
        this.menuDate = menuDate;
    }

    @JsonProperty("restaurant")
    public void setRestaurantFromId(Integer id) {
        this.restaurant = new Restaurant(id, null);
    }

    @Override
    public String toString() {
        return "MenuItem:{" +
                "id:" + this.getId() + "; " +
                "name:" + this.getName() + "; " +
                "price:" + this.getPrice() + "; " +
                "dt:" + this.getMenuDate() + "; " +
                "rest_id:" + (this.restaurant != null ? this.restaurant.getId() : "null")
                + "}";

    }
}
