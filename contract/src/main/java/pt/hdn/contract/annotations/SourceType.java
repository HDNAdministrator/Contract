package pt.hdn.contract.annotations;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({SourceType.NONE, SourceType.PERSON_PROFIT, SourceType.COMPANY_PROFIT, SourceType.TEAM_PROFIT, SourceType.DEPARTMENT_PROFIT, SourceType.DISTANCE, SourceType.TIME})

public @interface SourceType {
    int NONE = -1;
    int PERSON_PROFIT = 0;
    int TEAM_PROFIT = 1;
    int DEPARTMENT_PROFIT = 2;
    int COMPANY_PROFIT = 3;
    int DISTANCE = 4;
    int TIME = 5;
}
