package com.github.tsvvct.restaurantvoting.util;

import com.github.tsvvct.restaurantvoting.model.UserVote;
import lombok.experimental.UtilityClass;
import com.github.tsvvct.restaurantvoting.to.UserVoteTo;

@UtilityClass
public class TestUtil {

    public static UserVoteTo getUserVoteToFromUser(UserVote userVote) {
        return new UserVoteTo(userVote.getId(), userVote.getUser().getId(), userVote.getRestaurant().getId(),
                userVote.getVoteDate());
    }
}
