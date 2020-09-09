package pt.hdn.contract.annotations;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({MonthsPeriod.MONTHS_2, MonthsPeriod.MONTHS_3, MonthsPeriod.MONTHS_4, MonthsPeriod.MONTHS_6, MonthsPeriod.MONTHS_ALL})
public @interface MonthsPeriod {
    int MONTHS_ALL = 1;
    int MONTHS_2 = 2;
    int MONTHS_3 = 3;
    int MONTHS_4 = 4;
    int MONTHS_6 = 6;
}