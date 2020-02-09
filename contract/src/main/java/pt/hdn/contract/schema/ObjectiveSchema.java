package pt.hdn.contract.schema;

import android.os.Parcel;

import androidx.annotation.Nullable;

import pt.hdn.contract.annotations.SourceType;

import static pt.hdn.contract.annotations.SchemaType.COMMISSION;
import static pt.hdn.contract.annotations.SchemaType.OBJECTIVE;

public class ObjectiveSchema extends SchemaImp {

    public static final Creator<ObjectiveSchema> CREATOR = new Creator<ObjectiveSchema>() {
        @Override
        public ObjectiveSchema createFromParcel(Parcel in) {
            return new ObjectiveSchema(in);
        }

        @Override
        public ObjectiveSchema[] newArray(int size) {
            return new ObjectiveSchema[size];
        }
    };

    private final double bonus;
    private final @SourceType int source;
    private final Double lowerBound;
    private final Double upperBound;

    private ObjectiveSchema(Builder builder){
        super(OBJECTIVE);

        this.source = builder.source;
        this.bonus = builder.bonus;
        this.lowerBound = builder.lowerBound;
        this.upperBound = builder.upperBound;
    }

    private ObjectiveSchema(Parcel in){
        super(OBJECTIVE);

        this.bonus = in.readDouble();
        this.source = in.readInt();
        this.upperBound = in.readByte() != 0 ? in.readDouble() : null;
        this.lowerBound = in.readByte() != 0 ? in.readDouble() : null;
    }

    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(bonus);
        dest.writeInt(source);

        if(upperBound == null){
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(upperBound);
        }

        if(lowerBound == null){
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lowerBound);
        }
    }

    @Override
    public final int describeContents() {
        return 0;
    }

    @Override
    public Schema.Builder rebuild() {
        Builder builder = new Builder();

        builder.bonus = this.bonus;
        builder.source = this.source;
        builder.lowerBound = this.lowerBound;
        builder.upperBound = this.upperBound;

        return builder;
    }

    @Override
    public double calculate() {
        throw new RuntimeException("Schema depends on external values.");
    }

    @Override
    public double calculate(double value){
        return (upperBound == null || value <= upperBound) ? ((lowerBound == null || lowerBound <= value) ? bonus : 0d) : 0d;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if(!super.equals(object)){
            if(!(object instanceof ObjectiveSchema)) {
                return false;
            }

            ObjectiveSchema objectiveSchema = (ObjectiveSchema) object;

            if(this.bonus != objectiveSchema.bonus){
                return false;
            }

            if(this.source != objectiveSchema.source){
                return false;
            }

            if((this.lowerBound == null ^ objectiveSchema.lowerBound == null) || (this.lowerBound != null && !this.lowerBound.equals(objectiveSchema.lowerBound))){
                return false;
            }

            if((this.upperBound == null ^ objectiveSchema.upperBound == null) || (this.upperBound != null && !this.upperBound.equals(objectiveSchema.upperBound))){
                return false;
            }
        }

        return true;
    }

    public final boolean hasLowerBound(){
        return lowerBound != null;
    }

    public final Double getLowerBound() {
        return lowerBound;
    }

    public final boolean hasUpperBound(){
        return upperBound != null;
    }

    public final Double getUpperBound() {
        return upperBound;
    }

    public static class  Builder implements Schema.Builder{

        private Double bonus;
        private Integer source;
        private Double lowerBound;
        private Double upperBound;

        public Builder(){
            this.bonus = null;
            this.source = null;
            this.lowerBound = null;
            this.upperBound = null;
        }

        @Override
        public ObjectiveSchema create() throws SchemaException{
            if(bonus == null) {
                throw new SchemaException("The cut is missing.");
            } else if(bonus < 0 || bonus > 1) {
                throw new SchemaException("Cut can only be between 0 and 1.");
            } else if(source == null) {
                throw new SchemaException("The source is missing.");
            } else if(source < 0) {
                throw new SchemaException("Source needs to be positive.");
            } else if(lowerBound != null && lowerBound < 0) {
                throw new SchemaException("LowerBound needs to be positive.");
            } else if(upperBound != null && upperBound < 0) {
                throw new SchemaException("UpperBound needs to be positive.");
            } else if(lowerBound != null && upperBound != null && upperBound < lowerBound) {
                throw new SchemaException("LowerBound is greater than upperBound");
            } else {
                return new ObjectiveSchema(this);
            }
        }

        @Override
        public boolean validate() {
            return bonus != null && source != null;
        }

        public Double getBonus() {
            return bonus;
        }

        public Builder setBonus(Double bonus){
            this.bonus = bonus;

            return this;
        }

        public Integer getSource() {
            return source;
        }

        public Builder setSource(@SourceType Integer source){
            this.source = source;

            return this;
        }

        public Double getLowerBound() {
            return lowerBound;
        }

        public Builder setLowerBound(Double lowerBound){
            this.lowerBound = lowerBound;

            return this;
        }

        public Double getUpperBound() {
            return upperBound;
        }

        public Builder setUpperBound(Double upperBound){
            this.upperBound = upperBound;

            return this;
        }
    }
}
