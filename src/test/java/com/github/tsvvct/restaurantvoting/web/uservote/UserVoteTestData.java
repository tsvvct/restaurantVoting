package com.github.tsvvct.restaurantvoting.web.uservote;

import com.github.tsvvct.restaurantvoting.web.restaurant.RestaurantTestData;
import com.github.tsvvct.restaurantvoting.to.UserVoteTo;
import com.github.tsvvct.restaurantvoting.web.MatcherFactory;

import java.time.LocalDate;

import static com.github.tsvvct.restaurantvoting.web.user.UserTestData.*;

public class UserVoteTestData {
    public static final MatcherFactory.Matcher<UserVoteTo> USERTO_VOTE_MATCHER = MatcherFactory.usingEqualsComparator(UserVoteTo.class);

    public static final int USER_VOTE1_ID = 1;

    public static final LocalDate testDate = LocalDate.now();

    public static final UserVoteTo userVote1 = new UserVoteTo(USER_VOTE1_ID, USER_ID, RestaurantTestData.RESTAURANT1_ID, testDate.minusDays(1));
    public static final UserVoteTo userVote2 = new UserVoteTo(USER_VOTE1_ID + 1, USER_ID, RestaurantTestData.RESTAURANT2_ID, testDate);

    public static UserVoteTo getNew() {
        return new UserVoteTo(null, ADMIN_ID, RestaurantTestData.RESTAURANT1_ID, testDate);
    }

    public static UserVoteTo getUpdated() {
        return new UserVoteTo(USER_VOTE1_ID + 1, USER_ID, RestaurantTestData.RESTAURANT1_ID, testDate);
    }
}
