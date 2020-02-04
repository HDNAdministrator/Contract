package pt.hdn.contract.annotations;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({DaysPeriod.WORK_DAYS, DaysPeriod.WEEKEND_DAYS, DaysPeriod.ALL_DAYS})
public @interface DaysPeriod {
    int WORK_DAYS = 0;
    int WEEKEND_DAYS = 1;
    int ALL_DAYS = 2;
}