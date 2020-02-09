package pt.hdn.contract;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public final class ServiceType implements Parcelable {

    public static final Creator<ServiceType> CREATOR = new Creator<ServiceType>() {
        @Override
        public ServiceType createFromParcel(Parcel in) {
            return new ServiceType(in);
        }

        @Override
        public ServiceType[] newArray(int size) {
            return new ServiceType[size];
        }
    };

    private int type;
    private String name;

    private ServiceType(Builder builder){
        this.type = builder.type;
        this.name = builder.name;
    }

    private ServiceType(Parcel in) {
        this.type = in.readInt();
        this.name = in.readString();
    }

    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(name);
    }

    @Override
    public final int describeContents() {
        return 0;
    }

    public final int getType() {
        return type;
    }

    public final String getName() {
        return name;
    }

    public Builder rebuild() {
        Builder builder = new Builder();

        builder.name = this.name;
        builder.type = this.type;

        return builder;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if(!super.equals(object)){
            if(!(object instanceof ServiceType)){
                return false;
            }

            ServiceType serviceType = (ServiceType) object;

            if(this.type != serviceType.type){
                return false;
            }
        }

        return true;
    }

    public static class Builder{

        private Integer type;
        private String name;

        public Builder(){
            this.type = null;
            this.name = null;
        }

        public int getType(){
            return type;
        }

        public Builder setType(Integer type){
            this.type = type;

            return  this;
        }

        public String getName(){
            return name;
        }

        public Builder setName(String name){
            this.name = name;

            return  this;
        }

        public ServiceType create() throws ServiceTypeException{
            if(type == null) {
                throw new ServiceTypeException("The type is missing.");
            } else if(type < 0) {
                throw new ServiceTypeException("Type needs to be positive.");
            } else if(name == null) {
                throw new ServiceTypeException("The name is missing.");
            } else if(name.isEmpty()) {
                throw new ServiceTypeException("Name can not be empty.");
            } else {
                return new ServiceType(this);
            }
        }
    }
}
