package com.github.tsvvct.restaurantvoting.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.tsvvct.restaurantvoting.HasIdAndRestaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.github.tsvvct.restaurantvoting.util.ChildAsIdOnlySerializer;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_vote", uniqueConstraints = {@UniqueConstraint(name = "user_voted_idx",
        columnNames = {"user_id", "vote_date"})})
@Getter
@Setter
@JsonPropertyOrder({"id", "user", "restaurant", ""})
@NoArgsConstructor
public class UserVote extends BaseEntity implements HasIdAndRestaurant {
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonSerialize(using = ChildAsIdOnlySerializer.class)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonSerialize(using = ChildAsIdOnlySerializer.class)
    private Restaurant restaurant;

    @Column(name = "vote_date")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate voteDate = LocalDate.now();

    public UserVote(Integer id, User user, Restaurant restaurant, LocalDate voteDate) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.voteDate = voteDate;
    }

    @JsonProperty("restaurant")
    public void setRestaurantFromId(Integer id) {
        this.restaurant = new Restaurant(id, null);
    }

    @Override
    public String toString() {
        return "UserVote:{" +
                "id:" + id + "; " +
                "restaurant:" + restaurant.getId() + "; " +
                "voteDate:" + voteDate +
                '}';
    }
}
