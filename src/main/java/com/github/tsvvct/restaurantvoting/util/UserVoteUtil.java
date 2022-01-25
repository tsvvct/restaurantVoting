package com.github.tsvvct.restaurantvoting.util;

import com.github.tsvvct.restaurantvoting.model.Restaurant;
import com.github.tsvvct.restaurantvoting.model.User;
import com.github.tsvvct.restaurantvoting.model.UserVote;
import com.github.tsvvct.restaurantvoting.to.UserVoteTo;

import java.util.Collection;
import java.util.List;

public class UserVoteUtil {

    public static UserVote createVoteFromTo(UserVoteTo voteTo, User user, Restaurant restaurant) {
        return new UserVote(voteTo.getId(), user, restaurant, voteTo.getVoteDate());
    }

    public static UserVoteTo createToFromVote(UserVote vote) {
        if (vote == null) {
            return null;
        } else {
            return new UserVoteTo(vote.getId(), vote.getUser().getId(), vote.getRestaurant().getId(), vote.getVoteDate());
        }
    }

    public static List<UserVoteTo> getListToFromListOfVote(Collection<UserVote> userVotes) {
        return userVotes.stream().map(UserVoteUtil::createToFromVote).toList();
    }
}
