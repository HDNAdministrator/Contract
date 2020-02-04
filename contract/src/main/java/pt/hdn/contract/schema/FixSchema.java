package pt.hdn.contract.schema;

import android.os.Parcel;
import android.os.Parcelable;

import pt.hdn.contract.annotations.SchemaType;

import static pt.hdn.contract.annotations.SchemaType.FIX;

public final class FixSchema extends SchemaImp {

    public static final Creator<FixSchema> CREATOR = new Creator<FixSchema>() {
        @Override
        public FixSchema createFromParcel(Parcel in) {
            return new FixSchema(in);
        }

        @Override
        public FixSchema[] newArray(int size) {
            return new FixSchema[size];
        }
    };

    private final double value;

    private FixSchema(Builder builder) {
        super(FIX);

        this.value = builder.value;
    }

    private FixSchema(Parcel in){
        super(FIX);

        this.value = in.readDouble();
    }

    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(value);
    }

    @Override
    public final int describeContents() {
        return 0;
    }

    @Override
    public double calculate() {
        return value;
    }

    @Override
    public double calculate(double value) {
        throw new RuntimeException("Schema doesn't depend on any external values.");
    }

    @Override
    public final Builder rebuild(){
        Builder builder = new Builder();

        builder.value = this.value;

        return builder;
    }

    public final double getValue() {
        return value;
    }

    public static class Builder implements Schema.Builder {

        private Double value;

        public Builder(){
            this.value = null;
        }

        @Override
        public FixSchema create() throws SchemaException{
            if(value == null) {
                throw new SchemaException("The value is missing.");
            } else if(value < 0) {
                throw new SchemaException("Value needs to be positive.");
            } else {
                return new FixSchema(this);
            }
        }

        @Override
        public boolean validate() {
            return value != null;
        }

        public Double getValue() {
            return value;
        }

        public Builder setValue(Double value){
            this.value = value;

            return this;
        }
    }
}
