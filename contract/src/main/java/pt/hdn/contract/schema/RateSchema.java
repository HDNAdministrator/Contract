package pt.hdn.contract.schema;

import android.os.Parcel;

import androidx.annotation.Nullable;

import pt.hdn.contract.annotations.SourceType;

import static pt.hdn.contract.annotations.SchemaType.RATE;

public final class RateSchema extends SchemaImp {

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
    private final @SourceType int source;

    private RateSchema(Builder builder) {
        super(RATE);

        this.source = builder.source;
        this.rate = builder.rate;
    }

    private RateSchema(Parcel in){
        super(RATE);

        this.source = in.readInt();
        this.rate = in.readDouble();
    }

    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(source);
        dest.writeDouble(rate);
    }

    @Override
    public final int describeContents() {
        return 0;
    }

    @Override
    public final Builder rebuild() {
        Builder builder = new Builder();

        builder.source = this.source;
        builder.rate = this.rate;

        return builder;
    }

    @Override
    public final double calculate() {
        throw new RuntimeException("Schema depends on external value.");
    }

    @Override
    public final double calculate(double value) {
        return rate * value;
    }

    @Override
    public final boolean equals(@Nullable Object object) {
        if(!super.equals(object)){
            if(!(object instanceof RateSchema)) {
                return false;
            }

            RateSchema rateSchema = (RateSchema) object;

            if(this.rate != rateSchema.rate){
                return false;
            }

            if(this.source != rateSchema.source){
                return false;
            }
        }

        return true;
    }

    public final double getRate() {
        return rate;
    }

    public final static class Builder implements Schema.Builder{

        private Double rate;
        private @SourceType Integer source;

        public Builder(){
            this.rate = null;
        }

        @Override
        public final RateSchema create() throws SchemaException{
            if(rate == null) {
                throw new SchemaException("The rate is missing.");
            } else if(rate < 0) {
                throw new SchemaException("Value needs to be positive.");
            } else {
                return new RateSchema(this);
            }
        }

        @Override
        public final boolean validate() {
            return rate != null;
        }

        public final Integer getSource() {
            return source;
        }

        public final Builder setSource(@SourceType Integer source){
            this.source = source;

            return this;
        }

        public final Double getRate() {
            return rate;
        }

        public final Builder setRate(Double rate){
            this.rate = rate;

            return this;
        }
    }
}
