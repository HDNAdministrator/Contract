package pt.hdn.contract.schema;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import pt.hdn.contract.annotations.SchemaType;
import pt.hdn.contract.annotations.SourceType;

import static pt.hdn.contract.annotations.SchemaType.COMMISSION;

public final class CommissionSchema extends SchemaImp {

    public static final Creator<CommissionSchema> CREATOR = new Creator<CommissionSchema>() {
        @Override
        public CommissionSchema createFromParcel(Parcel in) {
            return new CommissionSchema(in);
        }

        @Override
        public CommissionSchema[] newArray(int size) {
            return new CommissionSchema[size];
        }
    };

    private final double cut;
    private final @SourceType int source;
    private final Double lowerBound;
    private final Double upperBound;

    private CommissionSchema(Builder builder){
        super(COMMISSION);

        this.source = builder.source;
        this.cut = builder.cut;
        this.lowerBound = builder.lowerBound;
        this.upperBound = builder.upperBound;
    }

    private CommissionSchema(Parcel in){
        super(COMMISSION);

        this.cut = in.readDouble();
        this.source = in.readInt();
        this.upperBound = in.readByte() != 0 ? in.readDouble() : null;
        this.lowerBound = in.readByte() != 0 ? in.readDouble() : null;
    }

    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(cut);
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
    public final Builder rebuild() {
        Builder builder = new Builder();

        builder.cut = this.cut;
        builder.source = this.source;
        builder.lowerBound = this.lowerBound;
        builder.upperBound = this.upperBound;

        return builder;
    }

    @Override
    public final double calculate() {
        throw new RuntimeException("Schema depends on external value.");
    }

    @Override
    public final double calculate(double value){
        return (upperBound == null || value <= upperBound) ? ((lowerBound == null || lowerBound <= value) ? value * cut : 0d) : 0d;
    }

    @Override
    public final boolean equals(@Nullable Object object) {
        if(!super.equals(object)){
            if(!(object instanceof CommissionSchema)) {
                return false;
            }

            CommissionSchema commissionSchema = (CommissionSchema) object;

            if(this.cut != commissionSchema.cut){
                return false;
            }

            if(this.source != commissionSchema.source){
                return false;
            }

            if((this.lowerBound == null ^ commissionSchema.lowerBound == null) || (this.lowerBound != null && !this.lowerBound.equals(commissionSchema.lowerBound))){
                return false;
            }

            if((this.upperBound == null ^ commissionSchema.upperBound == null) || (this.upperBound != null && !this.upperBound.equals(commissionSchema.upperBound))){
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

    public final int getSource() {
        return source;
    }

    public final double getCut() {
        return cut;
    }

    public final static class  Builder implements Schema.Builder{

        private Double cut;
        private @SourceType Integer source;
        private Double lowerBound;
        private Double upperBound;

        public Builder(){
            this.cut = null;
            this.source = null;
            this.lowerBound = null;
            this.upperBound = null;
        }

        @Override
        public final CommissionSchema create() throws SchemaException{
            if(cut == null) {
                throw new SchemaException("The cut is missing.");
            } else if(cut < 0 || cut > 1) {
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
                return new CommissionSchema(this);
            }
        }

        @Override
        public final boolean validate() {
            return cut != null && source != null;
        }

        public final Double getCut() {
            return cut;
        }

        public final Builder setCut(Double cut){
            this.cut = cut;

            return this;
        }

        public final Integer getSource() {
            return source;
        }

        public final Builder setSource(@SourceType Integer source){
            this.source = source;

            return this;
        }

        public final Double getLowerBound() {
            return lowerBound;
        }

        public final Builder setLowerBound(Double lowerBound){
            this.lowerBound = lowerBound;

            return this;
        }

        public final Double getUpperBound() {
            return upperBound;
        }

        public final Builder setUpperBound(Double upperBound){
            this.upperBound = upperBound;

            return this;
        }
    }
}
