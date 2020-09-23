package pt.hdn.contract.schema;

import android.os.Parcel;

import com.google.gson.JsonObject;

import java.util.Objects;

import pt.hdn.contract.annotations.SchemaType;
import pt.hdn.contract.annotations.Parameter;
import pt.hdn.contract.annotations.SourceType;


public final class FixSchema extends SchemaImp {

    //region vars
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

    private final double fix;
    //endregion vars

    public static final FixSchema deserialize(JsonObject json){
        Builder builder = new Builder();
        builder.fix = json.get(Parameter.FIX).getAsDouble();

        return new FixSchema(builder);
    }

    private FixSchema(Builder builder) {
        super(SchemaType.FIX, null);

        this.fix = builder.fix;
    }

    private FixSchema(Parcel in){
        super(in);

        this.fix = in.readDouble();
    }

    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeDouble(this.fix);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        } else {
            FixSchema fixSchema = (FixSchema) obj;

            return Double.compare(fixSchema.fix, this.fix) == 0;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(fix);
    }

    @Override
    public final double calculate(double toBeIgnored) {
        return this.fix;
    }

    @Override
    public final Builder rebuild(){
        Builder builder = new Builder();

        builder.fix = this.fix;

        return builder;
    }

    public final double getFix() {
        return this.fix;
    }

    public final static class Builder extends BuilderImp {

        //region vars
        public static final Creator<Builder> CREATOR = new Creator<Builder>() {
            @Override
            public Builder createFromParcel(Parcel in) {
                return new Builder(in);
            }

            @Override
            public Builder[] newArray(int size) {
                return new Builder[size];
            }
        };

        private Double fix;
        //endregion vars

        public static final Builder deserialize(JsonObject json){
            Builder builder = new Builder();
            builder.fix = json.get(Parameter.FIX).getAsDouble();

            return builder;
        }

        public Builder(){
            super(SchemaType.FIX);

            this.fix = null;
        }

        private Builder(Parcel in) {
            super(in);

            this.fix = in.readByte() != 0 ? in.readDouble() : null;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);

            if(this.fix == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(this.fix);
            }
        }

        @Override
        public final FixSchema create() throws SchemaException{
            if(this.fix == null) {
                throw new SchemaException("The value is missing.");
            } else if(this.fix < 0) {
                throw new SchemaException("Value needs to be positive.");
            } else {
                return new FixSchema(this);
            }
        }

        @Override
        public final boolean validate() {
            return this.fix != null && this.fix > 0;
        }

        public final boolean hasValue(){
            return this.fix != null;
        }

        public final Double getFix() {
            return this.fix;
        }

        public final Builder setFix(Double fix){
            this.fix = fix;

            return this;
        }
    }
}
