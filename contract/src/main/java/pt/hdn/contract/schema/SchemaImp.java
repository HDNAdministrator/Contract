package pt.hdn.contract.schema;

import android.os.Parcel;
import android.os.Parcelable;

import pt.hdn.contract.annotations.SchemaType;
import pt.hdn.contract.annotations.SourceType;

public abstract class SchemaImp implements Schema, Parcelable {

    private final @SchemaType int type;
    final @SourceType Integer source;

    SchemaImp(@SchemaType int type, @SourceType Integer source){
        this.type = type;
        this.source = source;
    }

    SchemaImp(Parcel in){
        this.type = in.readInt();
        this.source = in.readByte() != 0 ? in.readInt() : null;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);

        if(this.source == null){
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(this.source);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public final @SchemaType int getType() {
        return this.type;
    }

    @Override
    public Integer getSource() {
        return this.source;
    }

    public abstract static class BuilderImp implements Schema.Builder{

        final int type;

        BuilderImp(@SchemaType int type) {
            this.type = type;
        }

        @Override
        public final @SchemaType int getType() {
            return this.type;
        }
    }
}
