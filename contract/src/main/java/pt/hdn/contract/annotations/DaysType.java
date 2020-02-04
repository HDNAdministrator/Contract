package pt.hdn.contract.annotations;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({DaysType.DAYS, DaysType.DOW, DaysType.PERIOD})
public  @interface DaysType {
    int DAYS = 0;
    int DOW = 1;
    int PERIOD = 2;
}
