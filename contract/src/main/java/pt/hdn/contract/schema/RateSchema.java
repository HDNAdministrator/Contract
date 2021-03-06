package pt.hdn.contract.schema;

import android.os.Parcel;

import com.google.gson.JsonObject;

import java.util.Objects;

import pt.hdn.contract.annotations.SchemaType;
import pt.hdn.contract.annotations.SourceType;
import pt.hdn.contract.annotations.Parameter;

public final class RateSchema extends SchemaImp {

    //region vars
    public static final Creator<RateSchema> CREATOR = new Creator<RateSchema>() {
        @Override
        public RateSchema createFromParcel(Parcel in) {
            return new RateSchema(in);
        }

        @Override
        public RateSchema[] newArray(int size) {
            return new RateSchema[size];
        }
    };

    private final double rate;
    //endregion vars

    public static final RateSchema deserialize(JsonObject json){
        Builder builder = new Builder();
        builder.rate = json.get(Parameter.RATE).getAsDouble();
        builder.source = json.get(Parameter.SOURCE).getAsInt();

        return new RateSchema(builder);
    }

    private RateSchema(Builder builder) {
        super(SchemaType.RATE, builder.source);

        this.rate = builder.rate;
    }

    private RateSchema(Parcel in){
        super(in);

        this.rate = in.readDouble();
    }

    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeDouble(this.rate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        } else {
            RateSchema rateSchema = (RateSchema) obj;

            return Double.compare(rateSchema.rate, this.rate) == 0;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate);
    }

    @Override
    public final double calculate(double value) {
        return this.rate * value;
    }

    @Override
    public final Builder rebuild() {
        Builder builder = new Builder();

        builder.source = super.source;
        builder.rate = this.rate;

        return builder;
    }

    public final double getRate() {
        return this.rate;
    }

    public final static class Builder extends BuilderImp{

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

        private Double rate;
        private @SourceType Integer source;
        //endregion vars

        public static final Builder deserialize(JsonObject json){
            Builder builder = new Builder();
            builder.rate = json.get(Parameter.RATE).getAsDouble();

            return builder;
        }

        public Builder(){
            super(SchemaType.RATE);

            this.rate = null;
            this.source = null;
        }

        private Builder(Parcel in) {
            super(in);

            this.rate = in.readByte() != 0 ? in.readDouble() : null;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);

            if(this.rate == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(this.rate);
            }
        }
        @Override
        public final RateSchema create() throws SchemaException{
            if(this.rate == null) {
                throw new SchemaException("The rate is missing.");
            } else if(this.rate <= 0) {
                throw new SchemaException("Value needs to be positive.");
            } else if(this.source == null) {
                throw new SchemaException("The source is missing.");
            } else if(this.source < 0) {
                throw new SchemaException("Source needs to be positive.");
            } else {
                return new RateSchema(this);
            }
        }

        @Override
        public final boolean validate() {
            return this.rate != null && this.rate > 0;
        }

        public final boolean hasRate(){
            return this.rate != null;
        }

        public final boolean hasSource(){
            return this.source != null;
        }

        public final @SourceType Integer getSource() {
            return this.source;
        }

        public final Builder setSource(@SourceType Integer source){
            this.source = source;

            return this;
        }

        public final Double getRate() {
            return this.rate;
        }

        public final Builder setRate(Double rate){
            this.rate = rate;

            return this;
        }
    }
}
