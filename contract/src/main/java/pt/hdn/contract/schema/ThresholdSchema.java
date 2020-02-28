package pt.hdn.contract.schema;

import android.os.Parcel;

import com.google.gson.JsonObject;

import java.util.Objects;

import pt.hdn.contract.annotations.SchemaType;
import pt.hdn.contract.annotations.SourceType;

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
    private static final String BONUS = "bonus";
    private static final String SOURCE = "accomplish";
    private static final String POSITIVE_THRESHOLD = "positiveThreshold";
    private static final String NEGATIVE_THRESHOLD = "negativeThreshold";

    private final double bonus;
    private final Double positiveThreshold;
    private final Double negativeThreshold;
    private transient boolean accomplish;

    public static final ThresholdSchema deserialize(JsonObject json){
        Builder builder = new Builder();
        builder.bonus = json.get(BONUS).getAsDouble();
        builder.source = json.get(SOURCE).getAsInt();
        builder.positiveThreshold = json.has(POSITIVE_THRESHOLD) ? json.get(POSITIVE_THRESHOLD).getAsDouble() : null;
        builder.negativeThreshold = json.has(NEGATIVE_THRESHOLD) ? json.get(NEGATIVE_THRESHOLD).getAsDouble() : null;

        return new ThresholdSchema(builder);
    }

    private ThresholdSchema(Builder builder){
        super(SchemaType.THRESHOLD, builder.source);

        this.bonus = builder.bonus;
        this.positiveThreshold = builder.positiveThreshold;
        this.negativeThreshold = builder.negativeThreshold;
        this.accomplish = false;
    }

    private ThresholdSchema(Parcel in){
        super(in);

        this.bonus = in.readDouble();
        this.negativeThreshold = in.readByte() != 0 ? in.readDouble() : null;
        this.positiveThreshold = in.readByte() != 0 ? in.readDouble() : null;
        this.accomplish = in.readByte() != 0;
    }

    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeDouble(this.bonus);

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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        } else {
            ThresholdSchema thresholdSchema = (ThresholdSchema) obj;

            return Double.compare(thresholdSchema.bonus, this.bonus) == 0 &&
                    this.accomplish == thresholdSchema.accomplish &&
                    Objects.equals(this.positiveThreshold, thresholdSchema.positiveThreshold) &&
                    Objects.equals(this.negativeThreshold, thresholdSchema.negativeThreshold);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(bonus, positiveThreshold, negativeThreshold, accomplish);
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
    public final Builder rebuild() {
        Builder builder = new Builder();

        builder.source = super.source;
        builder.bonus = this.bonus;
        builder.positiveThreshold = this.positiveThreshold;
        builder.negativeThreshold = this.negativeThreshold;

        return builder;
    }

    public final boolean hasAccomplish(){
        return this.accomplish;
    }

    public final double getBonus() {
        return this.bonus;
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
        private @SourceType Integer source;
        private Double positiveThreshold;
        private Double negativeThreshold;

        public Builder(){
            super(SchemaType.THRESHOLD);

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
            } else if(this.positiveThreshold != null && this.negativeThreshold != null) {
                throw new SchemaException("Only one bound can be set.");
            } else if(this.positiveThreshold != null && this.positiveThreshold < 0) {
                throw new SchemaException("LowerBound needs to be positive.");
            } else if(this.negativeThreshold != null && this.negativeThreshold < 0) {
                throw new SchemaException("UpperBound needs to be positive.");
            } else {
                return new ThresholdSchema(this);
            }
        }

        @Override
        public final boolean validate() {
            return this.bonus != null && this.source != null;
        }

        public final boolean hasBonus(){
            return this.bonus != null;
        }

        public final boolean hasSourece(){
            return this.source != null;
        }

        public final boolean hasNegativeThreshold(){
            return this.negativeThreshold != null;
        }

        public final boolean hasPositiveThreshold(){
            return this.positiveThreshold != null;
        }

        public final Double getBonus() {
            return this.bonus;
        }

        public final Builder setBonus(Double bonus){
            this.bonus = bonus;

            return this;
        }

        public final @SourceType Integer getSource() {
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
