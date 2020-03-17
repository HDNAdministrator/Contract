package pt.hdn.contract.schema;

import android.os.Parcelable;

import pt.hdn.contract.annotations.SchemaType;
import pt.hdn.contract.annotations.SourceType;

public interface Schema extends Parcelable {
    @SchemaType int getType();

    @SourceType Integer getSource();

    double calculate(double value);

    Schema.Builder rebuild();

    interface Builder extends Parcelable{
        @SchemaType int getType();

        Schema create() throws SchemaException;

        boolean validate();
    }
}
