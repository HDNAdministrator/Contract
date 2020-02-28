package pt.hdn.contract.schema;

import android.os.Parcel;

import com.google.gson.JsonObject;

import java.util.Objects;

import pt.hdn.contract.annotations.SchemaType;
import pt.hdn.contract.annotations.SourceType;

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
    private static final String RATE = "rate";
    private static final String SOURCE = "source";
    private static final String LOWER_BOUND = "lowerBound";
    private static final String UPPER_BOUND = "upperBound";

    private final double cut;
    private final Double lowerBound;
    private final Double upperBound;

    public static final CommissionSchema deserialize(JsonObject json){
        Builder builder = new Builder();
        builder.cut = json.get(RATE).getAsDouble();
        builder.source = json.get(SOURCE).getAsInt();
        builder.lowerBound = json.has(LOWER_BOUND) ? json.get(LOWER_BOUND).getAsDouble() : null;
        builder.upperBound = json.has(UPPER_BOUND) ? json.get(UPPER_BOUND).getAsDouble() : null;

        return new CommissionSchema(builder);
    }

    private CommissionSchema(Builder builder){
        super(SchemaType.COMMISSION, builder.source);

        this.cut = builder.cut;
        this.lowerBound = builder.lowerBound;
        this.upperBound = builder.upperBound;
    }

    private CommissionSchema(Parcel in){
        super(in);

        this.cut = in.readDouble();
        this.upperBound = in.readByte() != 0 ? in.readDouble() : null;
        this.lowerBound = in.readByte() != 0 ? in.readDouble() : null;
    }

    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeDouble(this.cut);

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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        } else {
            CommissionSchema commissionSchema = (CommissionSchema) obj;

            return Double.compare(commissionSchema.cut, this.cut) == 0 &&
                    Objects.equals(this.lowerBound, commissionSchema.lowerBound) &&
                    Objects.equals(this.upperBound, commissionSchema.upperBound);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(cut, lowerBound, upperBound);
    }

    @Override
    public final double calculate() {
        throw new RuntimeException("Schema depends on external value.");
    }

    @Override
    public final double calculate(double value){
        return (this.upperBound == null || value <= this.upperBound) ? ((this.lowerBound == null || this.lowerBound <= value) ? value * this.cut : 0d) : 0d;
    }

    @Override
    public final Builder rebuild() {
        Builder builder = new Builder();

        builder.source = super.source;
        builder.cut = this.cut;
        builder.lowerBound = this.lowerBound;
        builder.upperBound = this.upperBound;

        return builder;
    }

    public final double getCut() {
        return this.cut;
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

        private Double cut;
        private @SourceType Integer source;
        private Double lowerBound;
        private Double upperBound;

        public Builder(){
            super(SchemaType.COMMISSION);

            this.cut = null;
            this.source = null;
            this.lowerBound = null;
            this.upperBound = null;
        }

        @Override
        public final CommissionSchema create() throws SchemaException{
            if(this.cut == null) {
                throw new SchemaException("The cut is missing.");
            } else if(this.cut < 0 || this.cut > 1) {
                throw new SchemaException("Cut can only be between 0 and 1.");
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
                return new CommissionSchema(this);
            }
        }

        @Override
        public final boolean validate() {
            return this.cut != null && this.source != null;
        }

        public final boolean hasCut(){
            return this.cut != null;
        }

        public final boolean hasSource(){
            return this.source != null;
        }

        public final boolean hasLowerBound(){
            return this.lowerBound != null;
        }

        public final boolean hasUpperBound(){
            return this.upperBound != null;
        }

        public final Double getCut() {
            return this.cut;
        }

        public final Builder setCut(Double cut){
            this.cut = cut;

            return this;
        }

        public final @SourceType Integer getSource() {
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
