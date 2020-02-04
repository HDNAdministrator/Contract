package pt.hdn.contract.schema;

import android.os.Parcel;

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
    public Schema.Builder rebuild() {
        Builder builder = new Builder();

        builder.source = this.source;
        builder.rate = this.rate;

        return builder;
    }

    @Override
    public double calculate() {
        throw new RuntimeException("Schema does depend on any external values.");
    }

    @Override
    public double calculate(double value) {
        return rate * value;
    }

    public final double getRate() {
        return rate;
    }

    public static class Builder implements Schema.Builder{

        private Double rate;
        private @SourceType Integer source;

        public Builder(){
            this.rate = null;
        }

        @Override
        public RateSchema create() throws SchemaException{
            if(rate == null) {
                throw new SchemaException("The rate is missing.");
            } else if(rate < 0) {
                throw new SchemaException("Value needs to be positive.");
            } else {
                return new RateSchema(this);
            }
        }

        @Override
        public boolean validate() {
            return rate != null;
        }

        public Integer getSource() {
            return source;
        }

        public Builder setSource(@SourceType Integer source){
            this.source = source;

            return this;
        }

        public Double getRate() {
            return rate;
        }

        public Builder setRate(Double rate){
            this.rate = rate;

            return this;
        }
    }
}
