package ru.javaops.topjava.util;

import lombok.experimental.UtilityClass;
import ru.javaops.topjava.model.UserVote;
import ru.javaops.topjava.to.UserVoteTo;

@UtilityClass
public class TestUtil {

    public static UserVoteTo getUserVoteToFromUser(UserVote userVote) {
        return new UserVoteTo(userVote.getId(), userVote.getUser().getId(), userVote.getRestaurant().getId(),
                userVote.getVoteDate());
    }
}
