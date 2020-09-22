package pt.hdn.contract;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import pt.hdn.contract.adapters.ZonedDateTimeTypeAdapter;
import pt.hdn.contract.adapters.SchemaTypeAdapter;
import pt.hdn.contract.adapters.ByteArrayTypeAdapter;
import pt.hdn.contract.annotations.Algorithm;
import pt.hdn.contract.annotations.Status;
import pt.hdn.contract.annotations.Field;
import pt.hdn.contract.schema.Schema;

import static java.lang.reflect.Modifier.STATIC;

public final class Contract implements Parcelable {

    //region vars
    private static final String TAG = "Contract";
    public static final Creator<Contract> CREATOR = new Creator<Contract>() {
        @Override
        public Contract createFromParcel(Parcel in) {
            return new Contract(in);
        }

        @Override
        public Contract[] newArray(int size) {
            return new Contract[size];
        }
    };

    private static GsonBuilder gsonBuilder;
    private final List<Task> tasks;
    private final Recurrence recurrence;
    private ZonedDateTime buyerDeputyTimestamp;
    private ZonedDateTime buyerTimestamp;
    private ZonedDateTime sellerDeputyTimestamp;
    private ZonedDateTime sellerTimestamp;
    private ZonedDateTime witnessTimestamp;
    private byte[] buyerDeputySignature;
    private byte[] buyerSignature;
    private byte[] sellerDeputySignature;
    private byte[] sellerSignature;
    private byte[] witnessSignature;
    private String buyerID;
    private String buyerDeputyID;
    private String sellerID;
    private String sellerDeputyID;
    private String witnessID;
    //endregion vars

    public static Contract from(String json) {
        return Contract.gsonBuilder().create().fromJson(json, Contract.class);
    }

