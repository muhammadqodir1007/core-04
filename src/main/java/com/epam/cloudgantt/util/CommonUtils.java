package com.epam.cloudgantt.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CommonUtils {

    public static int getDiffTwoDateInDays(LocalDateTime first, LocalDateTime second) {
        return (int) ChronoUnit.DAYS.between(first, second);
    }


}
