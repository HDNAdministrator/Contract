package pt.hdn.contract.annotations;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({SchemaType.FIX, SchemaType.RATE, SchemaType.COMMISSION, SchemaType.OBJECTIVE, SchemaType.THRESHOLD})

public @interface SchemaType {
    int FIX = 0;
    int RATE = 1;
    int COMMISSION = 2;
    int OBJECTIVE = 3;
    int THRESHOLD = 4;
}
