package ru.javaops.topjava.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import ru.javaops.topjava.HasIdAndRestaurant;
import ru.javaops.topjava.util.ChildAsIdOnlySerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "menu_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem extends NamedEntity implements HasIdAndRestaurant {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonSerialize(using = ChildAsIdOnlySerializer.class)
    Restaurant restaurant;

    // TODO: 06.01.2022 validation
    @NotNull
    @Range(min = 1)
    Integer price;

    @NotNull
    @Column(name = "menu_date")
    private LocalDate menuDate = LocalDate.now();

    @Override
    public String toString() {
        return "menuItem:{" +
                "id:" + this.getId() + "; " +
                "name:" + this.getName() + "; " +
                "price:" + this.getPrice() + "; " +
                "dt:" + this.getMenuDate() + "; " +
                "rest_id:" + (this.restaurant != null ? this.restaurant.getId() : "null");

    }

    //https://stackoverflow.com/questions/18306040/jackson-deserialize-jsonidentityreference-alwaysasid-true
    @JsonProperty("restaurant")
    public void setRestaurantFromId(Integer id) {
        this.restaurant = new Restaurant(id);
    }

    public MenuItem(Integer id, String name, Restaurant restaurant, Integer price, LocalDate menuDate) {
        super(id, name);
        this.restaurant = restaurant;
        this.price = price;
        this.menuDate = menuDate;
    }
}
