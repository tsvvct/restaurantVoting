package ru.javaops.topjava.util;

import lombok.experimental.UtilityClass;
import java.time.LocalTime;

@UtilityClass
public class DateTimeUtil {
    private static LocalTime endVotingTime = LocalTime.of(21, 0);

    public static boolean checkVotingIsOver() {
        return LocalTime.now().isAfter(endVotingTime);
    }
}
