package com.github.tsvvct.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "menu_item", uniqueConstraints = {@UniqueConstraint(name = "menuitem_date_name_idx",
        columnNames = {"restaurant_id", "menu_date", "name"})})
@Getter
@Setter
@NoArgsConstructor
public class MenuItem extends NamedEntity {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @Hidden
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

    @Override
    public String toString() {
        return "MenuItem:{" +
                "id:" + this.getId() + "; " +
                "name:" + this.getName() + "; " +
                "price:" + this.getPrice() + "; " +
                "dt:" + this.getMenuDate() + "; "
                + "}";

    }
}
