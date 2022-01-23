package com.github.tsvvct.restaurantvoting.util;

import com.github.tsvvct.restaurantvoting.web.uservote.UserVoteTestData;
import lombok.experimental.UtilityClass;

import java.time.*;

@UtilityClass
public class DateTimeUtil {
    private static LocalTime endVotingTime = LocalTime.of(11,0);
    private static Clock clock;
    static {
        setDefaultClock();
    }

    public static boolean isVotingOver() {
        boolean result = LocalTime.now(clock).isAfter(endVotingTime);
        setDefaultClock();
        return result;
    }

    private static void setDefaultClock() {
        clock = Clock.fixed(Instant.parse(UserVoteTestData.testDate + "T10:59:59.00Z"), ZoneId.of("UTC"));
    }

    public static void moveClock(int seconds) {
        clock = Clock.offset(clock, Duration.ofSeconds(seconds));
    }
}
