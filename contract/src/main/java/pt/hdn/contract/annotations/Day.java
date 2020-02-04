package pt.hdn.contract.annotations;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({Day.DAY_1, Day.DAY_2, Day.DAY_3, Day.DAY_4, Day.DAY_5, Day.DAY_6, Day.DAY_7, Day.DAY_8, Day.DAY_9, Day.DAY_10, Day.DAY_11, Day.DAY_12, Day.DAY_13, Day.DAY_14, Day.DAY_15, Day.DAY_16, Day.DAY_17, Day.DAY_18, Day.DAY_19, Day.DAY_20, Day.DAY_21, Day.DAY_22, Day.DAY_23, Day.DAY_24, Day.DAY_25, Day.DAY_26, Day.DAY_27, Day.DAY_28})
public @interface Day {
    int DAY_1 = 1;
    int DAY_2 = 2;
    int DAY_3 = 3;
    int DAY_4 = 4;
    int DAY_5 = 5;
    int DAY_6 = 6;
    int DAY_7 = 7;
    int DAY_8 = 8;
    int DAY_9 = 9;
    int DAY_10 = 10;
    int DAY_11 = 11;
    int DAY_12 = 12;
    int DAY_13 = 13;
    int DAY_14 = 14;
    int DAY_15 = 15;
    int DAY_16 = 16;
    int DAY_17 = 17;
    int DAY_18 = 18;
    int DAY_19 = 19;
    int DAY_20 = 20;
    int DAY_21 = 21;
    int DAY_22 = 22;
    int DAY_23 = 23;
    int DAY_24 = 24;
    int DAY_25 = 25;
    int DAY_26 = 26;
    int DAY_27 = 27;
    int DAY_28 = 28;
}