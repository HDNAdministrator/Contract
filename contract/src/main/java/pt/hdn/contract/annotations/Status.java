package pt.hdn.contract.annotations;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({Status.INVALID, Status.EXPIRED, Status.PENDING, Status.VALID})
public @interface Status {
    int INVALID = -2;
    int EXPIRED = -1;
    int PENDING = 0;
    int VALID = 1;
}