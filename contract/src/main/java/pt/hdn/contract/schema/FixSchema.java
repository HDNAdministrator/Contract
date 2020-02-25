package pt.hdn.contract.schema;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.util.Objects;

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
        super(FIX, null);

        this.value = builder.value;
    }

    private FixSchema(Parcel in){
        super(in);

        this.value = in.readDouble();
    }

    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeDouble(this.value);
    }

    @Override
    public final int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        } else {
            FixSchema fixSchema = (FixSchema) obj;

            return Double.compare(fixSchema.value, this.value) == 0;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public final double calculate() {
        return this.value;
    }

    @Override
    public final double calculate(double value) {
        throw new RuntimeException("Schema doesn't depend on any external values.");
    }

    @Override
    public final Builder rebuild(){
        Builder builder = new Builder();

        builder.value = this.value;

        return builder;
    }

    public final double getValue() {
        return this.value;
    }

    public final static class Builder extends BuilderImp {

        private Double value;

        public Builder(){
            super(FIX);

            this.value = null;
        }

        @Override
        public final FixSchema create() throws SchemaException{
            if(this.value == null) {
                throw new SchemaException("The value is missing.");
            } else if(this.value < 0) {
                throw new SchemaException("Value needs to be positive.");
            } else {
                return new FixSchema(this);
            }
        }

        @Override
        public final boolean validate() {
            return this.value != null;
        }

        public final Double getValue() {
            return this.value;
        }

        public final Builder setValue(Double value){
            this.value = value;

            return this;
        }
    }
}
