package pt.hdn.contract.schema;

import android.os.Parcel;

import androidx.annotation.Nullable;

import pt.hdn.contract.annotations.SourceType;

import static pt.hdn.contract.annotations.SchemaType.THRESHOLD;

public final class ThresholdSchema extends SchemaImp {

    public static final Creator<ThresholdSchema> CREATOR = new Creator<ThresholdSchema>() {
        @Override
        public ThresholdSchema createFromParcel(Parcel in) {
            return new ThresholdSchema(in);
        }

        @Override
        public ThresholdSchema[] newArray(int size) {
            return new ThresholdSchema[size];
        }
    };

    private final double bonus;
    private final @SourceType int source;
    private final Double positiveThreshold;
    private final Double negativeThreshold;
    private boolean accomplish;

    private ThresholdSchema(Builder builder){
        super(THRESHOLD);

        this.source = builder.source;
        this.bonus = builder.bonus;
        this.positiveThreshold = builder.positiveThreshold;
        this.negativeThreshold = builder.negativeThreshold;
        this.accomplish = false;
    }

    private ThresholdSchema(Parcel in){
        super(THRESHOLD);

        this.bonus = in.readDouble();
        this.source = in.readInt();
        this.negativeThreshold = in.readByte() != 0 ? in.readDouble() : null;
        this.positiveThreshold = in.readByte() != 0 ? in.readDouble() : null;
        this.accomplish = in.readByte() != 0;
    }

    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.bonus);
        dest.writeInt(this.source);

        if(this.negativeThreshold == null){
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(this.negativeThreshold);
        }

        if(this.positiveThreshold == null){
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(this.positiveThreshold);
        }

        dest.writeByte((byte) (this.accomplish ? 1 : 0));
    }

    @Override
    public final int describeContents() {
        return 0;
    }

    @Override
    public final Builder rebuild() {
        Builder builder = new Builder();

        builder.bonus = this.bonus;
        builder.source = this.source;
        builder.positiveThreshold = this.positiveThreshold;
        builder.negativeThreshold = this.negativeThreshold;

        return builder;
    }

    @Override
    public final double calculate() {
        throw new RuntimeException("Schema depends on external values.");
    }

    @Override
    public final double calculate(double value){
        if((this.negativeThreshold == null || value <= this.negativeThreshold) || (this.positiveThreshold == null || this.positiveThreshold <= value)){
            this.accomplish = true;
        }

        return this.accomplish ? this.bonus : 0d;
    }

    @Override
    public final boolean equals(@Nullable Object object) {
        if(!super.equals(object)){
            if(!(object instanceof ThresholdSchema)) {
                return false;
            }

            ThresholdSchema thresholdSchema = (ThresholdSchema) object;

            if((this.bonus != thresholdSchema.bonus) || (this.source != thresholdSchema.source)){
                return false;
            }

            if((this.positiveThreshold == null ^ thresholdSchema.positiveThreshold == null) || (this.positiveThreshold != null && !this.positiveThreshold.equals(thresholdSchema.positiveThreshold))){
                return false;
            }

            if((this.negativeThreshold == null ^ thresholdSchema.negativeThreshold == null) || (this.negativeThreshold != null && !this.negativeThreshold.equals(thresholdSchema.negativeThreshold))){
                return false;
            }
        }

        return true;
    }

    public final double getBonus() {
        return this.bonus;
    }

    public final int getSource() {
        return this.source;
    }

    public final boolean hasPositiveThreshold(){
        return this.positiveThreshold != null;
    }

    public final Double getPositiveThreshold() {
        return this.positiveThreshold;
    }

    public final boolean hasNegativeThreshold(){
        return this.negativeThreshold != null;
    }

    public final Double getNegativeThreshold() {
        return this.negativeThreshold;
    }

    public final static class  Builder extends BuilderImp{

        private Double bonus;
        private Integer source;
        private Double positiveThreshold;
        private Double negativeThreshold;

        public Builder(){
            super(THRESHOLD);

            this.bonus = null;
            this.source = null;
            this.positiveThreshold = null;
            this.negativeThreshold = null;
        }

        @Override
        public final ThresholdSchema create() throws SchemaException{
            if(this.bonus == null) {
                throw new SchemaException("The bonus is missing.");
            } else if(this.bonus < 0) {
                throw new SchemaException("Bonus needs to be positive.");
            } else if(this.source == null) {
                throw new SchemaException("The source is missing.");
            } else if(this.source < 0) {
                throw new SchemaException("Source needs to be positive.");
            } else if(this.positiveThreshold == null && this.negativeThreshold == null) {
                throw new SchemaException("One bound needs to be set.");
            } else if(this.positiveThreshold != null && this.positiveThreshold < 0) {
                throw new SchemaException("LowerBound needs to be positive.");
            } else if(this.negativeThreshold != null && this.negativeThreshold < 0) {
                throw new SchemaException("UpperBound needs to be positive.");
            } else if(this.positiveThreshold != null && this.negativeThreshold != null) {
                throw new SchemaException("Only one bound can be set.");
            } else {
                return new ThresholdSchema(this);
            }
        }

        @Override
        public final boolean validate() {
            return this.bonus != null && this.source != null;
        }

        public final Double getBonus() {
            return this.bonus;
        }

        public final Builder setBonus(Double bonus){
            this.bonus = bonus;

            return this;
        }

        public final Integer getSource() {
            return this.source;
        }

        public final Builder setSource(@SourceType Integer source){
            this.source = source;

            return this;
        }

        public final Double getPositiveThreshold() {
            return this.positiveThreshold;
        }

        public final Builder setPositiveThreshold(Double positiveThreshold){
            this.positiveThreshold = positiveThreshold;

            return this;
        }

        public final Double getNegativeThreshold() {
            return this.negativeThreshold;
        }

        public final Builder setNegativeThreshold(Double negativeThreshold){
            this.negativeThreshold = negativeThreshold;

            return this;
        }
    }
}
