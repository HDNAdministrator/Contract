package pt.hdn.contract;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.util.Objects;

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
        dest.writeInt(this.type);
        dest.writeString(this.name);
    }

    @Override
    public final int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object == null || getClass() != object.getClass()) {
            return false;
        } else {
            ServiceType serviceType = (ServiceType) object;

            return this.type == serviceType.type && name.equals(serviceType.name);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name);
    }

    public final int getType() {
        return this.type;
    }

    public final String getName() {
        return this.name;
    }

    public final Builder rebuild() {
        Builder builder = new Builder();

        builder.name = this.name;
        builder.type = this.type;

        return builder;
    }

    public final static class Builder{

        private Integer type;
        private String name;

        public Builder(){
            this.type = null;
            this.name = null;
        }

        public final int getType(){
            return this.type;
        }

        public final Builder setType(Integer type){
            this.type = type;

            return  this;
        }

        public final String getName(){
            return this.name;
        }

        public final Builder setName(String name){
            this.name = name;

            return  this;
        }

        public final ServiceType create() throws ServiceTypeException{
            if(this.type == null) {
                throw new ServiceTypeException("The type is missing.");
            } else if(this.type < 0) {
                throw new ServiceTypeException("Type needs to be positive.");
            } else if(this.name == null) {
                throw new ServiceTypeException("The name is missing.");
            } else if(this.name.isEmpty()) {
                throw new ServiceTypeException("Name can not be empty.");
            } else {
                return new ServiceType(this);
            }
        }
    }
}
