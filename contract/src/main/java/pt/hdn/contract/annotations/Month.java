package pt.hdn.contract.annotations;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE, Month.JULY, Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER})
public @interface Month {
    int JANUARY = 0;
    int FEBRUARY = 1;
    int MARCH = 2;
    int APRIL = 3;
    int MAY = 4;
    int JUNE = 5;
    int JULY = 6;
    int AUGUST = 7;
    int SEPTEMBER = 8;
    int OCTOBER = 9;
    int NOVEMBER = 10;
    int DECEMBER = 11;
}