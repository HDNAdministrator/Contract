package pt.hdn.contract;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pt.hdn.contract.adapters.SignatureTypeAdapter;
import pt.hdn.contract.adapters.TimestampTypeAdapter;

import static java.lang.reflect.Modifier.STATIC;
import static pt.hdn.contract.annotations.Contract.ALGORITHM;
import static pt.hdn.contract.annotations.Contract.BUYER;
import static pt.hdn.contract.annotations.Contract.BUYER_DEPUTY_TIMESTAMP;
import static pt.hdn.contract.annotations.Contract.BUYER_TIMESTAMP;
import static pt.hdn.contract.annotations.Contract.SELLER;
import static pt.hdn.contract.annotations.Contract.SELLER_DEPUTY_TIMESTAMP;
import static pt.hdn.contract.annotations.Contract.SELLER_TIMESTAMP;
import static pt.hdn.contract.annotations.Contract.WITNESS;
import static pt.hdn.contract.annotations.Contract.WITNESS_TIMESTAMP;

public final class Contract implements Parcelable {

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

    private static final String TAG = "Contract";
    private static GsonBuilder gsonBuilder;
    private final List<Task> tasks;
    private final Recurrence recurrence;
    private Instant buyerDeputyTimestamp;
    private Instant buyerTimestamp;
    private Instant sellerDeputyTimestamp;
    private Instant sellerTimestamp;
    private Instant witnessTimestamp;
    private byte[] buyerDeputySignature;
    private byte[] buyerSignature;
    private byte[] sellerDeputySignature;
    private byte[] sellerSignature;
    private byte[] witnessSignature;

    public static Contract from(String json){
        return Contract.gsonBuilder().create().fromJson(json, Contract.class);
    }

    private Contract(Builder builder) {
        this.tasks = Collections.unmodifiableList(builder.tasks);
        this.recurrence = builder.recurrence;
    }

    private Contract(Parcel in) {
        this.tasks = in.createTypedArrayList(Task.CREATOR);
        this.recurrence = in.readParcelable(Recurrence.class.getClassLoader());

        if(in.readByte() != 0){
            this.buyerTimestamp = Instant.parse(in.readString());
        }

        if(in.readByte() != 0){
            this.buyerSignature = in.createByteArray();
        }

        if(in.readByte() != 0){
            this.buyerDeputyTimestamp = Instant.parse(in.readString());
        }

        if(in.readByte() != 0){
            this.buyerDeputySignature = in.createByteArray();
        }

        if(in.readByte() != 0){
            this.sellerTimestamp = Instant.parse(in.readString());
        }

        if(in.readByte() != 0){
            this.sellerSignature = in.createByteArray();
        }

        if(in.readByte() != 0){
            this.sellerDeputyTimestamp = Instant.parse(in.readString());
        }

        if(in.readByte() != 0){
            this.sellerDeputySignature = in.createByteArray();
        }

        if(in.readByte() != 0){
            this.witnessTimestamp = Instant.parse(in.readString());
        }

        if(in.readByte() != 0){
            this.witnessSignature = in.createByteArray();
        }
    }

    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(tasks);
        dest.writeParcelable(recurrence, flags);

        if(buyerTimestamp == null){
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(buyerTimestamp.toString());
        }

        if(buyerSignature == null){
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeByteArray(buyerSignature);
        }

        if(buyerDeputyTimestamp == null){
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(buyerDeputyTimestamp.toString());
        }

        if(buyerDeputySignature == null){
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeByteArray(buyerDeputySignature);
        }

        if(sellerTimestamp == null){
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(sellerTimestamp.toString());
        }

        if(sellerSignature == null){
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeByteArray(sellerSignature);
        }

        if(sellerDeputyTimestamp == null){
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(sellerDeputyTimestamp.toString());
        }

        if(sellerDeputySignature == null){
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeByteArray(sellerDeputySignature);
        }

        if(witnessTimestamp == null){
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(witnessTimestamp.toString());
        }

