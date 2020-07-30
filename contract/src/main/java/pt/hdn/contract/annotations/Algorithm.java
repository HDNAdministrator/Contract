package pt.hdn.contract.annotations;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@StringDef({Algorithm.SHA512withRSA})
public @interface Algorithm {
    String SHA512withRSA = "SHA512withRSA";
}