package com.github.tsvvct.restaurantvoting.util;

import lombok.experimental.UtilityClass;

import java.time.LocalTime;

@UtilityClass
public class DateTimeUtil {
    private static final LocalTime END_VOTING_TIME = LocalTime.of(11, 0);

    public static boolean isVotingOver() {
        return LocalTime.now().isAfter(END_VOTING_TIME);
    }
}