        if(witnessSignature == null){
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

    public final List<Task> getTasks(){
        return tasks;
    }

    public final Recurrence getRecurrence(){
        return recurrence;
    }

    public final String setBuyerDeputySignature(PrivateKey privateKey){
        this.buyerDeputyTimestamp = Instant.now();
        this.buyerDeputySignature = sign(privateKey, BUYER_DEPUTY_TIMESTAMP);

        return toString();
    }

    public final boolean validateBuyerDeputySignature(PublicKey publicKey){
        return validate(publicKey, buyerDeputySignature, BUYER_DEPUTY_TIMESTAMP);
    }

    public final String setBuyerSignature(PrivateKey privateKey){
        this.buyerTimestamp = Instant.now();
        this.buyerSignature = sign(privateKey, BUYER_TIMESTAMP);

        return toString();
    }

    public final boolean validateBuyerSignature(PublicKey publicKey){
        return validate(publicKey, buyerSignature, BUYER_TIMESTAMP);
    }

    public final String setSellerDeputySignature(PrivateKey privateKey){
        this.sellerDeputyTimestamp = Instant.now();
        this.sellerDeputySignature = sign(privateKey, SELLER_DEPUTY_TIMESTAMP);

        return toString();
    }

    public final boolean validateSellerDeputySignature(PublicKey publicKey){
        return validate(publicKey, sellerDeputySignature, SELLER_DEPUTY_TIMESTAMP);
    }

    public final String setSellerSignature(PrivateKey privateKey){
        this.sellerTimestamp = Instant.now();
        this.sellerSignature = sign(privateKey, SELLER_TIMESTAMP);

        return toString();
    }

    public final boolean validateSellerSignature(PublicKey publicKey){
        return validate(publicKey, sellerSignature, SELLER_TIMESTAMP);
    }

    public final boolean validateWitnessSignature(PublicKey publicKey){
        return validate(publicKey, witnessSignature, WITNESS_TIMESTAMP);
    }

    @NonNull
    @Override
    public final String toString(){
        return gsonBuilder().create().toJson(this);
    }

    public final Builder rebuild(){
        Builder builder = new Builder();

        builder.recurrenceBuilder = recurrence.rebuild();

        for (Task task: tasks){
            builder.taskBuilders.add(task.rebuild());
        }

        return builder;
    }

    private static GsonBuilder gsonBuilder(){
        if(gsonBuilder == null){
            Contract.gsonBuilder = new GsonBuilder()
                    .excludeFieldsWithModifiers(STATIC)
                    .registerTypeHierarchyAdapter(Instant.class, new TimestampTypeAdapter())
                    .registerTypeHierarchyAdapter(byte[].class, new SignatureTypeAdapter());
        }

        return gsonBuilder;
    }

    private byte[] thisToBytes(final String field){
        ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                String fieldName = f.getName();

                return (fieldName.startsWith(BUYER) | fieldName.startsWith(SELLER) | fieldName.startsWith(WITNESS)) && !fieldName.equals(field);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };

        return gsonBuilder().setExclusionStrategies(exclusionStrategy).create().toJson(this).getBytes();
    }

    private boolean validate(PublicKey publicKey, byte[] bytes, String field){
        boolean valid = false;

        try {
            Signature signature = java.security.Signature.getInstance(ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(thisToBytes(field));

            valid = signature.verify(bytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }

        return valid;
    }

    private byte[] sign(PrivateKey privateKey, String field){
        byte[] bytes = new byte[0];

        try {
            Signature signature = java.security.Signature.getInstance(ALGORITHM);
            signature.initSign(privateKey);
            signature.update(thisToBytes(field));

            bytes = signature.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public static class Builder{

        private List<Task.Builder> taskBuilders;
        private List<Task> tasks;
        private Recurrence.Builder recurrenceBuilder;
        private Recurrence recurrence;

        public Builder() {
            this.tasks = new ArrayList<>();
            this.taskBuilders = new ArrayList<>();
            this.recurrenceBuilder = null;
        }

        public List<Task.Builder> getTaskBuilders() {
            return taskBuilders;
        }

        public Builder addTaskBuilder(Task.Builder builder){
            this.taskBuilders.add(builder);

            return this;
        }

        public Recurrence.Builder getRecurrenceBuilder() {
            return recurrenceBuilder;
        }

        public Builder setRecurrenceBuilder(Recurrence.Builder builder){
            this.recurrenceBuilder = builder;

            return this;
        }

        public Contract create() throws ContractException {
            try {
                if(recurrenceBuilder == null){
                    throw new ContractException("The Recurrence.Builder is missing.");
                } else {
                    this.recurrence = recurrenceBuilder.create();
                }

                if(taskBuilders.isEmpty()){
                    throw new ContractException("There are no Task.Builder added.");
                } else {
                    for (Task.Builder builder: taskBuilders) {
                        tasks.add(builder.create());
                    }
                }
            } catch (RecurrenceException | TaskException e) {
                throw new ContractException(e.getMessage());
            }

            return new Contract(this);
        }
    }
}
