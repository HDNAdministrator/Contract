package pt.hdn.contract.schema;

import pt.hdn.contract.annotations.SchemaType;

public interface Schema  {
    @SchemaType int getType();

    double calculate(double value);

    double calculate();

    Schema.Builder rebuild();

    interface Builder {
        Schema create() throws SchemaException;

        boolean validate();
    }
}
