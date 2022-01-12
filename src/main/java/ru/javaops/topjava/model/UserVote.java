package ru.javaops.topjava.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.javaops.topjava.HasIdAndRestaurant;
import ru.javaops.topjava.util.ChildAsIdOnlySerializer;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_vote", uniqueConstraints = {@UniqueConstraint(name = "user_voted_idx",
        columnNames = {"user_id", "vote_date"})})
@Getter
@Setter
@JsonPropertyOrder({"id", "user", "restaurant", ""})
@AllArgsConstructor
@NoArgsConstructor
public class UserVote extends BaseEntity implements HasIdAndRestaurant {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonSerialize(using = ChildAsIdOnlySerializer.class)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonSerialize(using = ChildAsIdOnlySerializer.class)
    Restaurant restaurant;

    @Column(name = "vote_date")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    LocalDate voteDate = LocalDate.now();

    @JsonProperty("restaurant")
    public void setRestaurantFromId(Integer id) {
        this.restaurant = new Restaurant(id, null);
    }

    @Override
    public String toString() {
        return "UserVote:{" +
                "id:" + id + "; " +
                "user:" + user.getId() + "; " +
                "restaurant:" + restaurant.getId() + "; " +
                "voteDate:" + voteDate +
                '}';
    }
}
