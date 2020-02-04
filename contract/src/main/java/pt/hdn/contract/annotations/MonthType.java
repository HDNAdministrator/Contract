package pt.hdn.contract.annotations;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({MonthType.MONTHS, MonthType.PERIOD})
public  @interface MonthType {
    int MONTHS = 0;
    int PERIOD = 1;
}
