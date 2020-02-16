package pt.hdn.contract.schema;

import android.os.Parcel;

import androidx.annotation.Nullable;

import pt.hdn.contract.annotations.SourceType;

import static pt.hdn.contract.annotations.SchemaType.COMMISSION;
import static pt.hdn.contract.annotations.SchemaType.OBJECTIVE;

public final class ObjectiveSchema extends SchemaImp {

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
        dest.writeDouble(this.bonus);
        dest.writeInt(this.source);

        if(this.upperBound == null){
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(this.upperBound);
        }

        if(this.lowerBound == null){
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(this.lowerBound);
        }
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
        builder.lowerBound = this.lowerBound;
        builder.upperBound = this.upperBound;

        return builder;
    }

    @Override
    public final double calculate() {
        throw new RuntimeException("Schema depends on external values.");
    }

    @Override
    public final double calculate(double value){
        return (this.upperBound == null || value <= this.upperBound) ? ((this.lowerBound == null || this.lowerBound <= value) ? this.bonus : 0d) : 0d;
    }

    @Override
    public final boolean equals(@Nullable Object object) {
        if(!super.equals(object)){
            if(!(object instanceof ObjectiveSchema)) {
                return false;
            }

            ObjectiveSchema objectiveSchema = (ObjectiveSchema) object;

            if((this.bonus != objectiveSchema.bonus) || (this.source != objectiveSchema.source)){
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

    public final double getBonus() {
        return this.bonus;
    }

    public final int getSource() {
        return this.source;
    }

    public final boolean hasLowerBound(){
        return this.lowerBound != null;
    }

    public final Double getLowerBound() {
        return this.lowerBound;
    }

    public final boolean hasUpperBound(){
        return this.upperBound != null;
    }

    public final Double getUpperBound() {
        return this.upperBound;
    }

    public final static class  Builder extends BuilderImp{

        private Double bonus;
        private Integer source;
        private Double lowerBound;
        private Double upperBound;

        public Builder(){
            super(OBJECTIVE);

            this.bonus = null;
            this.source = null;
            this.lowerBound = null;
            this.upperBound = null;
        }

        @Override
        public final ObjectiveSchema create() throws SchemaException{
            if(this.bonus == null) {
                throw new SchemaException("The bonus is missing.");
            } else if(this.bonus < 0) {
                throw new SchemaException("Bonus needs to be positive.");
            } else if(this.source == null) {
                throw new SchemaException("The source is missing.");
            } else if(this.source < 0) {
                throw new SchemaException("Source needs to be positive.");
            } else if(this.lowerBound != null && this.lowerBound < 0) {
                throw new SchemaException("LowerBound needs to be positive.");
            } else if(this.upperBound != null && this.upperBound < 0) {
                throw new SchemaException("UpperBound needs to be positive.");
            } else if(this.lowerBound != null && this.upperBound != null && this.upperBound < this.lowerBound) {
                throw new SchemaException("LowerBound is greater than upperBound");
            } else {
                return new ObjectiveSchema(this);
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

        public final Double getLowerBound() {
            return this.lowerBound;
        }

        public final Builder setLowerBound(Double lowerBound){
            this.lowerBound = lowerBound;

            return this;
        }

        public final Double getUpperBound() {
            return this.upperBound;
        }

        public final Builder setUpperBound(Double upperBound){
            this.upperBound = upperBound;

            return this;
        }
    }
}
