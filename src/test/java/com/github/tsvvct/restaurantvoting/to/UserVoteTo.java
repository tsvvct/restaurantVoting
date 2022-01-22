package com.github.tsvvct.restaurantvoting.to;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserVoteTo {

    Integer id;

    Integer user;

    Integer restaurant;

    LocalDate voteDate;

    @Override
    public String toString() {
        return "UserVoteTo:{" +
                "id:" + id + "; " +
                "user:" + user + "; " +
                "restaurant:" + restaurant + "; " +
                "voteDate:" + voteDate +
                '}';
    }
}