    public static final GsonBuilder gsonBuilder() {
        return Contract.gsonBuilder == null ? Contract.gsonBuilder = new GsonBuilder()
                .excludeFieldsWithModifiers(STATIC)
                .registerTypeHierarchyAdapter(Schema.class, new SchemaTypeAdapter())
                .registerTypeHierarchyAdapter(byte[].class, new ByteArrayTypeAdapter())
                .registerTypeHierarchyAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter()) : Contract.gsonBuilder;
    }

    private Contract(Builder builder) {
        this.tasks = Collections.unmodifiableList(builder.tasks);
        this.recurrence = builder.recurrence;
        this.buyerID = builder.buyerID;
        this.buyerDeputyID = builder.buyerDeputyID;
        this.sellerID = builder.sellerID;
        this.sellerDeputyID = builder.sellerDeputyID;
    }

    private Contract(Parcel in) {
        this.tasks = in.createTypedArrayList(Task.CREATOR);
        this.recurrence = in.readParcelable(Recurrence.class.getClassLoader());
        this.buyerID = in.readByte() != 0 ? in.readString() : null;
        this.buyerTimestamp = in.readByte() != 0 ? ZonedDateTime.parse(in.readString()) : null;
        this.buyerSignature = in.readByte() != 0 ? in.createByteArray() : null;
        this.buyerDeputyID = in.readByte() != 0 ? in.readString() : null;
        this.buyerDeputyTimestamp = in.readByte() != 0 ? ZonedDateTime.parse(in.readString()) : null;
        this.buyerDeputySignature = in.readByte() != 0 ? in.createByteArray() : null;
        this.sellerID = in.readByte() != 0 ? in.readString() : null;
        this.sellerTimestamp = in.readByte() != 0 ? ZonedDateTime.parse(in.readString()) : null;
        this.sellerSignature = in.readByte() != 0 ? in.createByteArray() : null;
        this.sellerDeputyID = in.readByte() != 0 ? in.readString() : null;
        this.sellerDeputyTimestamp = in.readByte() != 0 ? ZonedDateTime.parse(in.readString()) : null;
        this.sellerDeputySignature = in.readByte() != 0 ? in.createByteArray() : null;
        this.witnessID = in.readByte() != 0 ? in.readString() : null;
        this.witnessTimestamp = in.readByte() != 0 ? ZonedDateTime.parse(in.readString()) : null;
        this.witnessSignature = in.readByte() != 0 ? in.createByteArray() : null;
    }

    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(tasks);
        dest.writeParcelable(recurrence, flags);

        if (buyerID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(buyerID);
        }

        if (buyerTimestamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(buyerTimestamp.toString());
        }

        if (buyerSignature == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeByteArray(buyerSignature);
        }

        if (buyerDeputyID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(buyerDeputyID);
        }

        if (buyerDeputyTimestamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(buyerDeputyTimestamp.toString());
        }

        if (buyerDeputySignature == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeByteArray(buyerDeputySignature);
        }

        if (sellerID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(sellerID);
        }

        if (sellerTimestamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(sellerTimestamp.toString());
        }

        if (sellerSignature == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeByteArray(sellerSignature);
        }

        if (sellerDeputyID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(sellerDeputyID);
        }

        if (sellerDeputyTimestamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(sellerDeputyTimestamp.toString());
        }

        if (sellerDeputySignature == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeByteArray(sellerDeputySignature);
        }

        if (witnessID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(witnessID);
        }

        if (witnessTimestamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(witnessTimestamp.toString());
        }

        if (witnessSignature == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeByteArray(witnessSignature);
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
            Contract contract = (Contract) obj;

            return this.tasks.equals(contract.tasks) &&
                    this.recurrence.equals(contract.recurrence) &&
                    Objects.equals(this.witnessID, contract.witnessID) &&
                    Objects.equals(this.buyerID, contract.buyerID) &&
                    Objects.equals(this.buyerDeputyID, contract.buyerDeputyID) &&
                    Objects.equals(this.sellerID, contract.sellerID) &&
                    Objects.equals(this.sellerDeputyID, contract.sellerDeputyID) &&
                    Objects.equals(this.buyerDeputyTimestamp, contract.buyerDeputyTimestamp) &&
                    Objects.equals(this.buyerTimestamp, contract.buyerTimestamp) &&
                    Objects.equals(this.sellerDeputyTimestamp, contract.sellerDeputyTimestamp) &&
                    Objects.equals(this.sellerTimestamp, contract.sellerTimestamp) &&
                    Objects.equals(this.witnessTimestamp, contract.witnessTimestamp) &&
                    Arrays.equals(this.buyerDeputySignature, contract.buyerDeputySignature) &&
                    Arrays.equals(this.buyerSignature, contract.buyerSignature) &&
                    Arrays.equals(this.sellerDeputySignature, contract.sellerDeputySignature) &&
                    Arrays.equals(this.sellerSignature, contract.sellerSignature) &&
                    Arrays.equals(this.witnessSignature, contract.witnessSignature);
        }
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(tasks, recurrence, buyerID, buyerDeputyID, sellerID, sellerDeputyID, witnessID, buyerDeputyTimestamp, buyerTimestamp, sellerDeputyTimestamp, sellerTimestamp, witnessTimestamp);
        result = 31 * result + Arrays.hashCode(buyerDeputySignature);
        result = 31 * result + Arrays.hashCode(buyerSignature);
        result = 31 * result + Arrays.hashCode(sellerDeputySignature);
        result = 31 * result + Arrays.hashCode(sellerSignature);
        result = 31 * result + Arrays.hashCode(witnessSignature);

        return result;
    }

    public final String getBuyerID() {
        return this.buyerID;
    }

    public final String getBuyerDeputyID() {
        return this.buyerDeputyID;
    }

    public final String getSellerID() {
        return this.sellerID;
    }

    public final String getSellerDeputyID() {
        return this.sellerDeputyID;
    }

    public final String getWitnessID() {
        return this.witnessID;
    }

    public final List<Task> getTasks() {
        return this.tasks;
    }

    public final Recurrence getRecurrence() {
        return this.recurrence;
    }

    public final String setBuyerDeputySignature(PrivateKey privateKey) {
        this.buyerDeputyTimestamp = ZonedDateTime.now();
        this.buyerDeputySignature = sign(privateKey, Field.BUYER_DEPUTY_TIMESTAMP);

        return toJson();
    }

    public final boolean validateBuyerDeputySignature(PublicKey publicKey) {
        return validate(publicKey, buyerDeputySignature, Field.BUYER_DEPUTY_TIMESTAMP);
    }

    public final String setBuyerSignature(PrivateKey privateKey) {
        this.buyerTimestamp = ZonedDateTime.now();
        this.buyerSignature = sign(privateKey, Field.BUYER_TIMESTAMP);

        return toJson();
    }

    public final boolean validateBuyerSignature(PublicKey publicKey) {
        return validate(publicKey, buyerSignature, Field.BUYER_TIMESTAMP);
    }

    public final String setSellerDeputySignature(PrivateKey privateKey) {
        this.sellerDeputyTimestamp = ZonedDateTime.now();
        this.sellerDeputySignature = sign(privateKey, Field.SELLER_DEPUTY_TIMESTAMP);

        return toJson();
    }

    public final boolean validateSellerDeputySignature(PublicKey publicKey) {
        return validate(publicKey, sellerDeputySignature, Field.SELLER_DEPUTY_TIMESTAMP);
    }

    public final String setSellerSignature(PrivateKey privateKey) {
        this.sellerTimestamp = ZonedDateTime.now();
        this.sellerSignature = sign(privateKey, Field.SELLER_TIMESTAMP);

        return toJson();
    }

    public final boolean validateSellerSignature(PublicKey publicKey) {
        return validate(publicKey, sellerSignature, Field.SELLER_TIMESTAMP);
    }

    public final boolean validateWitnessSignature(PublicKey publicKey) {
        return validate(publicKey, witnessSignature, Field.WITNESS_TIMESTAMP);
    }

    public final @Status int getStatus() {
        if (sellerSignature == null || buyerSignature == null) {
            return Status.PENDING;
        } else if (recurrence.getFinish() != null && recurrence.getFinish().isBefore(ZonedDateTime.now())) {
            return Status.EXPIRED;
        } else {
            return Status.VALID;
        }
    }

    public final String toJson() {
        return gsonBuilder().create().toJson(this);
    }

    public final JsonElement toJsonTree() {
        return gsonBuilder().create().toJsonTree(this);
    }

    public final Builder rebuild() {
        Builder builder = new Builder();
        builder.recurrenceBuilder = recurrence.rebuild();

        for (Task task : tasks) {
            builder.taskBuilders.add(task.rebuild());
        }

        return builder;
    }

    private byte[] thisToBytes(final String field) {
        ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                String fieldName = f.getName();

                return (fieldName.startsWith(Field.BUYER) | fieldName.startsWith(Field.SELLER) | fieldName.startsWith(Field.WITNESS)) && !fieldName.equals(field);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };

        return gsonBuilder().setExclusionStrategies(exclusionStrategy).create().toJson(this).getBytes();
    }

    private boolean validate(PublicKey publicKey, byte[] bytes, String field) {
        boolean valid = false;

        try {
            Signature signature = java.security.Signature.getInstance(Algorithm.SHA512withRSA);
            signature.initVerify(publicKey);
            signature.update(thisToBytes(field));

            valid = signature.verify(bytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }

        return valid;
    }

    private byte[] sign(PrivateKey privateKey, String field) {
        byte[] bytes = new byte[0];

        try {
            Signature signature = java.security.Signature.getInstance(Algorithm.SHA512withRSA);
            signature.initSign(privateKey);
            signature.update(thisToBytes(field));

            bytes = signature.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public final static class Builder {

        //region vars
        private List<Task.Builder> taskBuilders;
        private List<Task> tasks;
        private Recurrence.Builder recurrenceBuilder;
        private Recurrence recurrence;
        private String buyerID;
        private String buyerDeputyID;
        private String sellerID;
        private String sellerDeputyID;
        //endregion vars

        public Builder() {
            this.tasks = new ArrayList<>();
            this.taskBuilders = new ArrayList<>();
            this.recurrenceBuilder = null;
        }

        public final List<Task.Builder> getTaskBuilders() {
            return taskBuilders;
        }

        public final Builder addTaskBuilder(Task.Builder builder) {
            this.taskBuilders.add(builder);

            return this;
        }

        public final Builder addTaskBuilders(Task.Builder... builders) {
            return addTaskBuilders(Arrays.asList(builders));
        }

        public final Builder addTaskBuilders(List<Task.Builder> builders) {
            this.taskBuilders.addAll(builders);

            return this;
        }

        public final Recurrence.Builder getRecurrenceBuilder() {
            return recurrenceBuilder;
        }

        public final Builder setRecurrenceBuilder(Recurrence.Builder builder) {
            this.recurrenceBuilder = builder;

            return this;
        }

        public final Builder setBuyerID(String buyerID) {
            this.buyerID = buyerID;

            return this;
        }

        public final Builder setBuyerDeputyID(String buyerDeputyID) {
            this.buyerDeputyID = buyerDeputyID;

            return this;
        }

        public final Builder setSellerID(String sellerID) {
            this.sellerID = sellerID;

            return this;
        }

        public final Builder setSellerDeputyID(String sellerDeputyID) {
            this.sellerDeputyID = sellerDeputyID;

            return this;
        }

        public final boolean validate() {
            for (Task.Builder builder : this.taskBuilders) {
                if (!builder.validate()) {
                    return false;
                }
            }

            return isSet(this.buyerID) && isSet(this.buyerDeputyID) && isSet(this.sellerID) && isSet(this.sellerDeputyID) && this.recurrenceBuilder != null && this.recurrenceBuilder.validate();
        }

        private boolean isSet(String value) {
            return !TextUtils.isEmpty(value);
        }

        public final Contract create() throws ContractException {
            try {
                if (recurrenceBuilder == null) {
                    throw new ContractException("The Recurrence.Builder is missing.");
                } else {
                    this.recurrence = recurrenceBuilder.create();
                }

                if (taskBuilders.isEmpty()) {
                    throw new ContractException("There are no Task.Builder added.");
                } else {
                    for (Task.Builder builder : taskBuilders) {
                        tasks.add(builder.create());
                    }
                }

                if (!isSet(buyerID)) {
                    throw new ContractException("Buyer ID is not set.");
                }

                if (!isSet(buyerDeputyID)) {
                    throw new ContractException("Buyer Deputy ID is not set.");
                }

                if (!isSet(sellerID)) {
                    throw new ContractException("Seller ID is not set.");
                }

                if (!isSet(sellerDeputyID)) {
                    throw new ContractException("Seller Deputy ID is not set.");
                }
            } catch (RecurrenceException | TaskException e) {
                throw new ContractException(e.getMessage());
            }

            return new Contract(this);
        }
    }
}
