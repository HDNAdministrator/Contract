package pt.hdn.contract.schema;

import android.os.Parcelable;

import pt.hdn.contract.annotations.SchemaType;

public abstract class SchemaImp implements Schema, Parcelable {

    final int type;

    SchemaImp(@SchemaType int type){
        this.type = type;
    }

    @Override
    public final @SchemaType int getType() {
        return type;
    }
}
