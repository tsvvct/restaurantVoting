package com.github.tsvvct.restaurantvoting.to;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserVoteTo {

    private Integer id;

    private Integer user;

    private Integer restaurant;

    private LocalDate voteDate;

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
