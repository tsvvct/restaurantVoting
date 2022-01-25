package com.github.tsvvct.restaurantvoting.to;

import com.github.tsvvct.restaurantvoting.HasRestaurantId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class UserVoteTo extends BaseTo implements HasRestaurantId {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer userId;
    @NotNull
    private Integer restaurantId;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate voteDate;

    public UserVoteTo(Integer id, Integer userId, Integer restaurantId, LocalDate voteDate) {
        super(id);
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.voteDate = voteDate;
    }

    @Override
    public String toString() {
        return "UserVoteTo:{" +
                "id:" + id + "; " +
                "user:" + userId + "; " +
                "restaurant:" + restaurantId + "; " +
                "voteDate:" + voteDate +
                "}";
    }
}
