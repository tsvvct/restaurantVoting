package com.github.tsvvct.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import java.time.LocalTime;

@UtilityClass
public class DateTimeUtil {
    private static LocalTime endVotingTime = LocalTime.of(23, 59);

    public static boolean isVotingOver() {
        return LocalTime.now().isAfter(endVotingTime);
    }
}
