package pt.hdn.contract.schema;

import pt.hdn.contract.annotations.SchemaType;
import pt.hdn.contract.annotations.SourceType;

public interface Schema  {
    @SchemaType int getType();

    @SourceType Integer getSource();

    double calculate(double value);

    double calculate();

    Schema.Builder rebuild();

    interface Builder {
        @SchemaType int getType();

        Schema create() throws SchemaException;

        boolean validate();
    }
}
