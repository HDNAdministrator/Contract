package pt.hdn.contract.annotations;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@StringDef({Parameter.FIX, Parameter.RATE, Parameter.CUT, Parameter.BONUS, Parameter.SOURCE, Parameter.LOWER_BOUND, Parameter.UPPER_BOUND, Parameter.NEGATIVE_THRESHOLD, Parameter.POSITIVE_THRESHOLD})
public @interface Parameter {
    String FIX = "fix";
    String RATE = "rate";
    String CUT = "cut";
    String BONUS = "bonus";
    String SOURCE = "Source";
    String LOWER_BOUND = "lowerBound";
    String UPPER_BOUND = "upperBound";
    String POSITIVE_THRESHOLD = "positiveThreshold";
    String NEGATIVE_THRESHOLD = "negativeThreshold";
}