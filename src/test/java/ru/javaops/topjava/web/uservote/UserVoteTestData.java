package ru.javaops.topjava.web.uservote;

import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.model.UserVote;
import ru.javaops.topjava.web.MatcherFactory;

import java.time.LocalDate;

import static ru.javaops.topjava.web.user.UserTestData.admin;
import static ru.javaops.topjava.web.user.UserTestData.user;

public class UserVoteTestData {
    public static final MatcherFactory.Matcher<UserVote> USER_VOTE_MATCHER = MatcherFactory.usingEqualsComparator(UserVote.class);

    public static final int USER_VOTE1_ID = 1;

    private static final Restaurant restaurant1 = new Restaurant(1);
    private static final Restaurant restaurant2 = new Restaurant(2);
    private static final Restaurant restaurant3 = new Restaurant(3);

    public static final LocalDate testDate = LocalDate.now();

    public static final UserVote userVote1 = new UserVote(USER_VOTE1_ID, user, restaurant1, testDate.minusDays(1));
    public static final UserVote userVote2 = new UserVote(USER_VOTE1_ID + 1, user, restaurant2, testDate);
    public static final UserVote adminVote1 = new UserVote(USER_VOTE1_ID + 2, admin, restaurant1, testDate.minusDays(1));

    public static UserVote getNew() {
        return new UserVote(null, admin, restaurant1, testDate);
    }

    public static UserVote getUpdated() {
        return new UserVote(USER_VOTE1_ID + 1, user, restaurant1, testDate);
    }
}
