package ru.javaops.topjava.util;

import lombok.experimental.UtilityClass;
import ru.javaops.topjava.error.IllegalRequestDataException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class DateTimeUtil {
    private static LocalTime endVotingTime = LocalTime.of(11,00);

    public static boolean checkVotingIsOver() {
        return LocalTime.now().isAfter(endVotingTime);
    }
}
