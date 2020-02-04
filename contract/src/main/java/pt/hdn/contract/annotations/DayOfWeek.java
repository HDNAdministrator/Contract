package pt.hdn.contract.annotations;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY})
public @interface DayOfWeek {
    int MONDAY = 0;
    int TUESDAY = 1;
    int WEDNESDAY = 2;
    int THURSDAY = 3;
    int FRIDAY = 4;
    int SATURDAY = 5;
    int SUNDAY = 6;
}