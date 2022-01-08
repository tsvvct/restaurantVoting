package ru.javaops.topjava.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class UserVote extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    Restaurant restaurant;

    @Column(name = "vote_date")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    LocalDate voteDate = LocalDate.now();

    public UserVote(Integer id, User user, Restaurant restaurant, LocalDate voteDate) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.voteDate = voteDate;
    }

    //https://stackoverflow.com/questions/18306040/jackson-deserialize-jsonidentityreference-alwaysasid-true
    @JsonProperty("restaurant")
    public void setRestaurantFromId(Integer id) {
        this.restaurant = new Restaurant(id);
    }

    @Override
    public String toString() {
        return "UserVote{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", restaurant=" + restaurant.getId() +
                ", voteDate=" + voteDate +
                '}';
    }
}
